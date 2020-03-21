package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class MatchTypeEntity extends BaseEntity {
    private String name;
    private Long setTypeId;

    public MatchTypeEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, MatchTypeEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "column": this.name = node.asText(); break;
            case "settypeid": this.setTypeId = node.asLong(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "MatchTypeEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", setTypeId=" + setTypeId +
                '}';
    }
}
