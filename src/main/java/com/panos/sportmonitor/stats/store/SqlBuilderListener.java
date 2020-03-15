package com.panos.sportmonitor.stats.store;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityIdList;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.Diff;
import scala.Tuple2;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class SqlBuilderListener<T extends BaseEntity> implements IStatsStoreListener<T> {
    private static final List<String> IGNORED_FIELDS = Lists.newArrayList("auxId", "childEntities", "parent", "IGNORED");

    @Override
    public void onSubmit(T entity) {
    }

    @Override
    public void onCreate(T entity) {
        Class<? extends BaseEntity> objectType = entity.getClass();
        EntityId objectId = entity.getId();
        String tableName = transformTableName(objectType.getSimpleName());
        List<Tuple2<String, String>> fields = new ArrayList<>();
        List<Tuple2<String, String>> relations = new ArrayList<>();
        List<Field> objFields = getAllFields(new LinkedList<Field>(), objectType);
        for (Field objField : objFields) {
            String fieldName = objField.getName();
            if (IGNORED_FIELDS.contains(fieldName))
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
                                fields.add(new Tuple2<>(transform(fieldName), fieldValue.toString()));
                                break;
                            case "String":
                                fields.add(new Tuple2<>(transform(fieldName), String.format("'%s'", fieldValue.toString().replace("'", "''"))));
                                break;
                            case "EntityIdList":
                                EntityIdList ids = (EntityIdList) fieldValue;
                                String relationFieldName = transform(fieldName);
                                for (EntityId id : ids) {
                                    relations.add(new Tuple2<>(relationFieldName, id.toString()));
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
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" (")
                .append(fields.stream().map(e -> e._1).collect(Collectors.joining(", ")))
                .append(") VALUES (")
                .append(fields.stream().map(e -> e._2).collect(Collectors.joining(", ")))
                .append(");")
                ;
        for (Tuple2<String, String> relation: relations) {
            String relTableName = String.format("%s__%s", tableName, relation._1);
            String values = String.format("%s, %s", objectId, relation._2);
            sb.append("\n").append("INSERT INTO ").append(relTableName).append(" (source, target) VALUES (").append(values).append(");");
        }
        String sql = sb.toString();
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
        List<String> sqlFields = new ArrayList<>();
        List<Tuple2<String, String>> sqlRelations = new ArrayList<>();
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
                    sqlFields.add(String.format("%s=%s", transform(fieldName), newValue));
                    break;
                case "String":
                    sqlFields.add(String.format("%s='%s'", transform(fieldName), newValue.toString().replace("'", "''")));
                    break;
                case "EntityIdList":
                    EntityIdList target = (EntityIdList)change.getLeft();
                    EntityIdList source = (EntityIdList)newValue;
                    for (EntityId id : source) {
                        if (!target.contains(id)) {
                            String relationFieldName = transform(fieldName);
                            sqlRelations.add(new Tuple2<>(relationFieldName, id.toString()));
                        }
                    }
                    break;
                default:
                    StatsConsole.printlnWarn(String.format("AbstractEntityMap.onUpdate %s: cannot append changes to SQL field '%s' of type '%s': %s", objType.getSimpleName(), fieldName, valueType, newValue));
            }
        }
        StringBuilder sb = new StringBuilder();
        String tableName = transformTableName(existing.getClass().getSimpleName());
        if (!sqlFields.isEmpty()) {
            sb.append("UPDATE ")
                    .append(tableName)
                    .append(" SET ");
            sb.append(String.join(", ", sqlFields))
                    .append(" WHERE id = ")
                    .append(existing.getId())
                    .append(";");
        }
        for (Tuple2<String, String> relation: sqlRelations) {
            String relTableName = String.format("%s__%s", tableName, relation._1);
            String values = String.format("%s, %s", existing.getId(), relation._2);
            sb.append("\n").append("INSERT INTO ").append(relTableName).append(" (source, target) VALUES (").append(values).append(");");
        }
        String sql = sb.toString().trim();
        if (!sql.isEmpty()) {
            //StatsConsole.printlnState(sql);
        }
    }

    @Override
    public void onDiscard(T existing, T submitted) {
    }

    public static List<Field> getAllFields(final List<Field> fields, final Class<?> type) {
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        return fields;
    }
    private static String transformTableName(String str) {
        return transform(str.replace("Entity", ""));
    }
    private static String transform(String str) {
        return str.replaceAll("(\\w)([A-Z])", "$1_$2").toLowerCase();
    }
}
