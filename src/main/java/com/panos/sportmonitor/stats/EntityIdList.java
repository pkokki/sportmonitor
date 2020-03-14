package com.panos.sportmonitor.stats;

import java.util.ArrayList;

public class EntityIdList extends ArrayList<EntityId> {
    @Override
    public boolean add(EntityId entityId) {
        if (this.contains(entityId))
            throw new IllegalArgumentException(String.format("Duplicate id %s", entityId));
        return super.add(entityId);
    }
}
