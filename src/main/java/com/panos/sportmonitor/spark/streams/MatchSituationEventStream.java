package com.panos.sportmonitor.spark.streams;


import com.panos.sportmonitor.spark.dto.MatchSituationEvent;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

public class MatchSituationEventStream extends AbstractJavaStream<MatchSituationEvent>  {
    public MatchSituationEventStream(JavaDStream<MatchSituationEvent> stream) {
        super(stream);
    }

    public void appendToMatchSituationEventsTable(JavaRDD<MatchSituationEvent> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, MatchSituationEvent.class);
            PostgresHelper.appendDataset(ds, "match_situation_events");
        }
    }
}
