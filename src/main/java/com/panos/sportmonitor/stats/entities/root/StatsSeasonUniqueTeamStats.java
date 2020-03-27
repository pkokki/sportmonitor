package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.time.UniqueTeamStatsEntity;

public class StatsSeasonUniqueTeamStats extends BaseRootEntity {
    private EntityId seasonId;

    public StatsSeasonUniqueTeamStats(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonUniqueTeamStats, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); break;
            default:
                if (entityName.startsWith("stats.uniqueteams.")) {
                    break;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.startsWith("stats.uniqueteams."))
            return new UniqueTeamStatsEntity(this, seasonId, node.get("uniqueteam").get("_id").asLong(), timeStamp);
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonUniqueTeamStats{");
        sb.append("name=").append(getName());
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
