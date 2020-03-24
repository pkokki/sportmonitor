package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class UniqueTeamSeasonPlayerEntity extends BaseEntity {
    private final long __teamId;
    private final long __playerId;
    private final long __seasonId;
    private Boolean active;
    private String lastEvent;
    private Integer started, goals, yellowCards, yellowredCards, redCards, matches, offside, shotsOnGoal, shotsOffGoal, shotsBlocked,
            assists, corners, penalties, goalPoints, minutesPlayed, substitutedIn, substitutedOut, teamScored, teamConceded, totalShots,
            matchesWon, matchesLost, matchesDrawn, firstGoals, lastGoals, numberOfCards1stHalf, numberOfCards2ndHalf, teamMatches, goalsByHeader;

    public static EntityId createId(long teamId, long seasonId, long playerId) {
        return new EntityId(UniqueTeamSeasonPlayerEntity.class,
                new EntityKey("teamId", teamId),
                new EntityKey("seasonId", seasonId),
                new EntityKey("playerId", playerId)
        );
    }

    public UniqueTeamSeasonPlayerEntity(BaseEntity parent, long teamId, long seasonId, long playerId) {
        super(parent, createId(teamId, seasonId, playerId));
        this.__teamId = teamId;
        this.__seasonId = seasonId;
        this.__playerId = playerId;
    }

    public long getTeamId() { return __teamId; }
    public long getSeasonId() {
        return __seasonId;
    }
    public long getPlayerId() {
        return __playerId;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "active": this.active = node.asBoolean(); break;
            case "lastevent": this.lastEvent = node.asText(); break;
            case "started": this.started = node.asInt(); break;
            case "goals": this.goals = node.asInt(); break;
            case "yellow_cards": this.yellowCards = node.asInt(); break;
            case "yellowred_cards": this.yellowredCards = node.asInt(); break;
            case "red_cards": this.redCards = node.asInt(); break;
            case "matches": this.matches = node.asInt(); break;
            case "offside": this.offside = node.asInt(); break;
            case "shots_on_goal": this.shotsOnGoal = node.asInt(); break;
            case "shots_off_goal": this.shotsOffGoal = node.asInt(); break;
            case "shots_blocked": this.shotsBlocked = node.asInt(); break;
            case "assists": this.assists = node.asInt(); break;
            case "goal_points": this.goalPoints = node.asInt(); break;
            case "goals_by_header": this.goalsByHeader = node.asInt(); break;
            case "minutes_played": this.minutesPlayed = node.asInt(); break;
            case "substituted_in": this.substitutedIn = node.asInt(); break;
            case "substituted_out": this.substitutedOut = node.asInt(); break;
            case "team_scored": this.teamScored = node.asInt(); break;
            case "team_conceded": this.teamConceded = node.asInt(); break;
            case "total_shots": this.totalShots = node.asInt(); break;
            case "matches_won": this.matchesWon = node.asInt(); break;
            case "matches_lost": this.matchesLost = node.asInt(); break;
            case "matches_drawn": this.matchesDrawn = node.asInt(); break;
            case "first_goals": this.firstGoals = node.asInt(); break;
            case "last_goals": this.lastGoals = node.asInt(); break;
            case "corners": this.corners = node.asInt(); break;
            case "penalties": this.penalties = node.asInt(); break;
            case "number_of_cards_2nd_half": this.numberOfCards2ndHalf = node.asInt(); break;
            case "number_of_cards_1st_half": this.numberOfCards1stHalf = node.asInt(); break;
            case "team_matches": this.teamMatches = node.asInt(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "UniqueTeamSeasonPlayerEntity{" + "id=" + getId() +
                ", active=" + active +
                ", lastEvent='" + lastEvent + '\'' +
                ", started=" + started +
                ", goals=" + goals +
                ", yellowCards=" + yellowCards +
                ", matches=" + matches +
                ", offside=" + offside +
                ", shotsOnGoal=" + shotsOnGoal +
                ", shotsOffGoal=" + shotsOffGoal +
                ", shotsBlocked=" + shotsBlocked +
                ", assists=" + assists +
                ", goalPoints=" + goalPoints +
                ", minutesPlayed=" + minutesPlayed +
                ", substitutedIn=" + substitutedIn +
                ", substitutedOut=" + substitutedOut +
                ", teamScored=" + teamScored +
                ", teamConceded=" + teamConceded +
                ", totalShots=" + totalShots +
                ", matchesWon=" + matchesWon +
                ", matchesLost=" + matchesLost +
                ", matchesDrawn=" + matchesDrawn +
                ", firstGoals=" + firstGoals +
                ", lastGoals=" + lastGoals +
                ", numberOfCards2ndHalf=" + numberOfCards2ndHalf +
                ", teamMatches=" + teamMatches +
                '}';
    }
}
