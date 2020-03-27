package com.panos.sportmonitor.stats;

import java.util.Objects;

public class EntityKey {
    private final String name;
    private final Object value;

    public EntityKey(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public static EntityKey Timestamp(long timeStamp) {
        return new EntityKey(EntityId.KEY_TIMESTAMP, timeStamp);
    }

    public Object getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityKey other = (EntityKey) o;
        return value.equals(other.value) && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }
}
