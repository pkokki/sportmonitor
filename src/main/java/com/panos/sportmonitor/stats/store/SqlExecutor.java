package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.StatsConsole;
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
    private boolean printSql;
    private long relationIndex = 0;

    public SqlExecutor(boolean printSql) {
        this.printSql = printSql;
    }

    private Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public void onCreate(BaseEntity entity) {
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
        Tuple2<String, EntityId> key = new Tuple2<>(relTableName, new EntityId(++relationIndex));
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
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            for (SqlStatement sqlStm : statements.values()) {
                String sql = sqlStm.toString();
                if (sql != null) {
                    if (printSql) StatsConsole.printlnState(sql);
                    statement.addBatch(sql);
                }
            }
            statement.executeBatch();
        }
        catch (SQLException e) {
            while (e != null) {
                StatsConsole.printlnError(e.getMessage());
                e = e.getNextException();
            }
        }
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
                    ");"; // ON CONFLICT DO NOTHING
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
