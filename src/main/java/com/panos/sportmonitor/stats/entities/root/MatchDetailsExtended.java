package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.MatchEntity;

public class MatchDetailsExtended extends BaseRootEntity {
    private EntityId matchId;
    private String teamHome, teamAway;
    private EntityIdList entries = new EntityIdList();

    public MatchDetailsExtended(long timeStamp) {
        super(BaseRootEntityType.MatchDetailsExtended, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_matchid": this.matchId = new EntityId(node.asLong(), MatchEntity.class); break;
            case "teams.home": this.teamHome = node.asText(); break;
            case "teams.away": this.teamAway = node.asText(); break;

            case "index[]":
                break;
            default:
                if (nodeName.startsWith("types.")) {
                    //this.types.put(nodeName.substring(6), node.asText());
                    return true;
                }
                else
                    return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.startsWith("values.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "match_details_entry");
            objNode.put("_id", this.getRoot().getNext());
            objNode.put("code", currentNodeName.substring(7));
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.startsWith("values.")) {
            this.entries.add(childEntity.getId());
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchDetailsExtended{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", matchId=").append(matchId);
        sb.append(", teamHome='").append(teamHome).append('\'');
        sb.append(", teamAway='").append(teamAway).append('\'');
        sb.append(", entries=").append(entries);
        sb.append('}');
        return sb.toString();
    }
}
