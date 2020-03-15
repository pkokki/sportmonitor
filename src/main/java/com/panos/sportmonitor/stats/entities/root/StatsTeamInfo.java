package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

public class StatsTeamInfo extends BaseRootEntity {
    private EntityId uniqueTeamId;
    private EntityId stadiumId;
    private EntityId managerId;
    private Long managerMemberSince;
    private String twitter;
    private String hashtag;
    private String matchup;
    private EntityIdList tournaments = new EntityIdList();

    public StatsTeamInfo(long timeStamp) {
        super(BaseRootEntityType.StatsTeamInfo, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "team": this.uniqueTeamId = childEntity.getId(); return true;
            case "tournaments[]": this.tournaments.add(childEntity.getId()); return true;
            case "stadium": this.stadiumId = childEntity.getId(); return true;
            case "manager": this.managerId = childEntity.getId(); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            this.managerMemberSince = node.asLong();
            return true;
        }
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "twitter": this.twitter = node.asText(); break;
            case "hashtag": this.hashtag = node.asText(); break;
            case "matchup": this.matchup = node.asText(); break;
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
        sb.append(", stadiumId=").append(stadiumId);
        sb.append(", managerId=").append(managerId);
        sb.append(", managerMemberSince=").append(managerMemberSince);
        sb.append(", twitter='").append(twitter).append('\'');
        sb.append(", hashtag='").append(hashtag).append('\'');
        sb.append(", matchup='").append(matchup).append('\'');
        sb.append(", tournamentsIds=").append(tournaments);
        sb.append('}');
        return sb.toString();
    }
}
