package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;


public class StatsSeasonGoals extends BaseRootEntity {
    private EntityId seasonId;
    private Integer matches, scoredSum, scored0015, scored1630, scored3145, scored4660, scored6175, scored7690;
    private EntityIdList teamGoalStats = new EntityIdList();
    private EntityIdList tables = new EntityIdList();

    public StatsSeasonGoals(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonGoals, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); break;
            case "tables[]": this.tables.add(childEntity.getId()); break;
            case "teams[]": this.teamGoalStats.add(childEntity.getId()); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("teams")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "team_goal_stats");
            objNode.put("_id", this.getRoot().getNext());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "totals.matches": this.matches = node.asInt(); break;
            case "totals.scoredsum": this.scoredSum = node.asInt(); break;
            case "totals.scored.0-15": this.scored0015 = node.asInt(); break;
            case "totals.scored.16-30": this.scored1630 = node.asInt(); break;
            case "totals.scored.31-45": this.scored3145 = node.asInt(); break;
            case "totals.scored.46-60": this.scored4660 = node.asInt(); break;
            case "totals.scored.61-75": this.scored6175 = node.asInt(); break;
            case "totals.scored.76-90": this.scored7690 = node.asInt(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonGoals{");
        sb.append("name=").append(getName());
        sb.append(", seasonId=").append(seasonId);
        sb.append(", matches=").append(matches);
        sb.append(", scoredSum=").append(scoredSum);
        sb.append(", scored0015=").append(scored0015);
        sb.append(", scored1630=").append(scored1630);
        sb.append(", scored3145=").append(scored3145);
        sb.append(", scored4660=").append(scored4660);
        sb.append(", scored6175=").append(scored6175);
        sb.append(", scored7690=").append(scored7690);
        sb.append(", teamGoalStats=").append(teamGoalStats);
        sb.append(", tables=").append(tables);
        sb.append('}');
        return sb.toString();
    }
}
