package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class MatchTypeEntity extends BaseEntity {
    private String column;
    private Long setTypeId;

    public MatchTypeEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "column": this.column = node.asText(); break;
            case "settypeid": this.setTypeId = node.asLong(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableTypeEntity{");
        sb.append("id=").append(getId());
        sb.append(", column='").append(column).append('\'');
        sb.append(", setTypeId=").append(setTypeId);
        sb.append('}');
        return sb.toString();
    }
}
