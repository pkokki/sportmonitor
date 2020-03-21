package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.MatchEntity;
import com.panos.sportmonitor.stats.entities.SeasonEntity;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;

public class SeasonPosEntity extends BaseEntity {
    private EntityId uniqueTeamId;
    private EntityId seasonId;
    private EntityId matchId;
    private Integer round;
    private Integer position;
    private String moved;

    public SeasonPosEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, SeasonPosEntity.class));
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        this.uniqueTeamId = new EntityId(auxEntityId, UniqueTeamEntity.class);
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "round": this.round = node.asInt(); break;
            case "position": this.position = node.asInt(); break;
            case "seasonid": this.seasonId = new EntityId(node.asLong(), SeasonEntity.class); break;
            case "matchid": this.matchId = new EntityId(node.asLong(), MatchEntity.class); break;
            case "moved": this.moved = node.asText(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "SeasonPosEntity{" + "id=" + getId() +
                ", uniqueTeamId=" + uniqueTeamId +
                ", seasonId=" + seasonId +
                ", round=" + round +
                ", position=" + position +
                ", matchId=" + matchId +
                ", moved='" + moved + '\'' +
                '}';
    }
}
