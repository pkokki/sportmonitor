package com.panos.sportmonitor.spark.streams;

import com.fasterxml.jackson.databind.JsonNode;
import com.panos.sportmonitor.spark.dto.MatchDetailsEvent;
import com.panos.sportmonitor.spark.dto.MatchSituationEvent;
import com.panos.sportmonitor.spark.dto.MatchTimelineEvent;
import com.panos.sportmonitor.spark.dto.RawRadarEvent;
import org.apache.spark.api.java.Optional;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RawRadarEventStream extends AbstractJavaStream<RawRadarEvent> {
    public RawRadarEventStream(JavaDStream<RawRadarEvent> stream) {
        super(stream);
    }

    public MatchSituationEventStream createMatchSituationEventStream() {
        JavaDStream<MatchSituationEvent> stream = this
                .filter(r -> r.getEvent().equals("stats_match_situation"))
                .flatMap(r -> {
                    List<MatchSituationEvent> list = new ArrayList<>();
                    long matchId = Long.parseLong(r.getData().path("matchid").asText());
                    long eventStamp = r.getDob();
                    Iterator<JsonNode> nodes = r.getData().path("data").elements();
                    while (nodes.hasNext()) {
                        JsonNode node = nodes.next();
                        list.add(new MatchSituationEvent(matchId, eventStamp, node));
                    }
                    return list.iterator();
                })
                .mapToPair(mse -> new Tuple2<>(mse.getId(), mse))
                .mapWithState(StateSpec.function(RawRadarEventStream::onlyOneMatchSituationEventSpec).timeout(Durations.minutes(5)))
                .filter(Optional::isPresent)
                .map(r -> r.get())
                ;
        return new MatchSituationEventStream(stream);
    }

    private static Optional<MatchSituationEvent> onlyOneMatchSituationEventSpec(Long id, Optional<MatchSituationEvent> item, State<Long> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }

    public MatchTimelineEventStream createMatchTimelineEventStream(String typesViewName) {
        JavaDStream<MatchTimelineEvent> stream = this
                .filter(r -> r.getEvent().equals("match_timeline"))
                .flatMap(r -> {
                    List<MatchTimelineEvent> list = new ArrayList<>();
                    Iterator<JsonNode> events = r.getData().path("events").elements();
                    while (events.hasNext()) {
                        JsonNode evNode = events.next();
                        if (evNode != null)
                            list.add(new MatchTimelineEvent(evNode));
                    }
                    return list.iterator();
                })
                .mapToPair(mte -> new Tuple2<>(mte.getId(), mte))
                .mapWithState(StateSpec.function(RawRadarEventStream::onlyOneMatchTimelineEventSpec).timeout(Durations.minutes(5)))
                .filter(r -> r.isPresent())
                .map(r -> r.get())
                ;
        return new MatchTimelineEventStream(stream, typesViewName);
    }

    private static Optional<MatchTimelineEvent> onlyOneMatchTimelineEventSpec(Long id, Optional<MatchTimelineEvent> item, State<Long> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }

    public MatchDetailsEventStream createMatchDetailsEventStream(String typesViewName) {
        JavaDStream<MatchDetailsEvent> stream = this
                .filter(r -> r.getEvent().equals("match_detailsextended"))
                .flatMap(r -> {
                    List<Tuple2<RawRadarEvent, Map.Entry<String, JsonNode>>> list = new ArrayList<>();
                    Iterator<Map.Entry<String, JsonNode>> fields = r.getData().path("values").fields();
                    while (fields.hasNext()) {
                        list.add(new Tuple2<>(r, fields.next()));
                    }
                    return list.iterator();
                })
                .map(r -> {
                    long matchId = r._1.getData().path("_matchid").asLong();
                    long eventStamp = r._1.getDob();
                    Map.Entry<String, JsonNode> entry = r._2;
                    return new MatchDetailsEvent(matchId, eventStamp, entry.getKey(), entry.getValue());
                })
                ;
        return new MatchDetailsEventStream(stream, typesViewName);
    }
}
