package com.panos.sportmonitor.stats;

import java.util.Objects;

public class EntityKey {
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "ts";

    private final String name;
    private final Object value;
    private final boolean singleId;

    public EntityKey(String name, Object value) {
        this(false, name, value);
        if (name.equals(KEY_ID) || name.equals(KEY_TIMESTAMP))
            throw new IllegalArgumentException("Invalid key name. Use static method.");
    }
    private EntityKey(boolean isSingleId, String name, Object value) {
        this.singleId = isSingleId;
        this.name = name;
        this.value = value;
    }

    public static EntityKey ID(long id) {
        return new EntityKey(true, KEY_ID, id);
    }
    public static EntityKey Timestamp(long timeStamp) {
        return new EntityKey(false, KEY_TIMESTAMP, timeStamp);
    }

    public Object getValue() {
        return value;
    }
    public String getName() {
        return name;
    }
    public boolean isSingleId() {
        return singleId;
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
