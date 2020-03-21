package com.panos.sportmonitor.stats;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.collect.Lists;
import org.apache.commons.math3.exception.OutOfRangeException;

import java.util.List;
import java.util.Objects;

public abstract class BaseEntity {
    private final static List<String> __IGNORED = Lists.newArrayList("_doc", "_id", "_sid");
    private final EntityId id;
    private final BaseEntity __parent;
    private int __next;

    public BaseEntity(BaseEntity parent, EntityId id) {
        this.__parent = parent;
        this.id = id;
    }

    public final boolean setProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (node.isNull() || (node.isTextual() && node.asText().length() == 0) || __IGNORED.contains(nodeName) )
            return true;
        return handleProperty(nodeName, nodeType, node);
    }

    public final boolean setEntity(String entityName, BaseEntity childEntity) {
        return handleChildEntity(entityName, childEntity);
    }

    public final boolean setChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (node.isNull()) return true;
        return handleChildProperty(childEntity, nodeName, nodeType, node);
    }

    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        return false;
    }

    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        StatsConsole.printlnError(String.format("%s [UNHANDLED *CHILD* PROPERTY]: %s->%s --- %s --- %s",
                this.getClass().getSimpleName(),
                childEntity.getClass().getSimpleName(),
                nodeName,
                nodeType,
                node.asText("<empty>")));
        return true;
    }

    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        return false;
    }

    public boolean handleAuxId(long auxEntityId) {
        return auxEntityId == 0 || Objects.equals(id.getId(), auxEntityId);
    }

    public JsonNode transformChildNode(final String currentNodeName, final int index, final JsonNode childNode) {
        return childNode;
    }

    public EntityId getId() {
        return id;
    }
    public final BaseEntity getParent() {
        return __parent;
    }
    public final BaseRootEntity getRoot() {
        return __parent == null ? (BaseRootEntity)this : __parent.getRoot();
    }

    public long getNext() {
        ++__next;
        if (__next < 1 || __next > 999)
            throw new OutOfRangeException(__next, 1, 999);
        return (this.id.getId() << 3) + __next;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '{' +
                "id=" + getId() +
                ", ......}";
    }

}
