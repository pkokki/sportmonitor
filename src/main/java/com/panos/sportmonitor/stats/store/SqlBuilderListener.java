package com.panos.sportmonitor.stats.store;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.*;
import org.apache.commons.lang3.builder.Diff;
import scala.Tuple2;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class SqlBuilderListener<T extends BaseEntity> implements IStatsStoreListener<T> {
    private static final List<String> IGNORED_FIELDS = Lists.newArrayList("auxId", "childEntities", "parent", "IGNORED");
    private static final String FIELD_ID = "id";
    private static final String FIELD_TIMESTAMP = "time_stamp";
    private static final String FIELD_REL_SOURCE = "src_id";
    private static final String FIELD_REL_TARGET = "dest_id";
    private static final String RELATION_SEPARATOR = "__";
    private static final String IGNORED_FIELD_PREFIX = "__";

    private final boolean flagDDL;

    public SqlBuilderListener(boolean flagDDL) {
        this.flagDDL = flagDDL;
    }
    @Override
    public void onSubmit(T entity) {
    }

    @Override
    public void onCreate(T entity) {
        Class<? extends BaseEntity> objectType = entity.getClass();
        EntityId objectId = entity.getId();
        String tableName = SqlUtils.transformTableName(objectType.getSimpleName());
        List<Tuple2<String, Object>> fields = new ArrayList<>();
        List<Tuple2<String, Object>> relations = new ArrayList<>();
        List<Field> objFields = SqlUtils.getAllFields(new LinkedList<>(), objectType);
        for (Field objField : objFields) {
            String fieldName = objField.getName();
            if (IGNORED_FIELDS.contains(fieldName) || fieldName.startsWith(IGNORED_FIELD_PREFIX))
                continue;
            try {
                final boolean accessible = objField.isAccessible();
                objField.setAccessible(true);
                try {
                    Object fieldValue = objField.get(entity);
                    if (fieldValue != null) {
                        String fieldType = fieldValue.getClass().getSimpleName();
                        switch (fieldType) {
                            case "Integer":
                            case "Long":
                            case "Double":
                            case "Boolean":
                            case "EntityId":
                            case "String":
                                fields.add(new Tuple2<>(SqlUtils.transform(fieldName), fieldValue));
                                break;
                            case "EntityIdList":
                                EntityIdList ids = (EntityIdList) fieldValue;
                                String relationFieldName = SqlUtils.transform(fieldName);
                                for (EntityId id : ids) {
                                    relations.add(new Tuple2<>(relationFieldName, id));
                                }
                                break;
                            default:
                                StatsConsole.printlnError(String.format("AbstractEntityMap.onCreate: %s contains unhandled field '%s' of type '%s': %s", objectType, fieldName, fieldType, fieldValue));
                        }
                    }
                } finally {
                    objField.setAccessible(accessible);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                StatsConsole.printlnError(String.format("AbstractEntityMap.onCreate %s: error creating SQL %s -> %s", objectType.getSimpleName(), fieldName, e));
            }
        }
        StringBuilder sb = new StringBuilder();
        AppendInsert(sb, tableName, entity instanceof BaseTimeEntity, fields);
        for (Tuple2<String, Object> relation: relations) {
            String relTableName = String.format("%s%s%s", tableName, RELATION_SEPARATOR, relation._1);
            List<Tuple2<String, Object>> relFields = new ArrayList<>();
            relFields.add(new Tuple2<>(FIELD_REL_SOURCE, objectId));
            relFields.add(new Tuple2<>(FIELD_REL_TARGET, relation._2));
            AppendInsert(sb, relTableName, false, relFields);
        }
        //String sql = sb.toString();
        //StatsConsole.printlnState(sql);
    }

    @Override
    public void onUpdate(T existing, T submitted, List<Diff<?>> changes) {
        final Class<? extends BaseEntity> objType = existing.getClass();
        for (Diff<?> change : changes) {
            try {
                final Field field = objType.getDeclaredField(change.getFieldName());
                final boolean accessible = field.isAccessible();
                field.setAccessible(true);
                try {
                    final String fieldType = field.getType().getSimpleName();
                    switch (fieldType) {
                        case "Integer":
                        case "Long":
                        case "Double":
                        case "Boolean":
                        case "EntityId":
                        case "String":
                            field.set(existing, change.getRight());
                            break;
                        case "EntityIdList":
                            EntityIdList target = (EntityIdList)field.get(existing);
                            EntityIdList source = (EntityIdList)field.get(submitted);
                            for (EntityId item : source) {
                                if (!target.contains(item)) {
                                    target.add(item);
                                }
                            }
                            break;
                        case "HashMap":
                            HashMap targetHashMap = (HashMap)field.get(existing);
                            HashMap sourceHashMap = (HashMap)field.get(submitted);
                            for (Object key : sourceHashMap.keySet()) {
                                if (!targetHashMap.containsKey(key)) {
                                    targetHashMap.put(key, sourceHashMap.get(key));
                                }
                            }
                            break;
                        default:
                            StatsConsole.printlnWarn(String.format("AbstractEntityMap.onUpdate %s: cannot apply changes to field '%s' of type '%s': %s", objType.getSimpleName(), change.getFieldName(), fieldType, change.getRight()));
                    }
                } finally {
                    field.setAccessible(accessible);
                }
            } catch (NoSuchFieldException
                    | SecurityException
                    | IllegalArgumentException
                    | IllegalAccessException e) {
                StatsConsole.printlnError(String.format("AbstractEntityMap.onUpdate %s: error applying changes %s -> %s", objType.getSimpleName(), change.getFieldName(), e));
            }
        }
        // Prepare SQL UPDATE
        List<Tuple2<String, Object>> sqlFields = new ArrayList<>();
        List<Tuple2<String, Object>> sqlRelations = new ArrayList<>();
        for (Diff<?> change : changes) {
            String fieldName = change.getFieldName();
            Object newValue = change.getRight();
            String valueType = newValue.getClass().getSimpleName();
            switch (valueType) {
                case "Integer":
                case "Long":
                case "Double":
                case "Boolean":
                case "EntityId":
                case "String":
                    sqlFields.add(new Tuple2<>(SqlUtils.transform(fieldName), newValue));
                    break;
                case "EntityIdList":
                    EntityIdList target = (EntityIdList)change.getLeft();
                    EntityIdList source = (EntityIdList)newValue;
                    for (EntityId id : source) {
                        if (!target.contains(id)) {
                            String relationFieldName = SqlUtils.transform(fieldName);
                            sqlRelations.add(new Tuple2<>(relationFieldName, id));
                        }
                    }
                    break;
                default:
                    StatsConsole.printlnWarn(String.format("AbstractEntityMap.onUpdate %s: cannot append changes to SQL field '%s' of type '%s': %s", objType.getSimpleName(), fieldName, valueType, newValue));
            }
        }
        StringBuilder sb = new StringBuilder();
        String tableName = SqlUtils.transformTableName(existing.getClass().getSimpleName());
        if (!sqlFields.isEmpty()) {
            AppendUpdate(sb, tableName, existing instanceof BaseTimeEntity, sqlFields, existing.getId());
        }
        for (Tuple2<String, Object> relation: sqlRelations) {
            String relTableName = String.format("%s%s%s", tableName, RELATION_SEPARATOR, relation._1);
            List<Tuple2<String, Object>> relFields = new ArrayList<>();
            relFields.add(new Tuple2<>(FIELD_REL_SOURCE, existing.getId()));
            relFields.add(new Tuple2<>(FIELD_REL_TARGET, relation._2));
            AppendInsert(sb, relTableName, false, relFields);
        }
//        String sql = sb.toString().trim();
//        if (!sql.isEmpty()) {
//            StatsConsole.printlnState(sql);
//        }
    }

    @Override
    public void onDiscard(T existing, T submitted) {
    }

    private void AppendInsert(StringBuilder sb, String tableName, boolean hasTimeStamp, List<Tuple2<String, Object>> fields) {
        prepareDDL(tableName, hasTimeStamp, fields);
        sb.append(tableName)
                .append(" (")
                .append(fields.stream().map(t -> t._1).collect(Collectors.joining(", ")))
                .append(") VALUES (")
                .append(fields.stream().map(t ->SqlUtils.prepareSql(t._2)).collect(Collectors.joining(", ")))
                .append(");");
    }

    private void AppendUpdate(StringBuilder sb, String tableName, boolean hasTimeStamp, List<Tuple2<String, Object>> fields, EntityId whereId) {
        prepareDDL(tableName, hasTimeStamp, fields);
        String sqlFields = fields.stream().map(t -> String.format("%s=%s", t._1, SqlUtils.prepareSql(t._2))).collect(Collectors.joining(", "));
        sb.append("UPDATE ")
                .append(tableName)
                .append(" SET ")
                .append(sqlFields)
                .append(" WHERE ").append(FIELD_ID).append("=")
                .append(whereId)
                .append(";");
    }


    private static class TableInfo implements Comparable<TableInfo> {
        public TableInfo(String name, boolean hasTimeStamp) {
            this.name = name;
            this.hasTimeStamp = hasTimeStamp;
        }
        public String name;
        public boolean hasTimeStamp;

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
    }
    private static class FieldInfo {
        public FieldInfo(String name, String type, int size) {
            this.name = name;
            this.type = type;
            this.size = size;
        }
        public String name;
        public String type;
        public int size;
    }
    private TreeMap<TableInfo, List<FieldInfo>> sqlData = new TreeMap<>();
    private void prepareDDL(String tableName, boolean hasTimeStamp, List<Tuple2<String, Object>> fields) {
        if (flagDDL) {
            List<FieldInfo> sqlFields;
            Optional<TableInfo> tableInfo = sqlData.keySet().stream().filter(e -> e.name.equals(tableName)).findFirst();
            if (tableInfo.isPresent()) {
                sqlFields = sqlData.get(tableInfo.get());
            }
            else {
                sqlFields = new LinkedList<>();
                sqlData.put(new TableInfo(tableName, hasTimeStamp), sqlFields);
            }
            for (Tuple2<String, Object> field : fields) {
                Optional<FieldInfo> sqlField = sqlFields.stream().filter(e -> e.name.equals(field._1)).findFirst();
                if (sqlField.isPresent()) {
                    sqlField.get().size = Math.max(sqlField.get().size, calcSize(field._2));
                }
                else {
                    sqlFields.add(new FieldInfo(field._1, field._2.getClass().getSimpleName(), calcSize(field._2)));
                }
            }
        }
    }

    private int calcSize(Object value) {
        if (value instanceof String) {
            return value.toString().length();
        }
        return 0;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sqlData.forEach((tableInfo, fields) -> {
            sb.append("CREATE TABLE ").append(tableInfo.name).append(" (").append("\n");
            fields.forEach(fieldInfo -> sb.append("\t").append(fieldInfo.name).append(" ").append(resolveType(fieldInfo)).append(",\n"));
            sb.append("\tPRIMARY KEY (").append(resolvePK(tableInfo)).append(")");
            sb.append(");\n");
        });
        return sb.toString();
    }

    private String resolveType(FieldInfo fieldInfo) {
        StringBuilder sb = new StringBuilder();
        switch (fieldInfo.type) {
            case "Integer": sb.append("smallint"); break;
            case "Long": sb.append("int"); break;
            case "EntityId": sb.append("bigint"); break;
            case "Double": sb.append("real"); break;
            case "Boolean": sb.append("boolean"); break;
            case "String": sb.append("varchar(").append((int)Math.pow(2, Math.ceil(Math.log(fieldInfo.size)/Math.log(2)))).append(")"); break;
            default: throw new IllegalArgumentException(String.format("SqlBuilderListener.resolveType: unknown field type %s", fieldInfo.type));
        }
        String name = fieldInfo.name;
        if (name.equals(FIELD_ID) || name.equals(FIELD_TIMESTAMP) || name.equals(FIELD_REL_SOURCE) || name.equals(FIELD_REL_TARGET))
            sb.append(" NOT NULL");
        return sb.toString();
    }

    private String resolvePK(TableInfo tableInfo) {
        if (tableInfo.hasTimeStamp)
            return FIELD_ID + ", " + FIELD_TIMESTAMP;
        if (tableInfo.name.indexOf(RELATION_SEPARATOR) > 0)
            return FIELD_REL_SOURCE + ", " + FIELD_REL_TARGET;
        return FIELD_ID;
    }
}
