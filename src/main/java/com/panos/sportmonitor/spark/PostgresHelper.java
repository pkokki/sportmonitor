package com.panos.sportmonitor.spark;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PostgresHelper {
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            // Do nothing;
        }
    }

    public static void overwriteDataset(Dataset<Row> ds, String tableName) {
        writeDataset(ds, tableName, "overwrite", true);
    }
    public static void appendDataset(Dataset<Row> ds, String tableName) {
        writeDataset(ds, tableName, "append", false);
    }
    private static void writeDataset(Dataset<Row> ds, String tableName, String mode, Boolean truncate) {
        ds.write()
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
}
