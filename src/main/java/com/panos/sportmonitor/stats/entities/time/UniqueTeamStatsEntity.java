package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;

public class UniqueTeamStatsEntity extends BaseTimeEntity {
    private EntityId uniqueTeamId;
    private Integer goalAttempts, shotsOnGoal, shotsOffGoal, cornerKicks, ballPossession, shotsBlocked,
            cardsGiven, freekicks, offside, shotsOnPost, shotsOnBar, goalsByFoot,
            goalsByHead, attendance, yellowCards, redCards, goalsScored, goalsConceded,
            yellowRedCards, shootingEfficiency;
    private Integer lateWinningGoals, penaltySuccessCount, penaltyFailCount, cleanSheetCount;
    private Double goalAttemptsAvg, shotsOnGoalAvg, shotsOffGoalAvg, cornerKicksAvg, ballPossessionAvg, shotsBlockedAvg,
            cardsGivenAvg, freekicksAvg, offsideAvg, shotsOnPostAvg, shotsOnBarAvg, goalsByFootAvg,
            goalsByHeadAvg, attendanceAvg, yellowCardsAvg, redCardsAvg, goalsScoredAvg, goalsConcededAvg,
            yellowRedCardsAvg;
    private String shootingEfficiencyAvg;
    private Integer goalAttemptsMatches, shotsOnGoalMatches, shotsOffGoalMatches, cornerKicksMatches, ballPossessionMatches, shotsBlockedMatches,
            cardsGivenMatches, freekicksMatches, offsideMatches, shotsOnPostMatches, shotsOnBarMatches, goalsByFootMatches,
            goalsByHeadMatches, attendanceMatches, yellowCardsMatches, redCardsMatches, goalsScoredMatches, goalsConcededMatches,
            yellowRedCardsMatches, shootingEfficiencyMatches;

    public UniqueTeamStatsEntity(BaseEntity parent, long uniqueTeamId, long timeStamp) {
        super(parent, createId(uniqueTeamId, timeStamp));
    }

