package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

public class StatsTeamVersus extends BaseRootEntity {

    public StatsTeamVersus(long timeStamp) {
        super(BaseRootEntityType.StatsTeamVersus, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "matches[]":
                return true;
            case "next":
                //this.nextMatchId = new EntityId(childEntity);
                return true;
            default:
                if (entityName.startsWith("tournaments.")) {
                    return true;
                } else if (entityName.startsWith("realcategories.")) {
                    return true;
                } else if (entityName.startsWith("teams.")) {
                    return true;
                }
                else if (entityName.startsWith("currentmanagers.")) {
                    //long teamId = Long.parseLong(entityName.split("\\.|\\[")[1]);
                   //this.currentManagers.put(teamId, childEntity.getId());
                    return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "livematchid": break;
            default:
                if (nodeName.startsWith("jersey.")) return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            //this.currentManagerSince.put(childEntity.getId(), node.asLong());
            return true;
        }
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamVersus{");
        sb.append("name='").append(getName()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
