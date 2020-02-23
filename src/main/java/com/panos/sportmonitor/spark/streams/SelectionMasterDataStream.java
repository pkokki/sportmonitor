package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.dto.EventData;
import com.panos.sportmonitor.spark.dto.SelectionMasterData;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

public class SelectionMasterDataStream extends AbstractJavaStream<SelectionMasterData> {
    public SelectionMasterDataStream(JavaDStream<SelectionMasterData> stream) {
        super(stream);
    }

    public void appendToSelectionMasterTable(JavaRDD<SelectionMasterData> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, SelectionMasterData.class);
            PostgresHelper.appendDataset(ds, "selection_master_data");
        }
    }
}
