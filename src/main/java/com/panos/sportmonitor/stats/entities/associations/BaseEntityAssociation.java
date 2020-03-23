package com.panos.sportmonitor.stats.entities.associations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.StatsConsole;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class BaseEntityAssociation {
    private final BaseEntity master;
    private final Class<? extends BaseEntity> childEntityClass;
    private final HashMap<EntityId, Properties> children;
    private final HashMap<String, Class<?>> properties;

    public BaseEntityAssociation(BaseEntity master, Class<? extends BaseEntity> childEntityClass) {
        this.master = master;
        this.childEntityClass = childEntityClass;
        this.children = new HashMap<>();
        this.properties = new LinkedHashMap<>();
    }

    public final boolean setProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (node.isNull())
            return true;
        return handleProperty(childEntity, nodeName, nodeType, node);
    }

    protected boolean handleProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        StatsConsole.printlnError(String.format("%s [UNHANDLED *ASSOCIATION* PROPERTY]: %s->%s --- %s --- %s",
                this.getClass().getSimpleName(),
                childEntity.getClass().getSimpleName(),
                nodeName,
                nodeType,
                node.asText("<empty>")));
        return true;
    }

    protected void setChildProperty(BaseEntity childEntity, String propName, Object propValue) {
        assertProperty(propName, propValue);
        getOrCreate(childEntity).put(propName, propValue);
    }

    private void assertProperty(String propName, Object propValue) {
        Class<?> propType = this.properties.computeIfAbsent(propName, k -> propValue.getClass());
        if (!propValue.getClass().equals(propType)) {
            throw new IllegalArgumentException(String.format("Trying to add property %s of type %s to association %s. Expecting property type %s.",
                    propName,
                    propValue.getClass().getSimpleName(),
                    this.getClass().getSimpleName(),
                    propType.getSimpleName()));
        }
    }

    public void add(BaseEntity childEntity) {
        getOrCreate(childEntity);
    }

    protected HashMap<String, Object> getOrCreate(BaseEntity childEntity) {
        EntityId childId = childEntity.getId();
        if (!childId.getEntityClass().equals(this.childEntityClass))
            throw new IllegalArgumentException(String.format("Trying to add child entity of type %s to association %s. Expecting child type %s.",
                    childId.getEntityClass().getSimpleName(),
                    this.getClass().getSimpleName(),
                    this.childEntityClass.getSimpleName()));
        return children.computeIfAbsent(childId, k -> new Properties());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "master=" + master.getId() +
                ", children=" + children +
                '}';
    }

    private static class Properties extends HashMap<String, Object> {
    }
}
