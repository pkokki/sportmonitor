package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class StatsTeamInfo extends RootEntity {
    private Long uniqueTeamId;
    private Long stadiumId;
    private Long managerId;
    private String twitter;
    private String hashtag;
    private String matchup;
    private List<Long> tournamentIds = new ArrayList<>();

    public StatsTeamInfo(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "team": this.uniqueTeamId = childEntity.getId(); return true;
            case "tournaments[]": this.tournamentIds.add(childEntity.getId()); return true;
            case "stadium": this.stadiumId = childEntity.getId(); return true;
            case "manager": this.managerId = childEntity.getId(); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
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
        sb.append(", twitter='").append(twitter).append('\'');
        sb.append(", hashtag='").append(hashtag).append('\'');
        sb.append(", matchup='").append(matchup).append('\'');
        sb.append(", tournamentsIds=").append(tournamentIds);
        sb.append('}');
        return sb.toString();
    }
}
