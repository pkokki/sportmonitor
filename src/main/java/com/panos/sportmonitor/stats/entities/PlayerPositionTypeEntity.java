package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class PlayerPositionTypeEntity extends BaseEntity {
    private String type;
    private String name;
    private String shortName;
    private String abbr;

    public PlayerPositionTypeEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_type": this.type = node.asText(); break;
            case "name": this.name = node.asText(); break;
            case "shortname": this.shortName = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlayerPositionTypeEntity{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
