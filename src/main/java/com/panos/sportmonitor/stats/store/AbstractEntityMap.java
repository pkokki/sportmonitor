package com.panos.sportmonitor.stats.store;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityIdList;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.builder.*;
import scala.Tuple2;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractEntityMap<T extends BaseEntity> {
    private static final List<String> IGNORED_FIELDS = Lists.newArrayList("auxId", "childEntities", "parent", "IGNORED");
    private final StoreCounters counters = new StoreCounters();
    private final HashMap<Object, T> entities = new HashMap<>();

    public void submit(T submittedEntity) {
        this.onSubmit(submittedEntity);
        if (this.containsEntity(submittedEntity)) {
            T existing = this.getExistingEntity(submittedEntity);
            List<Diff<?>> changes = diff(existing, submittedEntity);
            if (changes.isEmpty()) {
                this.onDiscard(existing, submittedEntity);
            }
            else {
                this.onUpdate(existing, submittedEntity, changes);
            }
        }
        else {
            this.add(submittedEntity);
            this.onCreate(submittedEntity);
        }
    }

    protected void onSubmit(T entity) {
        ++counters.submitted;
    }
    protected void onCreate(T entity) {
        ++counters.created;
        // Prepare SQL INSERT
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
    protected void onUpdate(final T existing, final T submitted, final List<Diff<?>> changes) {
        ++counters.updated;
        // Apply changes
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
    protected void onDiscard(T existing, T submitted) {
        ++counters.discarded;
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

    private boolean containsEntity(T entity) {
        Object key = getKey(entity);
        return this.entities.containsKey(key);
    }

    private T getExistingEntity(T entity) {
        Object key = getKey(entity);
        if (this.entities.containsKey(key))
            return this.entities.get(key);
        return null;
    }

    private void add(T entity) {
        Object key = getKey(entity);
        this.entities.put(key, entity);
    }

    protected abstract Object getKey(T entity);

    private List<Diff<?>> diff(BaseEntity existingEntity, BaseEntity submittedEntity) {
        try {
            List<Diff<?>> changes = new ArrayList<>();
            DiffResult result = new ReflectionDiffBuilder(existingEntity, submittedEntity, ToStringStyle.SHORT_PREFIX_STYLE)
                    .build();
            for (Diff<?> diff : result.getDiffs()) {
                if (isAcceptedDiff(diff.getFieldName(), diff.getLeft(), diff.getRight())) {
                    if (isImportantDiff(diff.getLeft()))
                        StatsConsole.printlnState(String.format("important diff: %s %s -> %s",
                                existingEntity.getClass().getSimpleName(),
                                diff.getLeft().getClass().getSimpleName(),
                                diff.toString().substring(0, Math.min(200, diff.toString().length()))));
                    changes.add(diff);
                }
            }
            return changes;
        } catch (Exception ex) {
            String existingParentName = existingEntity.getParent() != null ? existingEntity.getParent().getClass().getSimpleName() : "<ROOT>";
            String submittedParentName = submittedEntity.getParent() != null ? submittedEntity.getParent().getClass().getSimpleName() : "<ROOT>";
            StatsConsole.printlnError(String.format("existing:  %s -> %s", existingParentName, existingEntity));
            StatsConsole.printlnError(String.format("submitted: %s -> %s", submittedParentName, submittedEntity));
            throw ex;
        }
    }

    private boolean isImportantDiff(Object oldValue) {
        if (oldValue == null)
            return false;
        if (oldValue instanceof EntityIdList)
            return !((EntityIdList)oldValue).isEmpty();
        if (oldValue instanceof HashMap<?,?>)
            return !((HashMap<?,?>)oldValue).isEmpty();
        return true;
    }

    private boolean isAcceptedDiff(String fieldName, Object oldValue, Object newValue) {
        if (fieldName.equals("parent") || fieldName.equals("auxId") || fieldName.equals("childEntities"))
            return false;
        if (newValue == null || Objects.equals(newValue, ""))
            return false;
        if (newValue instanceof EntityIdList)
            return !((EntityIdList)newValue).isEmpty() && !ListUtils.subtract((EntityIdList)newValue, (EntityIdList)oldValue).isEmpty();
        if (newValue instanceof HashMap<?,?>)
            return !((HashMap<?,?>)newValue).isEmpty();
        return true;
    }

    public StoreCounters getCounters() {
        return counters;
    }
}
