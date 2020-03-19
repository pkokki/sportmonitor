package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.Diff;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SqlExecutor extends StatsStoreListener {
    private boolean printSql;
    private final List<String> statements;
    private static final PGPoolingDataSource source = new PGPoolingDataSource();

    static {
        source.setDataSourceName("livestats");
        source.setServerName("localhost:5432");
        source.setDatabaseName("livestats");
        source.setUser("postgres");
        source.setPassword("password");
        source.setMaxConnections(20);
    }

    public SqlExecutor(boolean printSql) {
        this.printSql = printSql;
        this.statements = new LinkedList<>();
    }

    private Connection getConnection() throws SQLException {
        return source.getConnection();
    }

//    public void addStatement(String sql) {
//        if (sql == null || sql.isEmpty()) return;
//        if (printSql)
//            StatsConsole.printlnState(sql);
//        statements.add(sql);
//    }

    @Override
    public void onCreate(BaseEntity entity) {
        //throw new IllegalStateException();
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
        //throw new IllegalStateException();
    }

    public void submitChanges() {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
        ) {
            for (String sql : statements) {
                if (printSql) StatsConsole.printlnState(sql);
                statement.addBatch(sql);
            }
            //statement.executeBatch();
        }
        catch (SQLException e) {
            while (e != null) {
                StatsConsole.printlnError(e.getMessage());
                e = e.getNextException();
            }
        }
    }
}
