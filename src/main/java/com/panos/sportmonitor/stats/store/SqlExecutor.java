package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.ref.NullEntity;
import com.panos.sportmonitor.stats.entities.root.NullRootEntity;
import org.apache.commons.lang3.builder.Diff;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SqlExecutor extends StatsStoreListener {

    private final HashMap<EntityId, SqlStatement> statements = new LinkedHashMap<>();
    private final List<SqlExecutorListener> listeners = new LinkedList<>();
    private final boolean printSql, execSql;
    private long relationIndex = 0;

    public SqlExecutor(boolean execSql, boolean printSql) {
        this.execSql = execSql;
        this.printSql = printSql;
    }

    @Override
    public void onCreate(BaseEntity entity) {
        if (entity instanceof NullRootEntity || entity instanceof NullEntity)
            return;
        SqlStatement stm = statements.get(entity.getId());
        if (stm != null)
            throw new IllegalArgumentException();
        stm = new InsertStatement(SqlUtils.transformTableName(entity.getClass().getSimpleName()));
        stm.addEntityId(entity.getId(), "", "id");
        statements.put(entity.getId(), stm);
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
        if (!statements.containsKey(existing.getId())) {
            SqlStatement stm = new UpdateStatement(SqlUtils.transformTableName(existing.getClass().getSimpleName()), existing.getId());
            statements.put(existing.getId(), stm);
            stm.addEntityId(existing.getId(), "", "id");
        }
    }

    @Override
    public void onPropertyChange(BaseEntity entity, String entityFieldName, Object oldValue, Object newValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            SqlStatement stm = statements.get(entity.getId());
            String sqlFieldName = SqlUtils.transform(entityFieldName);
            stm.addField(sqlFieldName, newValue);
        }
    }

    @Override
    public void onRelationChanged(BaseEntity entity, String entityFieldName, EntityId oldValue, EntityId newValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            SqlStatement stm = statements.get(entity.getId());
            for (EntityKey key : newValue.getKeys()) {
                stm.addField(SqlUtils.resolveSqlFieldName(entityFieldName, key.getName()), key.getValue());
            }
        }
    }

    @Override
    public void onRelationAdded(BaseEntity entity, String entityFieldName, EntityId id) {
        String masterTableName = SqlUtils.transformTableName(entity.getClass().getSimpleName());
        String relTableName = String.format("%s%s%s", masterTableName, SqlUtils.RELATION_SEPARATOR, SqlUtils.transform(entityFieldName));
        InsertStatement insertStm = new InsertStatement(relTableName);
        insertStm.addEntityId(entity.getId(), SqlUtils.FIELD_REL_SOURCE_PREFIX, entityFieldName);
        insertStm.addEntityId(id, SqlUtils.FIELD_REL_TARGET_PREFIX, entityFieldName);
        statements.put(new EntityId(NullEntity.class, ++relationIndex), insertStm);
    }

    public void submitChanges() {
        int sqlCounter = 0;
        int[] results = new int[0];
        try (
                Connection connection = SqlUtils.DATA_SOURCE.getConnection();
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
        if (execSql) {
            if (results.length == 0) failCounter = sqlCounter;
            for (int value : results) {
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
        }
        StatsConsole.printlnState(String.format("SQL statements in batch: Total=%d, Success=%d, SuccessNoInfo=%d, Failed=%d, TotalRowsAffected=%d",
                sqlCounter, successCounter, successNoInfoCounter, failCounter, totalRowsAffected));
        for (SqlExecutorListener listener : listeners) listener.onSqlExecutorCompleted(sqlCounter, successCounter, failCounter);
    }

    public void addSqlExecutorListener(SqlExecutorListener listener) {
        this.listeners.add(listener);
    }

    private static class SqlStatement {
        protected final String tableName;
        protected final HashMap<String, Object> fields;
        protected final Set<String> keys;

        public SqlStatement(String tableName) {
            this.tableName = tableName;
            this.fields = new LinkedHashMap<>();
            this.keys = new LinkedHashSet<>();
        }

        public void addEntityId(EntityId id, String prefix, String entityFieldName) {
            for (EntityKey key : id.getKeys()) {
                String sqlFieldName = prefix + SqlUtils.resolveSqlFieldName(entityFieldName, key.getName());
                this.addField(sqlFieldName, key.getValue());
                this.keys.add(sqlFieldName);
            }
        }

        public void addField(String name, Object value)
        {
            this.fields.put(name, value);
        }

        protected static String prepareSql(Object src) {
            if (src == null)
                return "null";
            if (src instanceof String)
                return "'" + src.toString().replace("'", "''") + "'";
            return src.toString();
        }
    }

    private static class InsertStatement extends SqlStatement {

        public InsertStatement(String tableName)
        {
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
                    fields.values().stream().map(SqlStatement::prepareSql).collect(Collectors.joining(", ")) +
                    ")" + getOnConflictDoUpdateClause() + ";";
        }

        private String getOnConflictDoUpdateClause() {
            String primaryKeyNames = String.join(", ", keys);
            List<String> fieldValues = fields.keySet().stream()
                    .filter(n -> !keys.contains(n))
                    .map(n -> n + "=" + prepareSql(fields.get(n)))
                    .collect(Collectors.toList());
            if (fieldValues.isEmpty())
                return " ON CONFLICT DO NOTHING";
            return " ON CONFLICT (" +
                    primaryKeyNames +
                    ") DO UPDATE SET " +
                    String.join(", ", fieldValues);
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
            String sqlFields = fields.entrySet().stream().map(e -> String.format("%s=%s", e.getKey(), prepareSql(e.getValue()))).collect(Collectors.joining(", "));
            return "UPDATE " +
                    tableName +
                    " SET " +
                    sqlFields +
                    " WHERE " +
                    whereId.getKeys().stream().map(k -> SqlUtils.transform(k.getName()) + "=" + prepareSql(k.getValue())).collect(Collectors.joining(" AND ")) +
                    ";";
        }
    }
}
