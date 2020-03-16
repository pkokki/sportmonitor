package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsSeasonOverUnder extends BaseRootEntity {
    private EntityId seasonId;
    private EntityId statsSeasonOverUnderId;
    private EntityIdList statsTeamOverUnders = new EntityIdList();

    public StatsSeasonOverUnder(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonOverUnder, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); return true;
            case "league.totals": this.statsSeasonOverUnderId = childEntity.getId(); return true;
            default:
                if (entityName.startsWith("stats.")) {
                    statsTeamOverUnders.add(childEntity.getId());
                    return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("league.totals")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "stats_season_over_under");
            objNode.put("_id", this.seasonId.asLong());
        } else if (currentNodeName.startsWith("stats.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "stats_team_over_under");
            objNode.put("_id", childNode.get("team").get("_id").asLong());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.startsWith("values.")) return true;
        return super.handleProperty(nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonOverUnder{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", seasonId=").append(seasonId);
        sb.append(", statsSeasonOverUnderId=").append(statsSeasonOverUnderId);
        sb.append(", statsTeamOverUnders=").append(statsTeamOverUnders);
        sb.append('}');
        return sb.toString();
    }
}
