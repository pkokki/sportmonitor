package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.databind.JsonNode;
import com.panos.sportmonitor.spark.PostgresHelper;
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
public class MatchSituationsProcessor {
    public void run(SparkSession spark, JavaDStream<RadarJsonEvent> messageStream) {
        JavaDStream<MatchSituationEvent> matchSituationEvents = messageStream
                .filter(r -> r.event.equals("stats_match_situation"))
                .flatMap(r -> {
                    List<MatchSituationEvent> list = new ArrayList<>();
                    long matchid = Long.parseLong(r.data.path("matchid").asText());
                    Iterator<JsonNode> nodes = r.data.path("data").elements();
                    while (nodes.hasNext()) {
                        JsonNode node = nodes.next();
                        list.add(new MatchSituationEvent(matchid, node));
                    }
                    return list.iterator();
                })
                .mapToPair(mse -> new Tuple2<>(mse.getId(), mse))
                .mapWithState(StateSpec.function(MatchSituationsProcessor::onlyOneMatchSituationEventSpec).timeout(Durations.minutes(5)))
                .filter(r -> r.isPresent())
                .map(r -> r.get());
        matchSituationEvents.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, MatchSituationEvent.class);
                PostgresHelper.appendDataset(ds, "match_situation_events");
            }
        });
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
}
