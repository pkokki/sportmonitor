package com.panos.sportmonitor.spark.util;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;

import java.util.Date;

public class TempViewHelper {
    public static void init() {
    }

    public static void appendOrCreateView(String viewName, Dataset<Row> newData, Time time) {
        SparkSession spark = SparkSession.active();
        if (spark.catalog().tableExists(viewName)) {
            Dataset<Row> existingData = spark.table(viewName);
            Dataset<Row> union = existingData.union(newData);
            union.createOrReplaceTempView(viewName);
            //System.out.println(String.format("TempView %s contains %d rows (%d new)", viewName, union.count(), newData.count()));
        }
        else {
            newData.createOrReplaceTempView(viewName);
            //System.out.println(String.format("TempView %s created with %d rows", viewName, newData.count());
        }
    }
}
