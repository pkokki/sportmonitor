package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

public class TableRoundEntity extends BaseEntity {
    private String name;

    public TableRoundEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableRoundEntity{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
