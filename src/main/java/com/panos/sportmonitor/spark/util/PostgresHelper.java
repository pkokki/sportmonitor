package com.panos.sportmonitor.spark.util;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;

public class PostgresHelper {
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            // Do nothing;
        }
    }

    public static void init() {
        // convenience method to force static constructor
    }

    public static void overwriteDataset(Dataset<Row> ds, String tableName) {
        writeDataset(ds, tableName, "overwrite", true);
    }
    public static void appendDataset(Dataset<Row> ds, String tableName) {
        writeDataset(ds, tableName, "append", false);
    }
    private static void writeDataset(Dataset<Row> ds, String tableName, String mode, Boolean truncate) {
        Arrays.stream(ds.columns())
                .filter(col -> !col.toLowerCase().equals(col))
                .forEach(col -> ds.withColumnRenamed(col, col.toLowerCase()));
        ds
                .write()
                .format("jdbc")
                .mode(mode)
                .option("truncate", truncate)
                .option("url", "jdbc:postgresql://localhost:5432/livedb")
                .option("dbtable", "public." + tableName)
                .option("user", "postgres")
                .option("password", "password")
                .option("isolationLevel", "READ_COMMITTED")
                .save();
    }

    public static Dataset<Row> readTable(SparkSession session, String tableName){
        return session.read()
                .format("jdbc")
                .option("url", "jdbc:postgresql://localhost:5432/livedb")
                .option("dbtable", "public." + tableName)
                .option("user", "postgres")
                .option("password", "password")
                .load();
    }

    public static Dataset<Row> readQuery(SparkSession session, String query){
        return session.read()
                .format("jdbc")
                .option("url", "jdbc:postgresql://localhost:5432/livedb")
                .option("query", query)
                .option("user", "postgres")
                .option("password", "password")
                .load();
    }
}