    private static EntityId createId(long uniqueTeamId, long timeStamp) {
        return new EntityId(UniqueTeamStatsEntity.class,
                new EntityKey("uniqueTeamId", uniqueTeamId),
                new EntityKey(EntityId.KEY_TIMESTAMP, timeStamp));
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        this.uniqueTeamId = new EntityId(UniqueTeamEntity.class, auxEntityId);
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("uniqueteam")) {
            this.uniqueTeamId = new EntityId(childEntity);
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "goal_attempts.total": this.goalAttempts = node.asInt(); break;
            case "goal_attempts.average": this.goalAttemptsAvg = node.asDouble(); break;
            case "goal_attempts.matches": this.goalAttemptsMatches = node.asInt(); break;
            case "shots_on_goal.total": this.shotsOnGoal = node.asInt(); break;
            case "shots_on_goal.average": this.shotsOnGoalAvg = node.asDouble(); break;
            case "shots_on_goal.matches": this.shotsOnGoalMatches = node.asInt(); break;
            case "shots_off_goal.total": this.shotsOffGoal = node.asInt(); break;
            case "shots_off_goal.average": this.shotsOffGoalAvg = node.asDouble(); break;
            case "shots_off_goal.matches": this.shotsOffGoalMatches = node.asInt(); break;
            case "corner_kicks.total": this.cornerKicks = node.asInt(); break;
            case "corner_kicks.average": this.cornerKicksAvg = node.asDouble(); break;
            case "corner_kicks.matches": this.cornerKicksMatches = node.asInt(); break;
            case "ball_possession.total": this.ballPossession = node.asInt(); break;
            case "ball_possession.average": this.ballPossessionAvg = node.asDouble(); break;
            case "ball_possession.matches": this.ballPossessionMatches = node.asInt(); break;
            case "shots_blocked.total": this.shotsBlocked = node.asInt(); break;
            case "shots_blocked.average": this.shotsBlockedAvg = node.asDouble(); break;
            case "shots_blocked.matches": this.shotsBlockedMatches = node.asInt(); break;
            case "cards_given.total": this.cardsGiven = node.asInt(); break;
            case "cards_given.average": this.cardsGivenAvg = node.asDouble(); break;
            case "cards_given.matches": this.cardsGivenMatches = node.asInt(); break;
            case "freekicks.total": this.freekicks = node.asInt(); break;
            case "freekicks.average": this.freekicksAvg = node.asDouble(); break;
            case "freekicks.matches": this.freekicksMatches = node.asInt(); break;
            case "offside.total": this.offside = node.asInt(); break;
            case "offside.average": this.offsideAvg = node.asDouble(); break;
            case "offside.matches": this.offsideMatches = node.asInt(); break;
            case "shots_on_post.total": this.shotsOnPost = node.asInt(); break;
            case "shots_on_post.average": this.shotsOnPostAvg = node.asDouble(); break;
            case "shots_on_post.matches": this.shotsOnPostMatches = node.asInt(); break;
            case "shots_on_bar.total": this.shotsOnBar = node.asInt(); break;
            case "shots_on_bar.average": this.shotsOnBarAvg = node.asDouble(); break;
            case "shots_on_bar.matches": this.shotsOnBarMatches = node.asInt(); break;
            case "goals_by_foot.total": this.goalsByFoot = node.asInt(); break;
            case "goals_by_foot.average": this.goalsByFootAvg = node.asDouble(); break;
            case "goals_by_foot.matches": this.goalsByFootMatches = node.asInt(); break;
            case "goals_by_head.total": this.goalsByHead = node.asInt(); break;
            case "goals_by_head.average": this.goalsByHeadAvg = node.asDouble(); break;
            case "goals_by_head.matches": this.goalsByHeadMatches = node.asInt(); break;
            case "attendance.total": this.attendance = node.asInt(); break;
            case "attendance.average": this.attendanceAvg = node.asDouble(); break;
            case "attendance.matches": this.attendanceMatches = node.asInt(); break;
            case "yellow_cards.total": this.yellowCards = node.asInt(); break;
            case "yellow_cards.average": this.yellowCardsAvg = node.asDouble(); break;
            case "yellow_cards.matches": this.yellowCardsMatches = node.asInt(); break;
            case "red_cards.total": this.redCards = node.asInt(); break;
            case "red_cards.average": this.redCardsAvg = node.asDouble(); break;
            case "red_cards.matches": this.redCardsMatches = node.asInt(); break;
            case "goals_scored.total": this.goalsScored = node.asInt(); break;
            case "goals_scored.average": this.goalsScoredAvg = node.asDouble(); break;
            case "goals_scored.matches": this.goalsScoredMatches = node.asInt(); break;
            case "goals_conceded.total": this.goalsConceded = node.asInt(); break;
            case "goals_conceded.average": this.goalsConcededAvg = node.asDouble(); break;
            case "goals_conceded.matches": this.goalsConcededMatches = node.asInt(); break;
            case "yellowred_cards.total": this.yellowRedCards = node.asInt(); break;
            case "yellowred_cards.average": this.yellowRedCardsAvg = node.asDouble(); break;
            case "yellowred_cards.matches": this.yellowRedCardsMatches = node.asInt(); break;
            case "shootingefficiency.total": this.shootingEfficiency = node.asInt(); break;
            case "shootingefficiency.average": this.shootingEfficiencyAvg = node.asText(); break;
            case "shootingefficiency.matches": this.shootingEfficiencyMatches = node.asInt(); break;
            case "late_winning_goals.total": this.lateWinningGoals = node.asInt(); break;
            case "penalty_success_count.total": this.penaltySuccessCount = node.asInt(); break;
            case "penalty_fail_count.total": this.penaltyFailCount = node.asInt(); break;
            case "clean_sheet.total": this.cleanSheetCount = node.asInt(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "UniqueTeamStatsEntity{" + "id=" + getId() +
                ", uniqueTeamId=" + uniqueTeamId +
                ", goalAttempts=" + goalAttempts +
                ", shotsOnGoal=" + shotsOnGoal +
                ", shotsOffGoal=" + shotsOffGoal +
                ", cornerKicks=" + cornerKicks +
                ", ballPossession=" + ballPossession +
                ", shotsBlocked=" + shotsBlocked +
                ", cardsGiven=" + cardsGiven +
                ", freekicks=" + freekicks +
                ", offside=" + offside +
                ", shotsOnPost=" + shotsOnPost +
                ", shotsOnBar=" + shotsOnBar +
                ", goalsByFoot=" + goalsByFoot +
                ", goalsByHead=" + goalsByHead +
                ", attendance=" + attendance +
                ", yellowCards=" + yellowCards +
                ", redCards=" + redCards +
                ", goalsScored=" + goalsScored +
                ", goalsConceded=" + goalsConceded +
                ", yellowRedCards=" + yellowRedCards +
                ", shootingEfficiency=" + shootingEfficiency +
                ", lateWinningGoals=" + lateWinningGoals +
                ", penaltySuccessCount=" + penaltySuccessCount +
                ", penaltyFailCount=" + penaltyFailCount +
                ", cleanSheetCount=" + cleanSheetCount +
                ", goalAttemptsAvg=" + goalAttemptsAvg +
                ", shotsOnGoalAvg=" + shotsOnGoalAvg +
                ", shotsOffGoalAvg=" + shotsOffGoalAvg +
                ", cornerKicksAvg=" + cornerKicksAvg +
                ", ballPossessionAvg=" + ballPossessionAvg +
                ", shotsBlockedAvg=" + shotsBlockedAvg +
                ", cardsGivenAvg=" + cardsGivenAvg +
                ", freekicksAvg=" + freekicksAvg +
                ", offsideAvg=" + offsideAvg +
                ", shotsOnPostAvg=" + shotsOnPostAvg +
                ", shotsOnBarAvg=" + shotsOnBarAvg +
                ", goalsByFootAvg=" + goalsByFootAvg +
                ", goalsByHeadAvg=" + goalsByHeadAvg +
                ", attendanceAvg=" + attendanceAvg +
                ", yellowCardsAvg=" + yellowCardsAvg +
                ", redCardsAvg=" + redCardsAvg +
                ", goalsScoredAvg=" + goalsScoredAvg +
                ", goalsConcededAvg=" + goalsConcededAvg +
                ", yellowRedCardsAvg=" + yellowRedCardsAvg +
                ", shootingEfficiencyAvg='" + shootingEfficiencyAvg + '\'' +
                ", goalAttemptsMatches=" + goalAttemptsMatches +
                ", shotsOnGoalMatches=" + shotsOnGoalMatches +
                ", shotsOffGoalMatches=" + shotsOffGoalMatches +
                ", cornerKicksMatches=" + cornerKicksMatches +
                ", ballPossessionMatches=" + ballPossessionMatches +
                ", shotsBlockedMatches=" + shotsBlockedMatches +
                ", cardsGivenMatches=" + cardsGivenMatches +
                ", freekicksMatches=" + freekicksMatches +
                ", offsideMatches=" + offsideMatches +
                ", shotsOnPostMatches=" + shotsOnPostMatches +
                ", shotsOnBarMatches=" + shotsOnBarMatches +
                ", goalsByFootMatches=" + goalsByFootMatches +
                ", goalsByHeadMatches=" + goalsByHeadMatches +
                ", attendanceMatches=" + attendanceMatches +
                ", yellowCardsMatches=" + yellowCardsMatches +
                ", redCardsMatches=" + redCardsMatches +
                ", goalsScoredMatches=" + goalsScoredMatches +
                ", goalsConcededMatches=" + goalsConcededMatches +
                ", yellowRedCardsMatches=" + yellowRedCardsMatches +
                ", shootingEfficiencyMatches=" + shootingEfficiencyMatches +
                '}';
    }
}
