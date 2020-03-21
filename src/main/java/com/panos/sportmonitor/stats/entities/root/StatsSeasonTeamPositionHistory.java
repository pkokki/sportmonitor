package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;

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
            case "season": this.seasonId = new EntityId(childEntity); return true;
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
            objNode.put("_id", this.getRoot().getNext());
        }
        else if (currentNodeName.startsWith("previousseason.") || currentNodeName.startsWith("currentseason.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_id", this.getRoot().getNext());
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
