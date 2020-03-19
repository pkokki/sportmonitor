package com.panos.sportmonitor.stats.store;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.*;
import org.apache.commons.lang3.builder.Diff;
import scala.Tuple2;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class SqlBuilderListener {
    /*
    private static final List<String> IGNORED_FIELDS = Lists.newArrayList("auxId", "childEntities", "parent", "IGNORED");
    private static final String IGNORED_FIELD_PREFIX = "__";

    private final SqlTableCreator sqlTableCreator;
    private final SqlExecutor sqlExecutor;

    public SqlBuilderListener(SqlExecutor sqlExecutor, SqlTableCreator sqlTableCreator) {
        this.sqlExecutor = sqlExecutor;
        this.sqlTableCreator = sqlTableCreator;
    }

    @Override
    public void onSubmit(BaseEntity entity) {
    }

    @Override
    public void onCreate(BaseEntity entity) {
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
        onSqlInsert(tableName, entity instanceof BaseTimeEntity, fields);
        for (Tuple2<String, Object> relation: relations) {
            String relTableName = String.format("%s%s%s", tableName, SqlUtils.RELATION_SEPARATOR, relation._1);
            List<Tuple2<String, Object>> relFields = new ArrayList<>();
            relFields.add(new Tuple2<>(SqlUtils.FIELD_REL_SOURCE, objectId));
            relFields.add(new Tuple2<>(SqlUtils.FIELD_REL_TARGET, relation._2));
            onSqlInsert(relTableName, false, relFields);
        }
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
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
        String tableName = SqlUtils.transformTableName(objType.getSimpleName());
        if (!sqlFields.isEmpty()) {
            if (sqlTableCreator != null)
                sqlTableCreator.prepare(tableName, sqlFields, existing instanceof BaseTimeEntity);
            onSqlUpdate(tableName, sqlFields, existing.getId());
        }
        for (Tuple2<String, Object> relation: sqlRelations) {
            String relTableName = String.format("%s%s%s", tableName, SqlUtils.RELATION_SEPARATOR, relation._1);
            List<Tuple2<String, Object>> relFields = new ArrayList<>();
            relFields.add(new Tuple2<>(SqlUtils.FIELD_REL_SOURCE, existing.getId()));
            relFields.add(new Tuple2<>(SqlUtils.FIELD_REL_TARGET, relation._2));
            onSqlInsert(relTableName, false, relFields);
        }
    }

    @Override
    public void onDiscard(BaseEntity existing, BaseEntity submitted) {
    }

    private void onSqlInsert(String tableName, boolean hasTimeStamp, List<Tuple2<String, Object>> fields) {
        if (sqlTableCreator != null)
            sqlTableCreator.prepare(tableName, hasTimeStamp, fields);
        String sql = "INSERT INTO " +
                tableName +
                " (" +
                fields.stream().map(t -> t._1).collect(Collectors.joining(", ")) +
                ") VALUES (" +
                fields.stream().map(t -> SqlUtils.prepareSql(t._2)).collect(Collectors.joining(", ")) +
                ");"; // ON CONFLICT DO NOTHING
        if (sqlExecutor != null)
            sqlExecutor.addStatement(sql);
    }

    private void onSqlUpdate(String tableName, List<Tuple2<String, Object>> fields, EntityId whereId) {
        String sqlFields = fields.stream().map(t -> String.format("%s=%s", t._1, SqlUtils.prepareSql(t._2))).collect(Collectors.joining(", "));
        String sql = "UPDATE " +
                tableName +
                " SET " +
                sqlFields +
                " WHERE " + SqlUtils.FIELD_ID + "=" +
                whereId +
                ";";
        if (sqlExecutor != null)
            sqlExecutor.addStatement(sql);
    }*/
}
