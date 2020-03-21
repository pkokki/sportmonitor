package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.StatsConsole;
import com.panos.sportmonitor.stats.entities.ref.NullEntity;
import com.panos.sportmonitor.stats.entities.root.NullRootEntity;
import org.apache.commons.lang3.builder.Diff;
import org.postgresql.ds.PGPoolingDataSource;
import scala.Tuple2;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SqlExecutor extends StatsStoreListener {
    private static final PGPoolingDataSource source = new PGPoolingDataSource();

    static {
        source.setDataSourceName("livestats");
        source.setServerName("localhost:5432");
        source.setDatabaseName("livestats");
        source.setUser("postgres");
        source.setPassword("password");
        source.setMaxConnections(20);
    }

    private final HashMap<Tuple2<String, EntityId>, SqlStatement> statements = new LinkedHashMap<>();
    private boolean printSql, execSql;
    private long relationIndex = 0;

    public SqlExecutor(boolean execSql, boolean printSql) {
        this.execSql = execSql;
        this.printSql = printSql;
    }

    private Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public void onCreate(BaseEntity entity) {
        if (entity instanceof NullRootEntity || entity instanceof NullEntity)
            return;
        Tuple2<String, EntityId> key = getKey(entity);
        SqlStatement stm = statements.get(key);
        if (stm != null)
            throw new IllegalArgumentException();
        stm = new InsertStatement(SqlUtils.transformTableName(entity.getClass().getSimpleName()));
        addEntityId(stm, entity.getId(), "");
        statements.put(key, stm);
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
        Tuple2<String, EntityId> key = getKey(existing);
        if (!statements.containsKey(key)) {
            SqlStatement stm = new UpdateStatement(SqlUtils.transformTableName(existing.getClass().getSimpleName()), existing.getId());
            statements.put(key, stm);
            addEntityId(stm, existing.getId(), "");
        }
    }

    @Override
    public void onPropertyChange(BaseEntity entity, String entityFieldName, Object oldValue, Object newValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            SqlStatement stm = getSqlStatement(entity);
            String sqlFieldName = SqlUtils.transform(entityFieldName);
            stm.addField(sqlFieldName, newValue);
        }
    }

    @Override
    public void onRelationChanged(BaseEntity entity, String entityFieldName, EntityId oldValue, EntityId newValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            SqlStatement stm = getSqlStatement(entity);
            String sqlFieldName = SqlUtils.transform(entityFieldName);
            stm.addField(sqlFieldName, newValue.getId());
            if (newValue.isComposite()) {
                String tsPrefix = sqlFieldName;
                if (sqlFieldName.endsWith("id"))
                    tsPrefix = sqlFieldName.substring(0, sqlFieldName.length() - 2);
                stm.addField(tsPrefix + SqlUtils.FIELD_TIMESTAMP, newValue.getTimeStamp());
            }
        }
    }

    @Override
    public void onRelationAdded(BaseEntity entity, String entityFieldName, EntityId id) {
        String masterTableName = SqlUtils.transformTableName(entity.getClass().getSimpleName());
        String relTableName = String.format("%s%s%s", masterTableName, SqlUtils.RELATION_SEPARATOR, SqlUtils.transform(entityFieldName));
        Tuple2<String, EntityId> key = new Tuple2<>(relTableName, new EntityId(++relationIndex, NullEntity.class));
        InsertStatement insertStm = new InsertStatement(relTableName);
        addEntityId(insertStm, entity.getId(), SqlUtils.FIELD_REL_SOURCE_PREFIX);
        addEntityId(insertStm, id, SqlUtils.FIELD_REL_TARGET_PREFIX);
        statements.put(key, insertStm);
    }

    private void addEntityId(SqlStatement statement, EntityId id, String prefix) {
        statement.addField(prefix + SqlUtils.FIELD_ID, id.getId());
        if (id.isComposite())
            statement.addField(prefix + SqlUtils.FIELD_TIMESTAMP, id.getTimeStamp());
    }

    private Tuple2<String, EntityId> getKey(BaseEntity entity) {
        return new Tuple2<>(entity.getClass().getSimpleName(), entity.getId());
    }
    private SqlStatement getSqlStatement(BaseEntity entity) {
        return statements.get(getKey(entity));
    }

    public void submitChanges() {
        int sqlCounter = 0;
        int[] results = new int[0];
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            for (SqlStatement sqlStm : statements.values()) {
                String sql = sqlStm.toString();
                if (sql != null) {
                    if (printSql) StatsConsole.printlnState(sql);
                    statement.addBatch(sql);
                    ++sqlCounter;
                }
            }
            if (execSql) results = statement.executeBatch();
        }
        catch (SQLException e) {
            while (e != null) {
                StatsConsole.printlnError(e.getMessage());
                e = e.getNextException();
            }
        }
        int successCounter = 0, successNoInfoCounter = 0, totalRowsAffected = 0, failCounter = 0;
        for (int i = 0; i < results.length; i++) {
            int value = results[i];
            // A number greater than or equal to zero -- indicates that the command was processed successfully and is
            // an update count giving the number of rows in the database that were affected by the command's execution
            if (value >= 0) {
                ++successCounter;
                totalRowsAffected += value;
            }
            // A value of SUCCESS_NO_INFO -- indicates that the command was processed successfully but that the number
            // of rows affected is unknown. If one of the commands in a batch update fails to execute properly, this
            // method throws a BatchUpdateException, and a JDBC driver may or may not continue to process the remaining
            // commands in the batch. However, the driver's behavior must be consistent with a particular DBMS, either
            // always continuing to process commands or never continuing to process commands. If the driver continues
            // processing after a failure, the array returned by the method BatchUpdateException.getUpdateCounts will
            // contain as many elements as there are commands in the batch, and at least one of the elements will be
            // the following:
            else if (value == Statement.SUCCESS_NO_INFO) {
                ++successNoInfoCounter;
            }
            // A value of EXECUTE_FAILED -- indicates that the command failed to execute successfully and occurs only
            // if a driver continues to process commands after a command fails
            else if (value == Statement.EXECUTE_FAILED) {
                ++failCounter;
            }
        }
        StatsConsole.printlnState(String.format("SQL statements in batch: Total=%d, Success=%d, SuccessNoInfo=%d, Failed=%d, TotalRowsAffected=%d",
                sqlCounter, successCounter, successNoInfoCounter, failCounter, totalRowsAffected));
    }

    private static class SqlStatement {
        protected final String tableName;
        protected final HashMap<String, Object> fields;

        public SqlStatement(String tableName) {
            this.tableName = tableName;
            this.fields = new LinkedHashMap<>();
        }
        public void addField(String name, Object value) {
            this.fields.put(name, value);
        }
    }

    private static class InsertStatement extends SqlStatement {

        public InsertStatement(String tableName) {
            super(tableName);
        }

        @Override
        public String toString() {
            if (fields.isEmpty()) return null;
            return "INSERT INTO " +
                    tableName +
                    " (" +
                    String.join(", ", fields.keySet()) +
                    ") VALUES (" +
                    fields.values().stream().map(SqlUtils::prepareSql).collect(Collectors.joining(", ")) +
                    ") ON CONFLICT DO NOTHING;";
        }
    }

    private static class UpdateStatement extends SqlStatement {
        private final EntityId whereId;

        public UpdateStatement(String tableName, EntityId whereId) {
            super(tableName);
            this.whereId = whereId;
        }

        @Override
        public String toString() {
            if (fields.isEmpty()) return null;
            String sqlFields = fields.entrySet().stream().map(e -> String.format("%s=%s", e.getKey(), SqlUtils.prepareSql(e.getValue()))).collect(Collectors.joining(", "));
            return "UPDATE " +
                    tableName +
                    " SET " +
                    sqlFields +
                    " WHERE " + SqlUtils.FIELD_ID + "=" + whereId.getId() +
                    (whereId.isComposite() ? String.format(" AND %s=%d", SqlUtils.FIELD_TIMESTAMP, whereId.getTimeStamp()) : "") +
                    ";";
        }
    }
}
