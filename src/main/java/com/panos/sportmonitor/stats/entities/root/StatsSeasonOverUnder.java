package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;

public class StatsSeasonOverUnder extends BaseRootEntity {
    private EntityId seasonId;
    private EntityId leagueTotalsId;
    private EntityIdList statsTeamOverUnders = new EntityIdList();

    public StatsSeasonOverUnder(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonOverUnder, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); return true;
            case "league.totals": this.leagueTotalsId = childEntity.getId(); return true;
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
            objNode.put("_doc", "season_over_under");
            objNode.put("_id", this.getRoot().getNext());
        } else if (currentNodeName.startsWith("stats.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "team_over_under");
            objNode.put("_id", this.getRoot().getNext());
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
        sb.append(", seasonId=").append(seasonId);
        sb.append(", leagueTotalsId=").append(leagueTotalsId);
        sb.append(", statsTeamOverUnders=").append(statsTeamOverUnders);
        sb.append('}');
        return sb.toString();
    }
}
