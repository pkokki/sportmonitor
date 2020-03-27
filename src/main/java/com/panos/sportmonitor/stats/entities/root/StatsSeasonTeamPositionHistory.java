package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.ref.SeasonPosEntity;

public class StatsSeasonTeamPositionHistory extends BaseRootEntity {
    private EntityId seasonId;

    public StatsSeasonTeamPositionHistory(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonTeamPositionHistory, timeStamp);
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.startsWith("positiondata.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.remove("position");
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.startsWith("previousseason.") || nodeName.startsWith("currentseason.")) {
            long uniqueTeamId = Long.parseLong(nodeName.replace("[]", "").split("\\.")[1]);
            return new SeasonPosEntity(this, seasonId, uniqueTeamId, node.get("round").asInt());
        }
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); return true;
            default:
                if (entityName.startsWith("positiondata") || entityName.startsWith("tables") || entityName.startsWith("teams") ||
                        entityName.startsWith("previousseason") || entityName.startsWith("currentseason"))
                    return true;
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.startsWith("jersey"))
            return true;
        switch (nodeName) {
            case "teamcount":
            case "roundcount":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonTeamPositionHistory{");
        sb.append("name='").append(getName()).append("'");
        sb.append(",seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
