package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class StatsFormTable extends RootEntity {
    private Long seasonId;
    private List<Long> teamFormTables = new ArrayList<>();
    private Integer winPoints, lossPoints, currentRound;
    private List<Long> matchTypes = new ArrayList<>();
    private List<Long> tableTypes = new ArrayList<>();

    public StatsFormTable(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "winpoints": this.winPoints = node.asInt(); break;
            case "losspoints": this.lossPoints = node.asInt(); break;
            case "currentround": this.currentRound = node.asInt(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch(entityName) {
            case "matchtype[]": this.matchTypes.add(childEntity.getId()); break;
            case "tabletype[]": this.tableTypes.add(childEntity.getId()); break;
            case "season": this.seasonId = childEntity.getId(); break;
            case "teams[]": this.teamFormTables.add(childEntity.getId()); break;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(final String currentNodeName, final int index, final JsonNode childNode) {
        if (currentNodeName.equals("teams")) {
            ObjectNode objNode = (ObjectNode)childNode;
            JsonNode teamNode = objNode.remove("team");
            objNode.put("_doc", "team_form_table");
            objNode.put("_id", teamNode.get("_id").asLong());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsFormTable{");
        sb.append("name='").append(getName()).append("'");
        sb.append(", seasonId=").append(seasonId);
        sb.append(", teamFormTables=").append(teamFormTables);
        sb.append(", winPoints=").append(winPoints);
        sb.append(", lossPoints=").append(lossPoints);
        sb.append(", currentRound=").append(currentRound);
        sb.append(", matchTypes=").append(matchTypes);
        sb.append(", tableTypes=").append(tableTypes);
        sb.append('}');
        return sb.toString();
    }
}
