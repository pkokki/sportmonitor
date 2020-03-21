package com.panos.sportmonitor.stats;

import java.util.LinkedList;

public class EntityIdList extends LinkedList<EntityId> {
    private Class<? extends BaseEntity> entityClass;

    @Override
    public boolean add(EntityId entityId) {
        if (entityClass == null) {
            entityClass = entityId.getEntityClass();
        }
        else {
            if (!entityClass.equals(entityId.getEntityClass()))
                throw new IllegalArgumentException(String.format("Adding id of type %s to entity list of type %s",
                        entityId.getEntityClass().getSimpleName(), entityClass.getSimpleName()));
            if (this.contains(entityId))
                throw new IllegalArgumentException(String.format("Duplicate id %s", entityId));
        }
        return super.add(entityId);
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return this.entityClass;
    }
}
