package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class PlayerPositionTypeEntity extends BaseEntity {
    private String type;
    private String name;
    private String shortName;
    private String abbr;

    public PlayerPositionTypeEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, PlayerPositionTypeEntity.class));
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
        return "PlayerPositionTypeEntity{" + "id='" + getId() + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", abbr='" + abbr + '\'' +
                '}';
    }
}
