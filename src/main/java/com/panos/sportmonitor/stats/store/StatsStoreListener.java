package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import org.apache.commons.lang3.builder.Diff;

import java.util.List;

public abstract class StatsStoreListener {
    public void onSubmit(BaseEntity entity) {}
    public void onCreate(BaseEntity entity) {}
    public void onUpdate(final BaseEntity existing, final BaseEntity submitted, final List<Diff<?>> changes) {}
    public void onDiscard(BaseEntity existing, BaseEntity submitted) {}

    public void submitChanges() {}

    public void onPropertyChange(BaseEntity entity, String entityFieldName, Object oldValue, Object newValue) {}
    public void onRelationChanged(BaseEntity entity, String entityFieldName, EntityId oldValue, EntityId newValue) {}
}
