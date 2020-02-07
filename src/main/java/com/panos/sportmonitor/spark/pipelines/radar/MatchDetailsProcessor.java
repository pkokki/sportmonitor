package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.databind.JsonNode;
import com.panos.sportmonitor.spark.PostgresHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.*;

@Service
public class MatchDetailsProcessor {
    public void run(SparkSession spark, JavaStreamingContext streamingContext, JavaDStream<RadarJsonEvent> messageStream) {
        JavaDStream<RadarJsonEvent> detailsJsonDS = messageStream.filter(r -> r.event.equals("match_detailsextended"));
        JavaDStream<Tuple2<RadarJsonEvent, Map.Entry<String, JsonNode>>> jsonEntriesDS = detailsJsonDS.flatMap(r -> {
            List<Tuple2<RadarJsonEvent, Map.Entry<String, JsonNode>>> list = new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> fields = r.data.path("values").fields();
            while (fields.hasNext()) {
                list.add(new Tuple2<>(r, fields.next()));
            }
            return list.iterator();
        });

        // Append missing types
        List<MatchDetailsType> existingDetailTypes = PostgresHelper
                .readTable(spark, "match_detail_types")
                .map((MapFunction<Row, MatchDetailsType>) row -> new MatchDetailsType(row.getString(0), row.getString(1)), Encoders.bean(MatchDetailsType.class))
                .collectAsList()
                ;
        JavaDStream<MatchDetailsType> newDetailTypesDS = jsonEntriesDS
                .mapToPair(e -> new Tuple2<>(e._2.getKey(), new MatchDetailsType(e._2.getKey(), e._2.getValue().path("name").asText())))
                .reduceByKey((k, n) -> n)
                .map(r -> r._2);
        newDetailTypesDS.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                List<MatchDetailsType> newDetailTypes = rdd.collect();
                Collection missingDetailTypes = CollectionUtils.subtract(newDetailTypes, existingDetailTypes);
                System.out.println(String.format("Adding %d new types", missingDetailTypes.size()));
                Dataset<Row> missingDetailTypesDF = spark.createDataFrame(new ArrayList<>(missingDetailTypes), MatchDetailsType.class);
                PostgresHelper.appendDataset(missingDetailTypesDF, "match_detail_types");
            }
        });

        // Append detail events
        JavaDStream<MatchDetailsEvent> detailsDS = jsonEntriesDS
                .map(r -> {
                    long matchid = r._1.data.path("_matchid").asLong();
                    long timestamp = r._1.dob;
                    Map.Entry<String, JsonNode> entry = r._2;
                        Tuple2<String, String> type = new Tuple2<>(entry.getKey(), entry.getValue().path("name").asText());
                    return new MatchDetailsEvent(matchid, timestamp, entry.getKey(), entry.getValue());
                });
        detailsDS.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, MatchDetailsEvent.class);
                PostgresHelper.appendDataset(ds, "match_detail_events");
            }
        });
    }
}
