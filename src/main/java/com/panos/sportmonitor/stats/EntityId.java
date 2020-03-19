package com.panos.sportmonitor.stats;

import java.util.Objects;

public class EntityId {
    private final long id, timeStamp;

    public EntityId(long id) {
        this(id, Long.MAX_VALUE);
    }
    public EntityId(long id, long timeStamp) {
        if (id <= 0) throw new NumberFormatException("Invalid EntityId: " + id);
        if (timeStamp <= 0) throw new NumberFormatException("Invalid EntityId timestamp: " + timeStamp);
        this.id = id;
        this.timeStamp = timeStamp;
    }

    public long asLong() {
        if (timeStamp != Long.MAX_VALUE)
            throw new IllegalArgumentException("Invalid use of EntityId.asLong(). EntityId is composite.");
        return id;
    }
    public long getId() { return id; }
    public long getTimeStamp() {
        if (timeStamp == Long.MAX_VALUE)
            throw new IllegalArgumentException("Invalid use of EntityId.getTimeStamp(). EntityId is not composite.");
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId entityId = (EntityId) o;
        return id == entityId.id && timeStamp == entityId.timeStamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStamp);
    }

    @Override
    public String toString() {
        if (timeStamp == Long.MAX_VALUE)
            return String.valueOf(id);
        return String.format("(%d, %d)", id, timeStamp);
    }

    public boolean isComposite() {
        return timeStamp != Long.MAX_VALUE;
    }
}
