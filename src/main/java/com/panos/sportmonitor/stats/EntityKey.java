package com.panos.sportmonitor.stats;

import java.util.Objects;

public class EntityKey {
    private final Object value;
    private final String name;

    public EntityKey(String name, Object value) {
        this.name = name;
        this.value = value;
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
        EntityKey entityKey = (EntityKey) o;
        return value.equals(entityKey.value) &&
                name.equals(entityKey.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }
}
