package com.panos.sportmonitor.stats;

import java.util.Objects;

public class EntityId {

    private final long id, timeStamp;
    private final Class<? extends BaseEntity> entityClass;

    public EntityId(long id, Class<? extends BaseEntity> entityClass) {
        this(id, Long.MAX_VALUE, entityClass);
    }
    public EntityId(BaseEntity entity) {
        this(entity.getId().id, entity.getId().timeStamp, entity.getClass());
    }
    public EntityId(long id, long timeStamp, Class<? extends BaseEntity> entityClass) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid EntityId: " + id);
        if (timeStamp <= 0)
            throw new IllegalArgumentException("Invalid EntityId timestamp: " + timeStamp);
        if (timeStamp != Long.MAX_VALUE && !BaseTimeEntity.class.isAssignableFrom(entityClass))
            throw new IllegalArgumentException("Invalid EntityId timestamp: " + timeStamp + ". Entity class is " + entityClass.getSimpleName());
        if (timeStamp == Long.MAX_VALUE && BaseTimeEntity.class.isAssignableFrom(entityClass))
            throw new IllegalArgumentException("EntityId timestamp not defined. Entity class is " + entityClass.getSimpleName());
        this.id = id;
        this.timeStamp = timeStamp;
        this.entityClass = entityClass;
    }


    public boolean isComposite() {
        return timeStamp != Long.MAX_VALUE;
    }
    public long getId() { return id; }
    public long getTimeStamp() {
        if (timeStamp == Long.MAX_VALUE)
            throw new IllegalArgumentException("Invalid use of EntityId.getTimeStamp(). EntityId is not composite.");
        return timeStamp;
    }
    public Class<? extends BaseEntity> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId entityId = (EntityId) o;
        return id == entityId.id && timeStamp == entityId.timeStamp && entityClass.equals(entityId.entityClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStamp, entityClass);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EntityId{");
        sb.append("id=").append(id);
        if (isComposite()) sb.append(", timeStamp=").append(timeStamp);
        sb.append(", entityClass=").append(entityClass.getSimpleName());
        sb.append('}');
        return sb.toString();
    }
}
