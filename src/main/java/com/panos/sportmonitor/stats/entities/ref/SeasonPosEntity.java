package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class SeasonPosEntity extends BaseEntity {
    private EntityId uniqueTeamId;
    private EntityId seasonId;
    private EntityId matchId;
    private Integer round;
    private Integer position;
    private String moved;

    public SeasonPosEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        this.uniqueTeamId = new EntityId(auxEntityId);
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "round": this.round = node.asInt(); break;
            case "position": this.position = node.asInt(); break;
            case "seasonid": this.seasonId = new EntityId(node.asLong()); break;
            case "matchid": this.matchId = new EntityId(node.asLong()); break;
            case "moved": this.moved = node.asText(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SeasonPosEntity{");
        sb.append("id=").append(getId());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append(", seasonId=").append(seasonId);
        sb.append(", round=").append(round);
        sb.append(", position=").append(position);
        sb.append(", matchId=").append(matchId);
        sb.append(", moved='").append(moved).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
