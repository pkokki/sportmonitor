package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class PromotionEntity extends BaseEntity {
    private Integer code;
    private String name;
    private String shortName;
    private Integer position;

    public PromotionEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(PromotionEntity.class, id));
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "code": this.code = node.asInt(); break;
            case "shortname": this.shortName = node.asText(); break;
            case "cssclass": break;
            case "position": this.position = node.asInt(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "PromotionEntity{" + "id=" + getId() +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", position=" + position +
                '}';
    }
}
