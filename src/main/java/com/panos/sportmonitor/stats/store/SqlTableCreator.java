package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.*;
import org.apache.commons.lang3.builder.Diff;
import scala.Tuple3;

import java.util.*;
import java.util.stream.Collectors;

public class SqlTableCreator extends StatsStoreListener {
    private final Map<String, TableInfo> sqlData;
    private final boolean suppressFKs;

    public SqlTableCreator(boolean suppressFKs) {
        this.sqlData = new LinkedHashMap<>();
        this.suppressFKs = suppressFKs;
    }

    @Override
    public void onCreate(BaseEntity entity) {
        createOrUpdateFieldInfo(entity, "id", entity.getId(), true);
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
    }

    @Override
    public void onPropertyChange(BaseEntity entity, String entityFieldName, Object oldValue, Object newValue) {
        createOrUpdateFieldInfo(entity, entityFieldName, newValue, false);
    }

    @Override
    public void onRelationChanged(BaseEntity entity, String entityFieldName, EntityId oldValue, EntityId newValue) {
        createOrUpdateFieldInfo(entity, entityFieldName, newValue, false);
    }

    @Override
    public void onRelationAdded(BaseEntity entity, String entityFieldName, EntityId targetId) {
        String relTableName = SqlUtils.transformTableName(String.format("%s%s%s", entity.getClass().getSimpleName(), SqlUtils.RELATION_SEPARATOR, entityFieldName));
        TableInfo relTableInfo = sqlData.computeIfAbsent(relTableName, TableInfo::new);
        if (relTableInfo.getFields().isEmpty()) {
            List<String> sqlFieldNames = new ArrayList<>();
            for (EntityKey key : entity.getId().getKeys()) {
                String sqlFieldName = SqlUtils.FIELD_REL_SOURCE_PREFIX + SqlUtils.resolveSqlFieldName(entityFieldName, key.getName());
                sqlFieldNames.add(sqlFieldName);
                createOrUpdateSingleFieldInfo(relTableInfo.getFields(), sqlFieldName, key.getValue(), true);
            }
            relTableInfo.addForeignKey(sqlFieldNames, entity.getId());

            sqlFieldNames = new ArrayList<>();
            for (EntityKey key : targetId.getKeys()) {
                String sqlFieldName = SqlUtils.FIELD_REL_TARGET_PREFIX + SqlUtils.resolveSqlFieldName(entityFieldName, key.getName());
                sqlFieldNames.add(sqlFieldName);
                createOrUpdateSingleFieldInfo(relTableInfo.getFields(), sqlFieldName, key.getValue(), true);
            }
            relTableInfo.addForeignKey(sqlFieldNames, targetId);
        }
    }

    private void createOrUpdateFieldInfo(BaseEntity entity, String entityFieldName, Object value, boolean isPK) {
        String tableName = SqlUtils.transformTableName(entity.getClass().getSimpleName());
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
        if (sqlField.isPresent()) {
            sqlField.get().updateSize(value);
        }
        else {
            FieldInfo fieldInfo = new FieldInfo(sqlFieldName, value, isPK);
            sqlFields.add(fieldInfo);
        }
    }

    @Override
    public void submitChanges() {
        StringBuilder sb = new StringBuilder();
        List<String> remainingTables = new ArrayList<>(sqlData.keySet());
        List<String> processedTables = new ArrayList<>();
        int cycleProcessed;
        do {
            cycleProcessed = 0;
            for (String tableName : sqlData.keySet()) {
                if (processedTables.contains(tableName))
                    continue;

                TableInfo tableInfo = sqlData.get(tableName);
                if (tableInfo.foreignKeys.size() == 0 || tableInfo.foreignKeys.stream().allMatch(e -> e._2().equals(tableName)
                        || !remainingTables.contains(e._2())
                        || processedTables.contains(e._2()))) {
                    createTable(sb, tableInfo, tableInfo.getFields());
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
                tableInfo.foreignKeys.forEach(fk -> { if (!processedTables.contains(fk._2())) ex.append(fk._2()).append("  "); });
            }
            throw new IllegalStateException("Cycle in foreign keys!!" + ex.toString());
        }
        StatsConsole.printlnState(sb.toString());
    }

    private void createTable(StringBuilder sb, TableInfo tableInfo, List<FieldInfo> fields) {
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

    private static class TableInfo implements Comparable<TableInfo> {
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
            String targetTableName = SqlUtils.transformTableName(targetId.getEntityClass().getSimpleName());
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
    private static class FieldInfo {
        public FieldInfo(String name, Object value, boolean isPK) {
            this.name = name;
            this.type = value.getClass().getSimpleName();
            updateSize(value);
            this.isPK = isPK;
        }
        public final String name;
        public final String type;
        public final boolean isPK;
        public int size;

        public void updateSize(Object value) {
            if (value instanceof String)
                size = Math.max(size, value.toString().length());
        }
        public String resolveType() {
            StringBuilder sb = new StringBuilder();
            switch (type) {
                case "Integer":
                case "int":
                    sb.append("int"); break;
                case "Long":
                case "long":
                    sb.append("bigint"); break;
                case "double":
                case "Double":
                    sb.append("real"); break;
                case "Boolean":
                case "boolean":
                    sb.append("boolean"); break;
                case "String":
                    sb.append("varchar(").append(2*(int)Math.pow(2, Math.ceil(Math.log(size)/Math.log(2)))).append(")"); break;
                default: throw new IllegalArgumentException(String.format("SqlBuilderListener.resolveType: unknown field type %s", type));
            }
            if (isPK) {
                sb.append(" NOT NULL");
                if (type.equals("String"))
                    sb.append(" CONSTRAINT not_empty_").append(name).append(" CHECK (").append(name).append(" <> '')");
                else
                    sb.append(" CONSTRAINT positive_").append(name).append(" CHECK (").append(name).append(" > 0)");
            }
            return sb.toString();
        }
    }
}
