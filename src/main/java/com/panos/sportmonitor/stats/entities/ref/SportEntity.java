package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class SportEntity extends BaseEntity {
    private String name;

    public SportEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, SportEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("name"))
            this.name = node.asText();
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        return true;
    }

    @Override
    public String toString() {
        return "SportEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
