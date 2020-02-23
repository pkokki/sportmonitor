package com.panos.sportmonitor.spark.streams;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.util.TempViewHelper;
import com.panos.sportmonitor.spark.dto.EventMasterData;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import scala.collection.JavaConversions;
import scala.collection.mutable.Buffer;

public class EventMasterDataStream extends AbstractJavaStream<EventMasterData> {
    public EventMasterDataStream(JavaDStream<EventMasterData> stream) {
        super(stream);
    }

    public void appendToEventMasterTable(JavaRDD<EventMasterData> rdd, Time time) {
        if (!rdd.isEmpty()) {
            SparkSession spark = SparkSession.active();
            Dataset<Row> currentEvents = spark.createDataFrame(rdd, EventMasterData.class);
            if (!spark.catalog().tableExists("tmp_current_events")) {
                long minStartTimeTicks = time.minus(Durations.minutes(120)).milliseconds();
                Dataset<Row> dbEvents = PostgresHelper.readQuery(spark,
                        String.format("select eventid, starttimeticks from event_master_data where starttimeticks >= %d", minStartTimeTicks));
                TempViewHelper.appendOrCreateView("tmp_current_events", dbEvents, time);
                //System.out.println(String.format("Created tmp_current_events with %d rows", dbEvents.count()));
            }

            Buffer<String> seq = JavaConversions.asScalaBuffer(Lists.newArrayList("eventid"));
            Dataset<Row> newEvents = currentEvents
                    .as("a")
                    .join(spark.table("tmp_current_events").as("b"), seq, "left_anti")
                    .select("a.*");
            if (!newEvents.isEmpty()) {
                PostgresHelper.appendDataset(newEvents, "event_master_data");
                TempViewHelper.appendOrCreateView("tmp_current_events", newEvents.select("eventid", "starttimeticks"), time);
                //System.out.println(String.format("Updated tmp_current_events with %d rows, total=%d", newEvents.count(), spark.table("tmp_current_events").count()));
            }
        }
    }
}
