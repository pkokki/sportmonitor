package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class CupRoundEntity extends BaseEntity {
    private String name;
    private String shortName;
    private Integer statisticsSortOrder;

    public CupRoundEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, CupRoundEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "shortname": this.shortName = node.asText(); break;
            case "statisticssortorder": this.statisticsSortOrder = node.asInt(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "CupRoundEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", statisticsSortOrder=" + statisticsSortOrder +
                '}';
    }
}
