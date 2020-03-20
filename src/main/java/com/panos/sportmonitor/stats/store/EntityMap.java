package com.panos.sportmonitor.stats.store;

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

public class EntityMap {
    private static final String IGNORED_FIELD_PREFIX = "__";

    private final HashMap<Tuple2<String, EntityId> , BaseEntity> entities = new HashMap<>();
    private final List<StatsStoreListener> listeners = new LinkedList<>();

    public EntityMap() {
    }

    public void addListener(StatsStoreListener listener) {
        this.listeners.add(listener);
    }

    public void submit(BaseEntity submittedEntity) {
        this.onSubmit(submittedEntity);
        Tuple2<String, EntityId> key = new Tuple2<>(submittedEntity.getClass().getSimpleName(), submittedEntity.getId());
        BaseEntity existing = this.entities.get(key);
        if (existing != null) {
            List<Diff<?>> changes = diff(existing, submittedEntity);
            if (changes.isEmpty()) {
                this.onDiscard(existing, submittedEntity);
            }
            else {
                this.onUpdate(existing, submittedEntity, changes);
            }
        }
        else {
            this.entities.put(key, submittedEntity);
            this.onCreate(submittedEntity);
        }
    }

    public void submitChanges() {
        listeners.forEach(StatsStoreListener::submitChanges);
    }

    private void onSubmit(BaseEntity entity) {
        listeners.forEach(o -> o.onSubmit(entity));
    }

    private void onDiscard(BaseEntity existing, BaseEntity submitted) {
        listeners.forEach(o -> o.onDiscard(existing, submitted));
    }

    private void onCreate(BaseEntity entity) {
        listeners.forEach(o -> o.onCreate(entity));
        List<Field> entityFields = ReflectionUtils.getAllFields(new LinkedList<>(), entity.getClass());
        for (Field entityField : entityFields) {
            onUpsert(false, entity, entityField, null, ReflectionUtils.getValue(entityField, entity));
        }
    }

    private void onUpdate(final BaseEntity existing, final BaseEntity submitted, final List<Diff<?>> changes) {
        listeners.forEach(o -> o.onUpdate(existing, submitted, changes));
        for (Diff<?> change : changes) {
            final Object oldValue = change.getLeft();
            final Object newValue = change.getRight();
            try {
                final Field entityField = existing.getClass().getDeclaredField(change.getFieldName());
                onUpsert(true, existing, entityField, oldValue, newValue);
            } catch (NoSuchFieldException e) {
                StatsConsole.printlnError(String.format("EntityMap.onUpdate %s: error applying changes %s -> %s", existing.getClass().getSimpleName(), change.getFieldName(), e));
            }
        }
    }

    private void onUpsert(boolean isUpdate, BaseEntity entity, Field entityField, Object oldValue, Object newValue) {
        final String entityFieldName = entityField.getName();
        if (newValue == null || entityFieldName.startsWith(IGNORED_FIELD_PREFIX))
            return;
        final String entityFieldType = newValue.getClass().getSimpleName();
        switch (entityFieldType) {
            case "Integer":
            case "Long":
            case "Double":
            case "Boolean":
            case "String":
                if (isUpdate) ReflectionUtils.setValue(entity, entityField, newValue);
                listeners.forEach(o -> o.onPropertyChange(entity, entityFieldName, oldValue, newValue));
                break;
            case "EntityId":
                if (isUpdate) ReflectionUtils.setValue(entity, entityField, newValue);
                if (!entityFieldName.equals("id"))
                    listeners.forEach(o -> o.onRelationChanged(entity, entityFieldName, (EntityId)oldValue, (EntityId)newValue));
                break;
            case "EntityIdList":
                final EntityIdList oldList = (EntityIdList) oldValue;
                final List<EntityId> newIds = ((EntityIdList) newValue)
                        .stream()
                        .filter(o -> oldList == null || !oldList.contains(o))
                        .collect(Collectors.toList());
                newIds.forEach(id -> {
                    if (oldList != null && isUpdate) oldList.add(id);
                    listeners.forEach(o -> o.onRelationAdded(entity, entityFieldName, id));
                });
                break;
            default:
                StatsConsole.printlnWarn(String.format("EntityMap.onUpsert %s: cannot apply changes to field '%s' of type '%s': %s", entity.getClass().getSimpleName(), entityFieldName, entityFieldType, newValue));
        }
    }

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
        if (fieldName.equals("parent") || fieldName.equals("auxId") || fieldName.equals("childEntities") || fieldName.startsWith("__"))
            return false;
        if (newValue == null || Objects.equals(newValue, ""))
            return false;
        if (newValue instanceof EntityIdList)
            return !((EntityIdList)newValue).isEmpty() && !ListUtils.subtract((EntityIdList)newValue, (EntityIdList)oldValue).isEmpty();
        if (newValue instanceof HashMap<?,?>)
            return !((HashMap<?,?>)newValue).isEmpty();
        return true;
    }
}
