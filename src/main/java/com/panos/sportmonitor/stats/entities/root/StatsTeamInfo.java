package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

public class StatsTeamInfo extends BaseRootEntity {
    private EntityId uniqueTeamId;
    private BaseEntity __uniqueTeam;

    public StatsTeamInfo(long timeStamp) {
        super(BaseRootEntityType.StatsTeamInfo, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "team":
                this.uniqueTeamId = new EntityId(childEntity);
                this.__uniqueTeam = childEntity;
                return true;
            case "stadium":
            case "manager":
                return __uniqueTeam.setEntity(entityName, childEntity);
            case "tournaments[]":
            case "historytournaments[]":
                return  true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            return true;
        }
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "twitter":
            case "hashtag":
            case "matchup":
                this.__uniqueTeam.setProperty(nodeName, nodeType, node);
                break;
            default:
                if (nodeName.startsWith("homejersey") || nodeName.startsWith("awayjersey") || nodeName.startsWith("gkjersey"))
                    return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamInfo{");
        sb.append("name=").append(getName());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append('}');
        return sb.toString();
    }
}
