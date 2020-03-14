package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityIdList;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class AbstractEntityMap<T extends BaseEntity> {
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
    }
    protected void onUpdate(T existing, T submitted, List<Diff<?>> changes) {
        ++counters.updated;
    }
    protected void onDiscard(T existing, T submitted) {
        ++counters.discarded;
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
            DiffResult result = existingEntity.diff(submittedEntity);
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
