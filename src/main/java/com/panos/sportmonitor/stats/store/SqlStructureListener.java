package com.panos.sportmonitor.stats.store;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.*;
import org.apache.commons.lang3.builder.Diff;
import scala.Tuple3;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SqlStructureListener extends StatsStoreListener {
    private final Map<String, TableInfo> sqlData;
    private final boolean suppressFKs;

    public SqlStructureListener(boolean suppressFKs) {
        this.sqlData = new LinkedHashMap<>();
        this.suppressFKs = suppressFKs;
    }

    @Override
    public final void onCreate(BaseEntity entity) {
        createOrUpdateFieldInfo(entity, "id", entity.getId(), true);
    }

    @Override
    public final void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
    }

    @Override
    public final void onPropertyChange(BaseEntity entity, String entityFieldName, Object oldValue, Object newValue) {
        if (entity instanceof BaseRootEntity) {
            throw new IllegalStateException(String.format("%s has invalid ROOT property %s", entity.getName(), entityFieldName));
        }
        createOrUpdateFieldInfo(entity, entityFieldName, newValue, false);
    }

    @Override
    public final void onRelationChanged(BaseEntity entity, String entityFieldName, EntityId oldValue, EntityId newValue) {
        createOrUpdateFieldInfo(entity, entityFieldName, newValue, false);
    }

    @Override
    public final void onRelationAdded(BaseEntity entity, String entityFieldName, EntityId targetId) {
        throw new IllegalStateException(String.format("%s has onRelationAdded: %s --> %s", entity.getName(), entityFieldName, targetId));
//        String relTableName = SqlUtils.transformTableName(String.format("%s%s%s", entity.getClass().getSimpleName(), SqlUtils.RELATION_SEPARATOR, entityFieldName));
//        TableInfo relTableInfo = sqlData.computeIfAbsent(relTableName, TableInfo::new);
//        if (relTableInfo.getFields().isEmpty()) {
//            List<String> sqlFieldNames = new ArrayList<>();
//            for (EntityKey key : entity.getId().getKeys()) {
//                String sqlFieldName = SqlUtils.FIELD_REL_SOURCE_PREFIX + SqlUtils.resolveSqlFieldName(entityFieldName, key.getName());
//                sqlFieldNames.add(sqlFieldName);
//                createOrUpdateSingleFieldInfo(relTableInfo.getFields(), sqlFieldName, key.getValue(), true);
//            }
//            relTableInfo.addForeignKey(sqlFieldNames, entity.getId());
//
//            sqlFieldNames = new ArrayList<>();
//            for (EntityKey key : targetId.getKeys()) {
//                String sqlFieldName = SqlUtils.FIELD_REL_TARGET_PREFIX + SqlUtils.resolveSqlFieldName(entityFieldName, key.getName());
//                sqlFieldNames.add(sqlFieldName);
//                createOrUpdateSingleFieldInfo(relTableInfo.getFields(), sqlFieldName, key.getValue(), true);
//            }
//            relTableInfo.addForeignKey(sqlFieldNames, targetId);
//        }
    }

    private void createOrUpdateFieldInfo(BaseEntity entity, String entityFieldName, Object value, boolean isPK) {
        String tableName = SqlUtils.transformTableName(entity);
        TableInfo tableInfo = sqlData.computeIfAbsent(tableName, TableInfo::new);

        if (value instanceof EntityId) {
            List<String> sqlFieldNames = new ArrayList<>();
            EntityId id = (EntityId) value;
            for (EntityKey key : id.getKeys()) {
                String sqlFieldName = SqlUtils.resolveSqlFieldName(entityFieldName, key.getName());
                sqlFieldNames.add(sqlFieldName);
                createOrUpdateSingleFieldInfo(tableInfo.getFields(), sqlFieldName, key.getValue(), isPK);
            }
            if (!isPK) {
                tableInfo.addForeignKey(sqlFieldNames, id);
            }
        }
        else {
            createOrUpdateSingleFieldInfo(tableInfo.getFields(), entityFieldName, value, isPK);
        }
    }

    private void createOrUpdateSingleFieldInfo(List<FieldInfo> sqlFields, String entityFieldName, Object value, boolean isPK) {
        String sqlFieldName = SqlUtils.transform(entityFieldName);
        Optional<FieldInfo> sqlField = sqlFields.stream().filter(e -> e.name.equals(sqlFieldName)).findFirst();
        if (!sqlField.isPresent()) {
            FieldInfo fieldInfo = new FieldInfo(sqlFieldName, value, isPK);
            sqlFields.add(fieldInfo);
        }
    }

    @Override
    public final void submitChanges() {
        List<TableInfo> tables = this.orderTables();
        if (!tables.isEmpty()) {
            onComplete(tables);
        }
    }

    protected abstract void onComplete(List<TableInfo> tables);

    private List<TableInfo> orderTables() {
        List<TableInfo> tables = new LinkedList<>();
        List<String> remainingTables = sqlData.keySet().stream().filter(e -> !e.startsWith("null")).collect(Collectors.toList());
        List<String> processedTables = new ArrayList<>();
        int cycleProcessed;
        do {
            cycleProcessed = 0;
            for (String tableName : Lists.newArrayList(remainingTables)) {
                TableInfo tableInfo = sqlData.get(tableName);
                if (tableInfo.getForeignKeys().stream().map(Tuple3::_2).allMatch(
                        name -> name.equals(tableName) || name.equals(SqlUtils.ROOT_ENTRIES_TABLE) || processedTables.contains(name) || !remainingTables.contains(name)
                )) {
                    tables.add(tableInfo);
                    processedTables.add(tableName);
                    remainingTables.remove(tableName);
                    ++cycleProcessed;
                }
            }
        } while (remainingTables.size() > 0 && cycleProcessed > 0);
        if (remainingTables.size() > 0) {
            StringBuilder ex = new StringBuilder().append("\nProcessed tables: ").append(processedTables).append("\nRemaining tables:");
            for (final String tableName : remainingTables) {
                TableInfo tableInfo = sqlData.get(tableName);
                ex.append("\n  TABLE ").append(tableName).append(": ");
                tableInfo.getForeignKeys().forEach(fk -> {
                    String name = fk._2();
                    if (!processedTables.contains(name)) {
                        ex.append(name).append(remainingTables.contains(name) ? "" : "?").append("  ");
                    }
                });
            }
            StatsConsole.printlnWarn("Cycle in foreign keys!!" + ex.toString());
        }
        if (suppressFKs)
            tables.addAll(remainingTables.stream().map(sqlData::get).collect(Collectors.toList()));
        return tables;
    }

    protected void appendTable(StringBuilder sb, TableInfo tableInfo) {
        List<FieldInfo> fields = tableInfo.getFields();
        sb.append("DROP TABLE IF EXISTS ").append(tableInfo.name).append(";").append("\n");
        sb.append("CREATE TABLE ").append(tableInfo.name).append(" (").append("\n");
        fields.forEach(fieldInfo -> sb.append("\t").append(fieldInfo.name).append(" ").append(fieldInfo.resolveType()).append(",\n"));
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

    protected static class TableInfo implements Comparable<TableInfo> {
        public final String name;
        private final List<FieldInfo> fields;
        private final List<Tuple3<String, String, String>> foreignKeys;

        public TableInfo(String name) {
            this.name = name;
            this.fields = new LinkedList<>();
            this.foreignKeys = new LinkedList<>();
        }

        public void addForeignKey(List<String> sourceFieldNames, EntityId targetId) {
            String sourceExpression = String.join(", ", sourceFieldNames);
            if (foreignKeys.stream().anyMatch(t -> t._1().equals(sourceExpression)))
                return;
            String targetTableName = SqlUtils.transformTableName(targetId);
            List<String> targetFields = targetId.getKeys().stream().map(k -> SqlUtils.transform(k.getName())).collect(Collectors.toList());
            Tuple3<String, String, String> entry = new Tuple3<>(sourceExpression, targetTableName, String.join(", ", targetFields));
            foreignKeys.add(entry);
        }

        public List<FieldInfo> getFields() {
            return this.fields;
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

    protected static class FieldInfo {
        public FieldInfo(String name, Object value, boolean isPK) {
            this.name = name;
            this.type = value.getClass().getSimpleName();
            this.isPK = isPK;
        }
        public final String name;
        public final String type;
        public final boolean isPK;

        public String getSqlType() {
            switch (type) {
                case "Integer":
                case "int":
                    return "int";
                case "Long":
                case "long":
                    return "bigint";
                case "double":
                case "Double":
                    return "real";
                case "Boolean":
                case "boolean":
                    return "boolean";
                case "String":
                    return "text";
                default: throw new IllegalArgumentException(String.format("SqlBuilderListener.resolveType: unknown field type %s", type));
            }
        }

        public String resolveType() {
            StringBuilder sb = new StringBuilder();
            sb.append(getSqlType());
            if (isPK) {
                sb.append(" NOT NULL");
                if (type.equals("String"))
                    sb.append(" CONSTRAINT not_empty_").append(name).append(" CHECK (char_length(").append(name).append(") > 0)");
                else
                    sb.append(" CONSTRAINT positive_").append(name).append(" CHECK (").append(name).append(" > 0)");
            }
            return sb.toString();
        }
    }
}
