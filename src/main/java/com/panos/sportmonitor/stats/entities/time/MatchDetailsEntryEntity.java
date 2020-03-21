package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;

public class MatchDetailsEntryEntity extends BaseTimeEntity {
    private String code;
    private String name;
    private Integer valueHome, valueHomeP1, valueHomeP2;
    private Integer valueAway, valueAwayP1, valueAwayP2;

    public MatchDetailsEntryEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, new EntityId(id, timeStamp, MatchDetailsEntryEntity.class));
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "code": this.code = node.asText(); break;
            case "name": this.name = node.asText(); break;
            case "value.home":
                if (node.isNumber())
                    this.valueHome = node.asInt();
                else if (node.isTextual()) {
                    String[] parts = node.asText().split("/");
                    this.valueHomeP1 = Integer.parseInt(parts[0]);
                    if (parts.length > 1)
                        this.valueHomeP2 = Integer.parseInt(parts[1]);
                }
                break;
            case "value.away":
                if (node.isNumber())
                    this.valueAway = node.asInt();
                else if (node.isTextual()) {
                    String[] parts = node.asText().split("/");
                    this.valueAwayP1 = Integer.parseInt(parts[0]);
                    if (parts.length > 1)
                        this.valueAwayP2 = Integer.parseInt(parts[1]);
                }
                break;
            default:
                if (nodeName.equals("value.home[]")) return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "MatchDetailsEntryEntity{" + "id=" + getId() +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", valueHome=" + valueHome +
                ", valueHomeP1=" + valueHomeP1 +
                ", valueHomeP2=" + valueHomeP2 +
                ", valueAway=" + valueAway +
                ", valueAwayP1=" + valueAwayP1 +
                ", valueAwayP2=" + valueAwayP2 +
                '}';
    }
}
