package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.dto.EventScoreChange;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

public class EventScoreChangeStream extends AbstractJavaStream<EventScoreChange> {
    public EventScoreChangeStream(JavaDStream<EventScoreChange> stream) {
        super(stream);
    }

    public void appendToEventScoreChangesTable(JavaRDD<EventScoreChange> rdd, Time time) {
        Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, EventScoreChange.class);
        PostgresHelper.appendDataset(ds, "event_score_changes");
    }
}
