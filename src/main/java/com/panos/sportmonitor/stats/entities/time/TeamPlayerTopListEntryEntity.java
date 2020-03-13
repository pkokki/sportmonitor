package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;

public class TeamPlayerTopListEntryEntity extends BaseTimeEntity {
    private Long uniqueTeamId;
    private Boolean playerId;
    private Boolean active;
    private String lastEvent;
    private Integer started;
    private Integer goals, assists;
    private Integer matches;
    private Integer penalties;
    private Integer goalPoints;
    private Integer minutesPlayed;
    private Integer substitutedIn;
    private Integer firstGoals;
    private Integer lastGoals;
    private Integer shirtNumber;
    private Integer yellowCards;
    private Integer yellowRedCards;
    private Integer redCards;
    private Integer firstHalfCards;
    private Integer secondHalfCards;

    public TeamPlayerTopListEntryEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, id, timeStamp);
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        this.uniqueTeamId = auxEntityId;
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "playerid": this.playerId = node.asBoolean(); break;
            case "active": this.active = node.asBoolean(); break;
            case "lastevent": this.lastEvent = node.asText(); break;
            case "started": this.started = node.asInt(); break;
            case "goals": this.goals = node.asInt(); break;
            case "assists": this.goals = node.asInt(); break;
            case "matches": this.matches = node.asInt(); break;
            case "penalties": this.penalties = node.asInt(); break;
            case "goal_points": this.goalPoints = node.asInt(); break;
            case "minutes_played": this.minutesPlayed = node.asInt(); break;
            case "substituted_in": this.substitutedIn = node.asInt(); break;
            case "first_goals": this.firstGoals = node.asInt(); break;
            case "last_goals": this.lastGoals = node.asInt(); break;
            case "yellow_cards": this.yellowCards = node.asInt(); break;
            case "yellowred_cards": this.yellowRedCards = node.asInt(); break;
            case "red_cards": this.redCards = node.asInt(); break;
            case "number_of_cards_1st_half": this.firstHalfCards = node.asInt(); break;
            case "number_of_cards_2nd_half": this.secondHalfCards = node.asInt(); break;
            case "shirtnumber": this.shirtNumber = node.asInt(); break;
            case "teamid": break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamPlayerTopListEntryEntity{");
        sb.append("id=").append(getId());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append(", playerId=").append(playerId);
        sb.append(", active=").append(active);
        sb.append(", lastEvent='").append(lastEvent).append('\'');
        sb.append(", started=").append(started);
        sb.append(", goals=").append(goals);
        sb.append(", assists=").append(assists);
        sb.append(", matches=").append(matches);
        sb.append(", penalties=").append(penalties);
        sb.append(", goalPoints=").append(goalPoints);
        sb.append(", minutesPlayed=").append(minutesPlayed);
        sb.append(", substitutedIn=").append(substitutedIn);
        sb.append(", firstGoals=").append(firstGoals);
        sb.append(", lastGoals=").append(lastGoals);
        sb.append(", shirtNumber=").append(shirtNumber);
        sb.append(", yellowCards=").append(yellowCards);
        sb.append(", yellowRedCards=").append(yellowRedCards);
        sb.append(", redCards=").append(redCards);
        sb.append(", firstHalfCards=").append(firstHalfCards);
        sb.append(", secondHalfCards=").append(secondHalfCards);
        sb.append('}');
        return sb.toString();
    }
}
