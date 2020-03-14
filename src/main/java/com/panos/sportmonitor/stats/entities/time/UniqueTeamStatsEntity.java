package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;

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

    public UniqueTeamStatsEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, id, timeStamp);
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        this.uniqueTeamId = new EntityId(auxEntityId);
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("uniqueteam")) {
            this.uniqueTeamId = childEntity.getId();
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
        final StringBuilder sb = new StringBuilder("UniqueTeamStatsEntity{");
        sb.append("id=").append(getId());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append(", goalAttempts=").append(goalAttempts);
        sb.append(", shotsOnGoal=").append(shotsOnGoal);
        sb.append(", shotsOffGoal=").append(shotsOffGoal);
        sb.append(", cornerKicks=").append(cornerKicks);
        sb.append(", ballPossession=").append(ballPossession);
        sb.append(", shotsBlocked=").append(shotsBlocked);
        sb.append(", cardsGiven=").append(cardsGiven);
        sb.append(", freekicks=").append(freekicks);
        sb.append(", offside=").append(offside);
        sb.append(", shotsOnPost=").append(shotsOnPost);
        sb.append(", shotsOnBar=").append(shotsOnBar);
        sb.append(", goalsByFoot=").append(goalsByFoot);
        sb.append(", goalsByHead=").append(goalsByHead);
        sb.append(", attendance=").append(attendance);
        sb.append(", yellowCards=").append(yellowCards);
        sb.append(", redCards=").append(redCards);
        sb.append(", goalsScored=").append(goalsScored);
        sb.append(", goalsConceded=").append(goalsConceded);
        sb.append(", yellowRedCards=").append(yellowRedCards);
        sb.append(", shootingEfficiency=").append(shootingEfficiency);
        sb.append(", lateWinningGoals=").append(lateWinningGoals);
        sb.append(", penaltySuccessCount=").append(penaltySuccessCount);
        sb.append(", penaltyFailCount=").append(penaltyFailCount);
        sb.append(", cleanSheetCount=").append(cleanSheetCount);
        sb.append(", goalAttemptsAvg=").append(goalAttemptsAvg);
        sb.append(", shotsOnGoalAvg=").append(shotsOnGoalAvg);
        sb.append(", shotsOffGoalAvg=").append(shotsOffGoalAvg);
        sb.append(", cornerKicksAvg=").append(cornerKicksAvg);
        sb.append(", ballPossessionAvg=").append(ballPossessionAvg);
        sb.append(", shotsBlockedAvg=").append(shotsBlockedAvg);
        sb.append(", cardsGivenAvg=").append(cardsGivenAvg);
        sb.append(", freekicksAvg=").append(freekicksAvg);
        sb.append(", offsideAvg=").append(offsideAvg);
        sb.append(", shotsOnPostAvg=").append(shotsOnPostAvg);
        sb.append(", shotsOnBarAvg=").append(shotsOnBarAvg);
        sb.append(", goalsByFootAvg=").append(goalsByFootAvg);
        sb.append(", goalsByHeadAvg=").append(goalsByHeadAvg);
        sb.append(", attendanceAvg=").append(attendanceAvg);
        sb.append(", yellowCardsAvg=").append(yellowCardsAvg);
        sb.append(", redCardsAvg=").append(redCardsAvg);
        sb.append(", goalsScoredAvg=").append(goalsScoredAvg);
        sb.append(", goalsConcededAvg=").append(goalsConcededAvg);
        sb.append(", yellowRedCardsAvg=").append(yellowRedCardsAvg);
        sb.append(", shootingEfficiencyAvg='").append(shootingEfficiencyAvg).append('\'');
        sb.append(", goalAttemptsMatches=").append(goalAttemptsMatches);
        sb.append(", shotsOnGoalMatches=").append(shotsOnGoalMatches);
        sb.append(", shotsOffGoalMatches=").append(shotsOffGoalMatches);
        sb.append(", cornerKicksMatches=").append(cornerKicksMatches);
        sb.append(", ballPossessionMatches=").append(ballPossessionMatches);
        sb.append(", shotsBlockedMatches=").append(shotsBlockedMatches);
        sb.append(", cardsGivenMatches=").append(cardsGivenMatches);
        sb.append(", freekicksMatches=").append(freekicksMatches);
        sb.append(", offsideMatches=").append(offsideMatches);
        sb.append(", shotsOnPostMatches=").append(shotsOnPostMatches);
        sb.append(", shotsOnBarMatches=").append(shotsOnBarMatches);
        sb.append(", goalsByFootMatches=").append(goalsByFootMatches);
        sb.append(", goalsByHeadMatches=").append(goalsByHeadMatches);
        sb.append(", attendanceMatches=").append(attendanceMatches);
        sb.append(", yellowCardsMatches=").append(yellowCardsMatches);
        sb.append(", redCardsMatches=").append(redCardsMatches);
        sb.append(", goalsScoredMatches=").append(goalsScoredMatches);
        sb.append(", goalsConcededMatches=").append(goalsConcededMatches);
        sb.append(", yellowRedCardsMatches=").append(yellowRedCardsMatches);
        sb.append(", shootingEfficiencyMatches=").append(shootingEfficiencyMatches);
        sb.append('}');
        return sb.toString();
    }
}
