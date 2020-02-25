package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.dto.EventMasterData;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

public class EventMasterDataStream extends AbstractJavaStream<EventMasterData> {
    public EventMasterDataStream(JavaDStream<EventMasterData> stream) {
        super(stream);
    }

    public void appendToEventMasterTable(JavaRDD<EventMasterData> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, EventMasterData.class);
            PostgresHelper.appendDataset(ds, "event_master_data");
        }
    }
}
