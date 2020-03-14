package com.panos.sportmonitor.stats;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.*;

import java.util.List;

public abstract class BaseEntity implements Diffable<BaseEntity> {
    private final static List<String> IGNORED = Lists.newArrayList("_doc",/* "_mid",*/ "_id", "_sid");
    private final EntityId id;
    private long auxId;
    private final BaseEntity parent;

    public BaseEntity(BaseEntity parent, long id) {
        this(parent, new EntityId(id));
    }
    public BaseEntity(BaseEntity parent, EntityId id) {
        this.parent = parent;
        this.id = id;
    }

    public final boolean setProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (node.isNull() || (node.isTextual() && node.asText().length() == 0) || IGNORED.contains(nodeName) )
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
        return auxEntityId == 0 || id.equals(new EntityId(auxEntityId));
    }

    public JsonNode transformChildNode(final String currentNodeName, final int index, final JsonNode childNode) {
        return childNode;
    }

    public EntityId getId() {
        return id;
    }
    public final BaseEntity getParent() {
        return parent;
    }
    public final BaseRootEntity getRoot() {
        return parent == null ? (BaseRootEntity)this : parent.getRoot();
    }
    public final long getAuxId() { return this.auxId; }
    public final void setAuxId(long auxId) { this.auxId = auxId; }

    public DiffResult diff(BaseEntity obj) {
        // No need for null check, as NullPointerException correct if obj is null
        return new ReflectionDiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE)
                .build();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append('{');
        sb.append("id=").append(getId());
        sb.append(", ......}");
        return sb.toString();
    }
}
