package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.*;
import org.apache.commons.lang3.builder.Diff;
import scala.Tuple3;

import java.util.*;
import java.util.stream.Collectors;

public class SqlTableCreator extends StatsStoreListener {
    private final Map<TableInfo, List<FieldInfo>> sqlData;
    private final boolean suppressFKs;

    public SqlTableCreator(boolean suppressFKs) {
        this.sqlData = new LinkedHashMap<>();
        this.suppressFKs = suppressFKs;
    }

    @Override
    public void onCreate(BaseEntity entity) {
        List<FieldInfo> sqlFields = getTableFields(getTableName(entity));
        if (sqlFields.isEmpty()) {
            EntityId id = entity.getId();
            if (id.isMultiple()) {
                for (EntityKey key : ((CompositeId)id).getKeys())
                    sqlFields.add(new FieldInfo(SqlUtils.transform(key.getName()), "Long", 0, true));
            } else {
                sqlFields.add(new FieldInfo(SqlUtils.FIELD_ID, "Long", 0, true));
                if (entity.getId().isComposite())
                    sqlFields.add(new FieldInfo(SqlUtils.FIELD_TIMESTAMP, "Long", 0, true));
            }
        }
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
    }

    @Override
    public void onPropertyChange(BaseEntity entity, String entityFieldName, Object oldValue, Object newValue) {
        createOrUpdateFieldInfo(entity, entityFieldName, newValue);
    }

    @Override
    public void onRelationChanged(BaseEntity entity, String entityFieldName, EntityId oldValue, EntityId newValue) {
        boolean isNew = createOrUpdateFieldInfo(entity, entityFieldName, newValue.getId());
        if (isNew) {
            TableInfo tableInfo = getTableInfo(entity);
            String fkTable = SqlUtils.transformTableName(newValue.getEntityClass().getSimpleName());
            if (newValue.isComposite()) {
                String tsPrefix = entityFieldName;
                if (entityFieldName.endsWith("Id"))
                    tsPrefix = entityFieldName.substring(0, entityFieldName.length() - 2);
                String tsFieldName = tsPrefix + "_" + SqlUtils.FIELD_TIMESTAMP;
                createOrUpdateFieldInfo(entity, tsFieldName, newValue.getTimeStamp());
                tableInfo.addForeignKey(
                        SqlUtils.transform(entityFieldName) + ", " + SqlUtils.transform(tsFieldName),
                        fkTable,
                        SqlUtils.FIELD_ID + ", " + SqlUtils.FIELD_TIMESTAMP);
            } else {
                tableInfo.addForeignKey(SqlUtils.transform(entityFieldName), fkTable, SqlUtils.FIELD_ID);
            }
        }
    }

