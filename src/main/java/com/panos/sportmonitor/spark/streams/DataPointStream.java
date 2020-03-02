package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.dto.DataPoint;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

public class DataPointStream extends AbstractJavaStream<DataPoint>  {
    public DataPointStream(JavaDStream<DataPoint> stream) {
        super(stream);
    }

    public void appendToDataPointTable(JavaRDD<DataPoint> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, DataPoint.class);
            PostgresHelper.appendDataset(ds, "data_points");
        }
    }
}
