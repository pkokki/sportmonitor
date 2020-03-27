package com.panos.sportmonitor.stats;

import com.panos.sportmonitor.stats.store.ReflectionUtils;
import com.panos.sportmonitor.stats.store.StatsStoreListener;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.builder.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class StatsStore {
    private static final String IGNORED_FIELD_PREFIX = "__";

    private final HashMap<EntityId, BaseEntity> entities = new HashMap<>();
    private final List<StatsStoreListener> listeners = new LinkedList<>();

    public StatsStore() {
    }

    public void addListener(StatsStoreListener listener) {
        this.listeners.add(listener);
    }

    public void submit(BaseRootEntity rootEntity) {
        for (BaseEntity entity : rootEntity.getChildEntities()) {
            submitEntity(entity);
        }
    }

    private void submitEntity(BaseEntity submittedEntity) {
        this.onSubmit(submittedEntity);
        BaseEntity existing = this.entities.get(submittedEntity.getId());
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
            this.entities.put(submittedEntity.getId(), submittedEntity);
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
                final Field entityField = ReflectionUtils.getField(existing.getClass(), change.getFieldName());
                onUpsert(true, existing, entityField, oldValue, newValue);
            } catch (NoSuchFieldException e) {
                StatsConsole.printlnError(String.format("StatsStore.onUpdate %s: error applying changes %s -> %s", existing.getClass().getSimpleName(), change.getFieldName(), e));
            }
        }
    }

    private void onUpsert(boolean isUpdate, BaseEntity entity, Field entityField, Object oldValue, Object newValue) {
        final String entityFieldName = entityField.getName();
        if (newValue == null || entityFieldName.startsWith(IGNORED_FIELD_PREFIX))
            return;
        final String entityFieldType = newValue.getClass().getSimpleName();
        switch (entityFieldType) {
            case "int":
            case "Integer":
            case "long":
            case "Long":
            case "double":
            case "Double":
            case "Boolean":
            case "boolean":
            case "String":
                if (isUpdate) ReflectionUtils.setValue(entity, entityField, newValue);
                listeners.forEach(o -> o.onPropertyChange(entity, entityFieldName, oldValue, newValue));
                break;
            case "EntityId":
                if (isUpdate) ReflectionUtils.setValue(entity, entityField, newValue);
                if (!entityFieldName.equals("id"))
                    listeners.forEach(o -> o.onRelationChanged(entity, entityFieldName, (EntityId)oldValue, (EntityId)newValue));
                break;
            default:
                StatsConsole.printlnWarn(String.format("StatsStore.onUpsert %s: cannot apply changes to field '%s' of type '%s': %s", entity.getClass().getSimpleName(), entityFieldName, entityFieldType, newValue));
        }
    }

    private List<Diff<?>> diff(BaseEntity existingEntity, BaseEntity submittedEntity) {
        try {
            List<Diff<?>> changes = new ArrayList<>();
            DiffResult result = new ReflectionDiffBuilder(existingEntity, submittedEntity, ToStringStyle.SHORT_PREFIX_STYLE)
                    .build();
            for (Diff<?> diff : result.getDiffs()) {
                if (isAcceptedDiff(diff.getFieldName(), diff.getLeft(), diff.getRight())) {
//                    if (isImportantDiff(diff.getLeft()))
//                        StatsConsole.printlnState(String.format("important diff: %s %s -> %s",
//                                existingEntity.getClass().getSimpleName(),
//                                diff.getLeft().getClass().getSimpleName(),
//                                diff.toString().substring(0, Math.min(200, diff.toString().length()))));
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
        return true;
    }

    private boolean isAcceptedDiff(String fieldName, Object oldValue, Object newValue) {
        if (fieldName.startsWith("__"))
            return false;
        if (newValue == null || Objects.equals(newValue, ""))
            return false;
        return true;
    }
}
