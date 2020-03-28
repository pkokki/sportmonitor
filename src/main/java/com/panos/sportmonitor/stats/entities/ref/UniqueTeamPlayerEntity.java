package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class UniqueTeamPlayerEntity extends BaseEntity {
    private final long __teamId;
    private final long __playerId;
    private Long startTime;
    private Long endTime;
    private Integer type;
    private Boolean active;
    private String shirt;
    private Integer totalGoals, totalYellowCards, totalMatches, totalOffside, totalShotsOnGoal, totalShotsOffGoal, totalShotsBlocked,
            totalAssists, totalGoalPoints, totalMinutesPlayed, totalSubstitutedIn, totalSubstitutedOut,
            totalTeamScored, totalTeamConceded, totalTotalShots, totalMatchesWon, totalMatchesLost, totalMatchesDrawn,
            totalFirstGoals, totalLastGoals, totalOwnGoals, totalNumberOfCards1stHalf, totalNumberOfCards2ndHalf, homeGoals, awayGoals, firstHalfGoals, secondHalfGoals,
            totalYellowredCards, totalGoalsByHeader, totalCorners, totalRedCards, totalPenalties;

    public static EntityId createId(long teamId, long playerId) {
        return new EntityId(UniqueTeamPlayerEntity.class,
                new EntityKey("teamId", teamId),
                new EntityKey("playerId", playerId)
        );
    }

    public UniqueTeamPlayerEntity(BaseEntity parent, long teamId, long playerId) {
        super(parent, createId(teamId, playerId));
        this.__teamId = teamId;
        this.__playerId = playerId;
    }

    public long getTeamId() {
        return __teamId;
    }

    public long getPlayerId() {
        return __playerId;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        // UniqueTeamPlayerEntity [UNHANDLED AUX ID]: 'roles.7129[]' --- id=CompositeId{teamId=3252, playerId=7129, type=1}, aux=7129
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "start.uts": this.setStartTime(node.asLong()); break;
            case "end.uts": this.endTime = node.asLong(); break;
            case "active": this.active = node.asBoolean(); break;
            case "shirt": this.shirt = node.asText(); break;
            case "_type": this.type = node.asInt(); break;

            case "total.goals": this.totalGoals = node.asInt(); break;
            case "total.yellow_cards": this.totalYellowCards = node.asInt(); break;
            case "total.matches": this.totalMatches = node.asInt(); break;
            case "total.offside": this.totalOffside = node.asInt(); break;
            case "total.shots_on_goal": this.totalShotsOnGoal = node.asInt(); break;
            case "total.shots_off_goal": this.totalShotsOffGoal = node.asInt(); break;
            case "total.shots_blocked": this.totalShotsBlocked = node.asInt(); break;
            case "total.assists": this.totalAssists = node.asInt(); break;
            case "total.goal_points": this.totalGoalPoints = node.asInt(); break;
            case "total.minutes_played": this.totalMinutesPlayed = node.asInt(); break;
            case "total.substituted_in": this.totalSubstitutedIn = node.asInt(); break;
            case "total.substituted_out": this.totalSubstitutedOut = node.asInt(); break;
            case "total.team_scored": this.totalTeamScored = node.asInt(); break;
            case "total.team_conceded": this.totalTeamConceded = node.asInt(); break;
            case "total.total_shots": this.totalTotalShots = node.asInt(); break;
            case "total.matches_won": this.totalMatchesWon = node.asInt(); break;
            case "total.matches_lost": this.totalMatchesLost = node.asInt(); break;
            case "total.matches_drawn": this.totalMatchesDrawn = node.asInt(); break;
            case "total.first_goals": this.totalFirstGoals = node.asInt(); break;
            case "total.last_goals": this.totalLastGoals = node.asInt(); break;
            case "total.own_goals": this.totalOwnGoals = node.asInt(); break;
            case "total.number_of_cards_2nd_half": this.totalNumberOfCards2ndHalf = node.asInt(); break;
            case "total.number_of_cards_1st_half": this.totalNumberOfCards1stHalf = node.asInt(); break;
            case "home.goals": this.homeGoals = node.asInt(); break;
            case "away.goals": this.awayGoals = node.asInt(); break;
            case "firsthalf.goals": this.firstHalfGoals = node.asInt(); break;
            case "secondhalf.goals": this.secondHalfGoals = node.asInt(); break;
            case "total.yellowred_cards": this.totalYellowredCards = node.asInt(); break;
            case "total.goals_by_header": this.totalGoalsByHeader = node.asInt(); break;
            case "total.corners": this.totalCorners = node.asInt(); break;
            case "total.red_cards": this.totalRedCards = node.asInt(); break;
            case "total.penalties": this.totalPenalties = node.asInt(); break;

            case "_playerid":
            case "playerid":
            case "name":
            case "start._doc":
            case "start.time":
            case "start.date":
            case "start.tz":
            case "start.tzoffset":
            case "end._doc":
            case "end.time":
            case "end.date":
            case "end.tz":
            case "end.tzoffset":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        String[] parts = nodeName.split("\\.");
        if (parts.length == 4 && parts[0].equals("teams") && parts[2].equals("seasons")) {
            long teamId = Long.parseLong(parts[1]);
            long seasonId = Long.parseLong(parts[3]);
            return new UniqueTeamSeasonPlayerEntity(this, teamId, seasonId, this.getPlayerId());
        }
        return null;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team") || entityName.equals("player") || entityName.matches("teams\\.\\d+\\.seasons\\.\\d+"))
            return true;
        return super.handleChildEntity(entityName, childEntity);
    }


    @Override
    public String toString() {
        return "UniqueTeamPlayerEntity{" +
                "id=" + getId() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", type=" + type +
                ", active=" + active +
                ", shirt='" + shirt + '\'' +
                ", totalGoals=" + totalGoals +
                ", totalYellowCards=" + totalYellowCards +
                ", totalMatches=" + totalMatches +
                ", totalOffside=" + totalOffside +
                ", totalShotsOnGoal=" + totalShotsOnGoal +
                ", totalShotsOffGoal=" + totalShotsOffGoal +
                ", totalShotsBlocked=" + totalShotsBlocked +
                ", totalAssists=" + totalAssists +
                ", totalGoalPoints=" + totalGoalPoints +
                ", totalMinutesPlayed=" + totalMinutesPlayed +
                ", totalSubstitutedIn=" + totalSubstitutedIn +
                ", totalSubstitutedOut=" + totalSubstitutedOut +
                ", totalTeamScored=" + totalTeamScored +
                ", totalTeamConceded=" + totalTeamConceded +
                ", totalTotalShots=" + totalTotalShots +
                ", totalMatchesWon=" + totalMatchesWon +
                ", totalMatchesLost=" + totalMatchesLost +
                ", totalMatchesDrawn=" + totalMatchesDrawn +
                ", totalFirstGoals=" + totalFirstGoals +
                ", totalLastGoals=" + totalLastGoals +
                ", totalNumberOfCards1stHalf=" + totalNumberOfCards1stHalf +
                ", totalNumberOfCards2ndHalf=" + totalNumberOfCards2ndHalf +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", firstHalfGoals=" + firstHalfGoals +
                ", secondHalfGoals=" + secondHalfGoals +
                '}';
    }
}
