package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class TableRoundEntity extends BaseEntity {
    private String name;

    public TableRoundEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, TableRoundEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if ("name".equals(nodeName)) {
            this.name = node.asText();
        } else {
            return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "TableRoundEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
