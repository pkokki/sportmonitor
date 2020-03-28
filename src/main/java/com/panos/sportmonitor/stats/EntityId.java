package com.panos.sportmonitor.stats;

import com.google.common.collect.Lists;
import org.apache.commons.lang.WordUtils;

import java.util.*;
import java.util.stream.Collectors;

public class EntityId {


    private final List<EntityKey> keys;
    private final Class<? extends BaseEntity> entityClass;

    public EntityId(Class<? extends BaseEntity> entityClass, long id) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid id: " + id);
        this.keys = Collections.unmodifiableList(Lists.newArrayList(EntityKey.ID(id)));
        this.entityClass = entityClass;
    }

    public EntityId(Class<? extends BaseEntity> entityClass, long id, long timeStamp) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid id: " + id);
        if (timeStamp <= 0)
            throw new IllegalArgumentException("Invalid timestamp: " + timeStamp);
        List<EntityKey> allKeys = new LinkedList<>();
        allKeys.add(EntityKey.ID(id));
        allKeys.add(EntityKey.Timestamp(timeStamp));
        this.keys = Collections.unmodifiableList(allKeys);
        this.entityClass = entityClass;
    }
    public EntityId(BaseEntity entity) {
        this.keys = Collections.unmodifiableList(new LinkedList<>(entity.getId().getKeys()));
        this.entityClass = entity.getId().getEntityClass();
    }
    public EntityId(Class<? extends BaseEntity> entityClass, List<EntityKey> keys) {
        this.keys = Collections.unmodifiableList(keys);
        this.entityClass = entityClass;
    }
    public EntityId(Class<? extends BaseEntity> entityClass, EntityKey... ids) {
        this.keys = Collections.unmodifiableList(new LinkedList<>(Arrays.asList(ids)));
        this.entityClass = entityClass;
    }
    public EntityId(Class<? extends BaseEntity> entityClass, EntityId... ids) {
        List<EntityKey> newKeys = new LinkedList<>();
        for (EntityId id : ids)
            newKeys.addAll(split(id));
        this.keys = Collections.unmodifiableList(newKeys);
        this.entityClass = entityClass;
    }

    public EntityId(Class<? extends BaseEntity> entityClass, EntityId[] entityIds, EntityKey[] entityKeys) {
        List<EntityKey> newKeys = new LinkedList<>();
        for (EntityId entityId : entityIds)
            newKeys.addAll(split(entityId));
        newKeys.addAll(Arrays.asList(entityKeys));
        this.keys = Collections.unmodifiableList(newKeys);
        this.entityClass = entityClass;
    }

    private static List<EntityKey> split(EntityId id) {
        List<EntityKey> newKeys = new LinkedList<>();
        String name = WordUtils.uncapitalize(id.getEntityClass().getSimpleName());
        if (name.endsWith("Entity")) name = name.substring(0, name.length() - 6);
        for (EntityKey key : id.getKeys()) {
            String keyName = name + WordUtils.capitalize(key.getName());
            newKeys.add(new EntityKey(keyName, key.getValue()));
        }
        return newKeys;
    }

    public List<EntityKey> getKeys() {
        return keys;
    }

    public long getId() {
        if (keys.size() > 1)
            throw new IllegalStateException("Invalid use of EntityId.getId(). EntityId is multiple.");
        EntityKey key = keys.get(0);
        if (key.isSingleId())
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
