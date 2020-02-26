package com.panos.sportmonitor.spark.streams;


import com.google.common.collect.Lists;
import com.panos.sportmonitor.spark.dto.MatchDetailsEvent;
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

public class MatchDetailsEventStream extends AbstractJavaStream<MatchDetailsEvent> {
    private final String viewMatchDetailTypes;
    private final Encoder<Row> encoder;
    private final Seq<String> matchDetailJoinColumns;

    public MatchDetailsEventStream(JavaDStream<MatchDetailsEvent> stream, String viewMatchDetailTypes) {
        super(stream);
        this.viewMatchDetailTypes = viewMatchDetailTypes;
        this.encoder = RowEncoder.apply(new StructType(new StructField[]{
                new StructField("typeid", DataTypes.StringType, true, Metadata.empty()),
                new StructField("description", DataTypes.StringType, true, Metadata.empty())
        }));
        this.matchDetailJoinColumns = JavaConverters
                .asScalaBufferConverter(Lists.newArrayList("typeid"))
                .asScala()
                .toSeq()
        ;
    }

    public void appendToMatchDetailsEventsTable(JavaRDD<MatchDetailsEvent> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, MatchDetailsEvent.class)
                    .drop("typeDescription");
            PostgresHelper.appendDataset(ds, "match_detail_events");
        }
    }

    public void appendToMatchDetailsTypesTable(JavaRDD<MatchDetailsEvent> rdd, Time time) {
        if (!rdd.isEmpty()) {
            SparkSession spark = SparkSession.active();
            RDD<Row> rowRdd = rdd
                    .map(e -> RowFactory.create(e.getTypeId(), e.getTypeDescription()))
                    .rdd();
            Dataset<Row> newTypes = spark.sqlContext().createDataset(rowRdd, encoder)
                    .distinct()
                    .join(spark.table(viewMatchDetailTypes), matchDetailJoinColumns, "left_anti");
            if (!newTypes.isEmpty()) {
                PostgresHelper.appendDataset(newTypes, "match_detail_types");
                TempViewHelper.appendOrCreateView(viewMatchDetailTypes, newTypes.select("typeid"));
            }
        }
    }
}
