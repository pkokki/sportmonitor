package com.panos.sportmonitor.stats;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class EntityId {
    public static final String KEY_ID = "id";
    public static final String KEY_TIMESTAMP = "timeStamp";

    private final List<EntityKey> keys;
    private final Class<? extends BaseEntity> entityClass;

    public EntityId(long id, Class<? extends BaseEntity> entityClass) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid id: " + id);
        this.keys = Collections.unmodifiableList(Lists.newArrayList(new EntityKey(KEY_ID, id)));
        this.entityClass = entityClass;
    }

    public EntityId(long id, long timeStamp, Class<? extends BaseEntity> entityClass) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid id: " + id);
        if (timeStamp <= 0)
            throw new IllegalArgumentException("Invalid timestamp: " + timeStamp);
        if (timeStamp != Long.MAX_VALUE && !BaseTimeEntity.class.isAssignableFrom(entityClass))
            throw new IllegalArgumentException("Invalid EntityId timestamp: " + timeStamp + ". Entity class is " + entityClass.getSimpleName());
        if (timeStamp == Long.MAX_VALUE && BaseTimeEntity.class.isAssignableFrom(entityClass))
            throw new IllegalArgumentException("EntityId timestamp not defined. Entity class is " + entityClass.getSimpleName());
        List<EntityKey> allKeys = new LinkedList<>();
        allKeys.add(new EntityKey(KEY_ID, id));
        allKeys.add(new EntityKey(KEY_TIMESTAMP, timeStamp));
        this.keys = Collections.unmodifiableList(allKeys);
        this.entityClass = entityClass;
    }
//    public EntityId(List<EntityKey> keys, long timeStamp, Class<? extends BaseEntity> entityClass){
//        if (timeStamp <= 0)
//            throw new IllegalArgumentException("Invalid timestamp: " + timeStamp);
//        if (timeStamp != Long.MAX_VALUE && !BaseTimeEntity.class.isAssignableFrom(entityClass))
//            throw new IllegalArgumentException("Invalid EntityId timestamp: " + timeStamp + ". Entity class is " + entityClass.getSimpleName());
//        if (timeStamp == Long.MAX_VALUE && BaseTimeEntity.class.isAssignableFrom(entityClass))
//            throw new IllegalArgumentException("EntityId timestamp not defined. Entity class is " + entityClass.getSimpleName());
//        validateKeys(keys);
//        List<EntityKey> allKeys = new LinkedList<>(keys);
//        allKeys.add(new EntityKey(KEY_TIMESTAMP, timeStamp));
//        this.keys = Collections.unmodifiableList(allKeys);
//        this.entityClass = entityClass;
//    }
    public EntityId(BaseEntity entity) {
        this.keys = Collections.unmodifiableList(new LinkedList<>(entity.getId().getKeys()));
        this.entityClass = entity.getId().getEntityClass();
    }
    public EntityId(List<EntityKey> keys, Class<? extends BaseEntity> entityClass) {
        validateKeys(keys);
        this.keys = Collections.unmodifiableList(keys);
        this.entityClass = entityClass;
    }

    private void validateKeys(List<EntityKey> keys) {
        if (keys.stream().anyMatch(e -> e.getName().equals(KEY_ID) || e.getName().equals(KEY_TIMESTAMP)))
            throw new IllegalArgumentException("Invalid use of id or timestamp in Entity id");
    }

    public List<EntityKey> getKeys() {
        return keys;
    }

    public long getId() {
        if (keys.size() > 1)
            throw new IllegalStateException("Invalid use of EntityId.getId(). EntityId is multiple.");
        EntityKey key = keys.get(0);
        if (key.getName().equals(KEY_ID))
            return (long)key.getValue();
        throw new IllegalStateException("Invalid use of EntityId.getId(). EntityId does not have id.");
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId other = (EntityId) o;
        return entityClass.equals(other.entityClass) && keys.equals(other.keys); // && IntStream.range(0, keys.size()).allMatch(i -> keys.get(i).equals(other.keys.get(i)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityClass.hashCode(), Arrays.hashCode(keys.toArray()));
    }

    @Override
    public String toString() {
        return keys.stream().map(EntityKey::toString)
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
