package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;

public class TeamPlayerTopListEntryEntity extends BaseTimeEntity {
    private EntityId uniqueTeamId;
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
        super(parent, new EntityId(TeamPlayerTopListEntryEntity.class, id, timeStamp));
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        this.uniqueTeamId = new EntityId(UniqueTeamEntity.class, auxEntityId);
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
            case "assists": this.assists = node.asInt(); break;
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
        return "TeamPlayerTopListEntryEntity{" + "id=" + getId() +
                ", uniqueTeamId=" + uniqueTeamId +
                ", playerId=" + playerId +
                ", active=" + active +
                ", lastEvent='" + lastEvent + '\'' +
                ", started=" + started +
                ", goals=" + goals +
                ", assists=" + assists +
                ", matches=" + matches +
                ", penalties=" + penalties +
                ", goalPoints=" + goalPoints +
                ", minutesPlayed=" + minutesPlayed +
                ", substitutedIn=" + substitutedIn +
                ", firstGoals=" + firstGoals +
                ", lastGoals=" + lastGoals +
                ", shirtNumber=" + shirtNumber +
                ", yellowCards=" + yellowCards +
                ", yellowRedCards=" + yellowRedCards +
                ", redCards=" + redCards +
                ", firstHalfCards=" + firstHalfCards +
                ", secondHalfCards=" + secondHalfCards +
                '}';
    }
}
