package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.entities.root.RootEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseEntity {
    private final long id;
    private long auxId;
    private final static List<String> IGNORED = Lists.newArrayList("_doc", "_mid", "_id", "_sid");
    private final BaseEntity parent;

    protected BaseEntity(BaseEntity parent, long id) {
        this.parent = parent;
        this.id = id;
    }

    public final boolean setProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (IGNORED.contains(nodeName))
            return true;
        return handleProperty(nodeName, nodeType, node);
    }

    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        return node.isNull();
    }

    public final boolean setEntity(String entityName, BaseEntity childEntity) {
        return handleChildEntity(entityName, childEntity);
    }

    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        return false;
    }

    public long getId() {
        return id;
    }
    public final BaseEntity getParent() {
        return parent;
    }
    public final RootEntity getRoot() {
        return parent == null ? (RootEntity)this : parent.getRoot();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("{id=").append(id);
        sb.append(", ......}");
        return sb.toString();
    }

    public boolean handleRemoteProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        return handleProperty(nodeName, nodeType, node);
    }

    public boolean handleAuxId(long auxEntityId) {
        return auxEntityId == 0 || auxEntityId == id;
    }

    public final long getAuxId() { return this.auxId; }
    public final void setAuxId(long auxId) { this.auxId = auxId; }

    public JsonNode transformChildNode(final String currentNodeName, final int index, final JsonNode childNode) {
        return childNode;
    }
}
