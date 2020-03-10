package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.entities.root.RootEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseEntity {
    private final long id;
    private final List<String> ignored;
    private final BaseEntity parent;

    protected BaseEntity(BaseEntity parent, long id) {
        this.parent = parent;
        this.id = id;
        this.ignored = new ArrayList<>();
        this.ignored.addAll(Arrays.asList("_doc", "_mid", "_id", "_sid"));
    }

    public final boolean setProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (isIgnored(nodeName))
            return true;
        return handleProperty(nodeName, nodeType, node);
    }

    private boolean isIgnored(String nodeName) {
        return this.ignored.contains(nodeName);
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
    public BaseEntity getParent() {
        return parent;
    }
    public RootEntity getRoot() {
        return parent == null ? (RootEntity)this : parent.getRoot();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("{id=").append(id);
        sb.append(", ......}");
        return sb.toString();
    }
}
