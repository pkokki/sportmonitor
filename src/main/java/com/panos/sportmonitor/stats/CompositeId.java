package com.panos.sportmonitor.stats;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompositeId extends EntityId {
    private final List<EntityKey> keys;

    public CompositeId(Class<? extends BaseEntity> entityClass, List<EntityKey> keys) {
        super(entityClass);
        this.keys = keys;
    }

    public List<EntityKey> getKeys() {
        return keys;
    }

    @Override
    public long getId() {
        throw new IllegalStateException("Invalid use of EntityId.getId(). EntityId is multiple.");
    }

    @Override
    public boolean isMultiple() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeId entityId = (CompositeId) o;
        return super.equals(entityId) && keys.equals(entityId.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(keys.toArray()));
    }

    @Override
    public String toString() {
        return keys.stream().map(EntityKey::toString)
                .collect(Collectors.joining(", ", "CompositeId{", "}"));
    }

}
