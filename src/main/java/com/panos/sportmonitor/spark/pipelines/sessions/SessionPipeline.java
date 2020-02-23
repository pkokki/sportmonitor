package com.panos.sportmonitor.spark.pipelines.sessions;

import com.panos.sportmonitor.spark.dto.RawOverviewEvent;
import com.panos.sportmonitor.spark.dto.RawRadarEvent;
import com.panos.sportmonitor.spark.pipelines.sessions.models.*;
import com.panos.sportmonitor.spark.sources.KafkaOverviewSource;
import com.panos.sportmonitor.spark.sources.KafkaRadarSource;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.spark_project.guava.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class SessionPipeline {
    @Autowired
    private KafkaOverviewSource kafkaOverviewSource;
    @Autowired
    private KafkaRadarSource kafkaRadarSource;

    public void run(SparkSession spark, JavaStreamingContext streamingContext) {
        // Sources
        JavaDStream<RawOverviewEvent> rawOverviewEvents = kafkaOverviewSource.createRawOverviewEventStream(streamingContext);
        JavaDStream<RawRadarEvent> rawRadarEvents = kafkaRadarSource.run(streamingContext);

        // Processors
        JavaDStream<MatchEvent> matchOverviewEvents = rawOverviewEvents
                .filter(e -> e.getBetRadarId() > 0)
                .map(MatchRadarEventFactory::create)
                .filter(Objects::nonNull);
        JavaDStream<MatchEvent> matchRadarEvents = rawRadarEvents
                .map(MatchRadarEventFactory::create)
                .filter(Objects::nonNull);
        JavaDStream<MatchEvent> matchEvents = matchOverviewEvents.union(matchRadarEvents);
        matchEvents
                .mapToPair(e -> new Tuple2<>(e.getClass().getSimpleName(), 1))
                .reduceByKey(Integer::sum)
                .print(100);

        JavaDStream<MatchSessionEvent> matchSessionEvents = matchEvents
                .mapToPair(e -> new Tuple2<>(e.getMatchid(), e))
                .mapWithState(StateSpec.function(SessionPipeline::matchEventToMatchSessionEventMapping).timeout(Durations.minutes(120)))
                .flatMap(e -> e);
        // Sinks
        matchSessionEvents.count().print();
    }

    private static Iterator<MatchSessionEvent> matchEventToMatchSessionEventMapping(long id, Optional<MatchEvent> item, State<MatchSession> state) {
        Iterator<MatchSessionEvent> matchSessionEvents = Iterators.emptyIterator();
        if (!state.isTimingOut()) {
            MatchEvent matchEvent = item.get();
            MatchSession session;
            if (state.exists()) {
                session = state.get();
                if (session.getTimestamp() < matchEvent.getTimestamp()) {
                    matchSessionEvents = updateMatchSession(session, matchEvent);
                }
            } else {
                session = new MatchSession();
                matchSessionEvents = initializeMatchSession(session, matchEvent);
            }
            state.update(session);
        }
        return matchSessionEvents;
    }

    private static Iterator<MatchSessionEvent> initializeMatchSession(MatchSession session, MatchEvent matchEvent) {
        System.out.println(String.format("initializeMatchSession: %s", matchEvent));
        List<MatchSessionEvent> matchSessionEvents = new ArrayList<>();
        session.setMatchid(matchEvent.getMatchid());
        session.setTimestamp(matchEvent.getTimestamp());
        return matchSessionEvents.iterator();
    }

    private static Iterator<MatchSessionEvent> updateMatchSession(MatchSession session, MatchEvent matchEvent) {
        System.out.println(String.format("updateMatchSession: %s", matchEvent));
        List<MatchSessionEvent> matchSessionEvents = new ArrayList<>();
        return matchSessionEvents.iterator();
    }
}
