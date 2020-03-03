package com.panos.sportmonitor.spark.util;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static boolean execute(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/livedb", "postgres", "password");
            stmt = conn.createStatement();
            return stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
            }
        }
    }

    public static int[] execute(Iterable<String> statements) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/livedb", "postgres", "password");
            stmt = conn.createStatement();
            for (String sql : statements) stmt.addBatch(sql);
            return stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return new int[0];
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
            }
        }
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
