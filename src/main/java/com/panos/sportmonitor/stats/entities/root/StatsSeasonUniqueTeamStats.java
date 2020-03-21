package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;

public class StatsSeasonUniqueTeamStats extends BaseRootEntity {
    private EntityId seasonId;
    private EntityIdList uniqueTeamStats = new EntityIdList();

    public StatsSeasonUniqueTeamStats(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonUniqueTeamStats, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); break;
            default:
                if (entityName.startsWith("stats.uniqueteams.")) {
                    this.uniqueTeamStats.add(childEntity.getId());
                    break;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.startsWith("stats.uniqueteams.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "unique_team_stats");
            objNode.put("_id", this.getRoot().getNext());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonUniqueTeamStats{");
        sb.append("name=").append(getName());
        sb.append(", seasonId=").append(seasonId);
        sb.append(", uniqueTeamStats=").append(uniqueTeamStats);
        sb.append('}');
        return sb.toString();
    }
}
