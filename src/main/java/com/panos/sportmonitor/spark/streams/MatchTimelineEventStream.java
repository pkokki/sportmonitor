package com.panos.sportmonitor.spark.streams;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.spark.dto.MatchTimelineEvent;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.util.TempViewHelper;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import scala.collection.JavaConverters;
import scala.collection.Seq;

public class MatchTimelineEventStream extends AbstractJavaStream<MatchTimelineEvent>  {
    private final String viewMatchTimelineTypes;
    private final Encoder<Row> encoder;
    private final Seq<String> matchTimelineJoinColumns;

    public MatchTimelineEventStream(JavaDStream<MatchTimelineEvent> stream, String viewMatchTimelineTypes) {
        super(stream);
        this.viewMatchTimelineTypes = viewMatchTimelineTypes;
        this.encoder = RowEncoder.apply(new StructType(new StructField[]{
                new StructField("typeid", DataTypes.LongType, true, Metadata.empty()),
                new StructField("description", DataTypes.StringType, true, Metadata.empty())
        }));
        this.matchTimelineJoinColumns = JavaConverters
                .asScalaBufferConverter(Lists.newArrayList("typeid"))
                .asScala()
                .toSeq()
        ;
    }

    public void appendToMatchTimelineEventsTable(JavaRDD<MatchTimelineEvent> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, MatchTimelineEvent.class)
                    .drop("typeDescription");
            PostgresHelper.appendDataset(ds, "match_timeline_events");
        }
    }

    public void appendToMatchTimelineTypesTable(JavaRDD<MatchTimelineEvent> rdd, Time time) {
        if (!rdd.isEmpty()) {
            SparkSession spark = SparkSession.active();
            RDD<Row> rowRdd = rdd
                    .map(e -> RowFactory.create(e.getTypeId(), e.getTypeDescription()))
                    .rdd();
            Dataset<Row> newTypes = spark.sqlContext().createDataset(rowRdd, encoder)
                    .distinct()
                    .join(spark.table(viewMatchTimelineTypes), matchTimelineJoinColumns, "left_anti");
            if (!newTypes.isEmpty()) {
                PostgresHelper.appendDataset(newTypes, "match_timeline_types");
                TempViewHelper.appendOrCreateView(viewMatchTimelineTypes, newTypes.select("typeid"));
            }
        }
    }
}
