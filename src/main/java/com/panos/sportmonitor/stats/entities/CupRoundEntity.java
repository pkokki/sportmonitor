package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class CupRoundEntity extends BaseEntity {
    private String name;
    private String shortName;
    private Integer statisticsSortOrder;

    public CupRoundEntity(BaseEntity parent, long id) {
        super(parent, id);
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
        final StringBuilder sb = new StringBuilder("CupRoundEntity{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", statisticsSortOrder=").append(statisticsSortOrder);
        sb.append('}');
        return sb.toString();
    }
}
