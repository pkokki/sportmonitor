package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.databind.JsonNode;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MatchTimelineProcessor {
    public void run(SparkSession spark, JavaDStream<RadarJsonEvent> messageStream) {
        JavaDStream<MatchTimelineEvent> matchTimelineEvents = messageStream
                .filter(r -> r.event.equals("match_timeline"))
                .flatMap(r -> {
                    List<MatchTimelineEvent> list = new ArrayList<>();
                    Iterator<JsonNode> events = r.data.path("events").elements();
                    while (events.hasNext()) {
                        JsonNode evNode = events.next();
                        if (evNode != null)
                            list.add(new MatchTimelineEvent(evNode));
                    }
                    return list.iterator();
                })
                .mapToPair(mte -> new Tuple2<>(mte.getId(), mte))
                .mapWithState(StateSpec.function(MatchTimelineProcessor::onlyOneMatchTimelineEventSpec).timeout(Durations.minutes(5)))
                .filter(r -> r.isPresent())
                .map(r -> r.get());

        matchTimelineEvents.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, MatchTimelineEvent.class);
                PostgresHelper.appendDataset(ds, "match_timeline_events");
            }
        });
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
}
