package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.dto.MarketMasterData;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

public class MarketMasterDataStream extends AbstractJavaStream<MarketMasterData> {
    public MarketMasterDataStream(JavaDStream<MarketMasterData> stream) {
        super(stream);
    }

    public void appendToMarketMasterTable(JavaRDD<MarketMasterData> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, MarketMasterData.class);
            PostgresHelper.appendDataset(ds, "market_master_data");
        }
    }
}
