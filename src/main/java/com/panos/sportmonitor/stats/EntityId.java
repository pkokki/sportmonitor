package com.panos.sportmonitor.stats;

import java.util.Objects;

public class EntityId {
    private final long id;

    public EntityId(long id) {
        if (id <= 0) throw new NumberFormatException("Invalid EntityId: " + id);
        this.id = id;
    }

    public long asLong() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId entityId = (EntityId) o;
        return id == entityId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