    @Override
    public void onRelationAdded(BaseEntity entity, String entityFieldName, EntityId id) {
        String tableName = getTableName(entity);
        String relTableName = String.format("%s%s%s", tableName, SqlUtils.RELATION_SEPARATOR, SqlUtils.transformTableName(entityFieldName));
        List<FieldInfo> sqlFields = getTableFields(relTableName);
        if (sqlFields.isEmpty()) {
            sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_SOURCE_PREFIX + SqlUtils.FIELD_ID, "Long", 0, true));
            if (entity.getId().isComposite())
                sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_SOURCE_PREFIX + SqlUtils.FIELD_TIMESTAMP, "Long", 0, true));
            sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_TARGET_PREFIX + SqlUtils.FIELD_ID, "Long", 0, true));
            if (id.isComposite())
                sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_TARGET_PREFIX + SqlUtils.FIELD_TIMESTAMP, "Long", 0, true));
        }
    }

    @Override
    public void submitChanges() {
        StringBuilder sb = new StringBuilder();
        List<TableInfo> remainingTables = new ArrayList<>(sqlData.keySet());
        List<String> processedTables = new ArrayList<>();
        int cycleProcessed;
        int remainingCount = remainingTables.size();
        do {
            cycleProcessed = 0;
            for (TableInfo tableInfo : sqlData.keySet()) {
                if (suppressFKs || !processedTables.contains(tableInfo.name)
                        && (tableInfo.foreignKeys.size() == 0 || tableInfo.foreignKeys.stream().allMatch(e -> e._2().equals(tableInfo.name) || processedTables.contains(e._2())))) {
                    createTable(sb, tableInfo, sqlData.get(tableInfo));
                    processedTables.add(tableInfo.name);
                    remainingTables.remove(tableInfo);
                    ++cycleProcessed;
                    --remainingCount;
                }
            }
        } while (remainingTables.size() > 0 && cycleProcessed > 0);
        if (remainingTables.size() > 0) {
            StringBuilder ex = new StringBuilder();
            for (final TableInfo t : remainingTables) {
                ex.append("\n  ").append(t.name).append(": ");
                t.foreignKeys.forEach(fk -> { if (!processedTables.contains(fk._2())) ex.append(fk._2()).append("  "); });
            }
            throw new IllegalStateException("Cycle in foreign keys!!" + ex.toString());
        }
        StatsConsole.printlnState(sb.toString());
    }

    private void createTable(StringBuilder sb, TableInfo tableInfo, List<FieldInfo> fields) {
        sb.append("DROP TABLE IF EXISTS ").append(tableInfo.name).append(";").append("\n");
        sb.append("CREATE TABLE ").append(tableInfo.name).append(" (").append("\n");
        fields.forEach(fieldInfo -> sb.append("\t").append(fieldInfo.name).append(" ").append(resolveType(fieldInfo)).append(",\n"));
        sb.append("\tPRIMARY KEY (").append(fields.stream().filter(e -> e.isPK).map(e -> e.name).collect(Collectors.joining(", "))).append(")");
        tableInfo.getForeignKeys().forEach(e -> sb.append("\n\t")
                .append(suppressFKs ? "-- " : "")
                .append(", FOREIGN KEY (")
                .append(e._1())
                .append(") REFERENCES ")
                .append(e._2())
                .append(" (")
                .append(e._3())
                .append(")"));
        sb.append("\n);\n");
    }

    private String getTableName(BaseEntity entity) {
        return SqlUtils.transformTableName(entity.getClass().getSimpleName());
    }

    private TableInfo getTableInfo(BaseEntity entity) {
        String tableName = getTableName(entity);
        return sqlData.keySet().stream().filter(e -> e.name.equals(tableName)).findFirst().get();
    }

    private List<FieldInfo> getTableFields(String tableName) {
        TableInfo tableInfo = new TableInfo(tableName);
        return sqlData.computeIfAbsent(tableInfo, k -> new LinkedList<>());
    }

    private boolean createOrUpdateFieldInfo(BaseEntity entity, String entityFieldName, Object value) {
        String tableName = getTableName(entity);
        List<FieldInfo> sqlFields = getTableFields(tableName);
        String sqlFieldName = SqlUtils.transform(entityFieldName);
        Optional<FieldInfo> sqlField = sqlFields.stream().filter(e -> e.name.equals(sqlFieldName)).findFirst();
        FieldInfo fieldInfo;
        if (sqlField.isPresent()) {
            fieldInfo = sqlField.get();
            fieldInfo.size = Math.max(fieldInfo.size, calcSize(value));
            return false;
        }
        else {
            if (sqlFieldName.startsWith(SqlUtils.FIELD_REL_SOURCE_PREFIX) || sqlFieldName.startsWith(SqlUtils.FIELD_REL_TARGET_PREFIX))
                throw new IllegalArgumentException("Invalid field name: " + sqlFieldName);
            fieldInfo = new FieldInfo(sqlFieldName, value.getClass().getSimpleName(), calcSize(value), false);
            sqlFields.add(fieldInfo);
            return true;
        }
    }

    private static class TableInfo implements Comparable<TableInfo> {
        public final String name;
        private final List<Tuple3<String, String, String>> foreignKeys;

        public TableInfo(String name) {
            this.name = name;
            this.foreignKeys = new LinkedList<>();
        }

        public void addForeignKey(String ownFields, String targetTable, String targetFields) {
            foreignKeys.add(new Tuple3<>(ownFields, targetTable, targetFields));
        }

        public List<Tuple3<String, String, String>> getForeignKeys() {
            return this.foreignKeys;
        }

        @Override
        public int compareTo(TableInfo o) {
            return name.compareTo(o.name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableInfo tableInfo = (TableInfo) o;
            return name.equals(tableInfo.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return name;
        }
    }
    private static class FieldInfo {
        public FieldInfo(String name, String type, int size, boolean isPK) {
            this.name = name;
            this.type = type;
            this.size = size;
            this.isPK = isPK;
        }
        public final String name;
        public final String type;
        public final boolean isPK;
        public int size;
    }

    private int calcSize(Object value) {
        if (value instanceof String) {
            return value.toString().length();
        }
        return 0;
    }

    private String resolveType(FieldInfo fieldInfo) {
        StringBuilder sb = new StringBuilder();
        switch (fieldInfo.type) {
            case "Integer": sb.append("int"); break;
            case "Long":
            case "long":
                sb.append("bigint"); break;
            case "Double": sb.append("real"); break;
            case "Boolean": sb.append("boolean"); break;
            case "String": sb.append("varchar(").append(2*(int)Math.pow(2, Math.ceil(Math.log(fieldInfo.size)/Math.log(2)))).append(")"); break;
            default: throw new IllegalArgumentException(String.format("SqlBuilderListener.resolveType: unknown field type %s", fieldInfo.type));
        }
        if (fieldInfo.isPK)
            sb.append(" NOT NULL CONSTRAINT positive_").append(fieldInfo.name).append(" CHECK (").append(fieldInfo.name).append(" > 0)");
        return sb.toString();
    }
}
