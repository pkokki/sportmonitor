package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.SeasonEntity;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;
import com.panos.sportmonitor.stats.entities.ref.TeamFormTableEntity;
import com.panos.sportmonitor.stats.entities.ref.UniqueTeamSeasonPlayerEntity;

public class StatsFormTable extends BaseRootEntity {
    private final EntityId seasonId;
    private Integer __currentRound;

    public StatsFormTable(long timeStamp, long seasonId) {
        super(BaseRootEntityType.StatsFormTable, timeStamp);
        this.seasonId = SeasonEntity.createId(seasonId);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "currentround": this.__currentRound = node.asInt(); break;
            case "winpoints":
            case "losspoints":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch(entityName) {
            case "matchtype[]":
            case "tabletype[]":
            case "teams[]":
            case "season":
                break;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.equals("teams[]")) {
            return new TeamFormTableEntity(this, seasonId,
                    UniqueTeamEntity.createId(node.get("team").get("uid").asLong()), __currentRound);
        }
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    public String toString() {
        return "StatsFormTable{" + "name='" + getName() + "'" +
                ", seasonId=" + seasonId +
                '}';
    }
}
