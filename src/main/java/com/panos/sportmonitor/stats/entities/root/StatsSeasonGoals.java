package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.ref.SeasonGoalStatsEntity;
import com.panos.sportmonitor.stats.entities.ref.UniqueTeamGoalStatsEntity;


public class StatsSeasonGoals extends BaseRootEntity {
    private EntityId seasonId;

    public StatsSeasonGoals(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonGoals, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); break;
            case "tables[]":
            case "teams[]":
            case "totals":
                break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.equals("teams[]"))
            return new UniqueTeamGoalStatsEntity(this, node.get("team").get("uid").asLong(), timeStamp);
        else if (nodeName.equals("totals"))
            return new SeasonGoalStatsEntity(this, this.seasonId, timeStamp);
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }


    @Override
    public String toString() {
        return "StatsSeasonGoals{" + "name=" + getName() +
                ", seasonId=" + seasonId +
                '}';
    }
}
