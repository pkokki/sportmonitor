package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsSeasonTeamPositionHistory extends BaseRootEntity {
    private EntityId seasonId;
    private Integer teamCount;
    private Integer roundCount;
    private EntityIdList promotions = new EntityIdList();
    private EntityIdList tables = new EntityIdList();
    private EntityIdList teams = new EntityIdList();
    private EntityIdList seasonPositions = new EntityIdList();

    public StatsSeasonTeamPositionHistory(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonTeamPositionHistory, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); return true;
            default:
                if (entityName.startsWith("positiondata")) this.promotions.add(childEntity.getId());
                else if (entityName.startsWith("tables")) this.tables.add(childEntity.getId());
                else if (entityName.startsWith("teams")) this.teams.add(childEntity.getId());
                else if (entityName.startsWith("previousseason") || entityName.startsWith("currentseason")) {
                    seasonPositions.add(childEntity.getId());
                }
                else return super.handleChildEntity(entityName, childEntity);
                return true;
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.startsWith("jersey"))
            return true;
        switch (nodeName) {
            case "teamcount": this.teamCount = node.asInt(); break;
            case "roundcount": this.roundCount = node.asInt(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.startsWith("positiondata.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("code", childNode.get("_id").asInt());
            objNode.put("_id", currentNodeName.substring(currentNodeName.indexOf('.') + 1));
        }
        else if (currentNodeName.startsWith("previousseason.") || currentNodeName.startsWith("currentseason.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            long seasonId = childNode.get("seasonid").asInt();
            long teamId =  Long.parseLong(currentNodeName.substring(currentNodeName.indexOf('.') + 1).replace("[", "").replace("]", ""));
            long roundId = childNode.get("round").asInt();
            objNode.put("_id", (seasonId << 10) + (teamId << 2) + roundId);
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonTeamPositionHistory{");
        sb.append("name='").append(getName()).append("'");
        sb.append(",seasonId=").append(seasonId);
        sb.append(", teamCount=").append(teamCount);
        sb.append(", roundCount=").append(roundCount);
        sb.append(", promotions=").append(promotions);
        sb.append(", tables=").append(tables);
        sb.append(", teams=").append(teams);
        sb.append(", seasonPositions=").append(seasonPositions);
        sb.append('}');
        return sb.toString();
    }
}
