package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityIdList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchEntity extends BaseEntity {
    private EntityId realCategoryId;
    private EntityId tournamentId;
    private EntityId uniqueTournamentId;
    private Long time;
    private String week;
    private Integer round;
    private Integer resultHome, resultAway;
    private String resultPeriod;
    private String resultWinner, resultBettingWinner;
    private EntityId seasonId;
    private EntityId teamHomeId, teamAwayId;
    private EntityId teamHomeUid, teamAwayUid;
    private Boolean neutralGround;
    private String comment;
    private Boolean toBeAnnounced;
    private Boolean postponed;
    private Boolean canceled;
    private Boolean inlivescore;
    private EntityId stadiumid;
    private Boolean walkover;
    private Boolean retired;
    private Boolean disqualified;
    private EntityIdList referees = new EntityIdList();
    private EntityId managerHomeId;
    private EntityId managerAwayId;
    private EntityId roundNameId;
    private EntityId stadiumId;
    private EntityId historyPreviousMatchId;
    private EntityId historyNextMatchId;
    private EntityId homeTeamHistoryPrevMatchId;
    private EntityId homeTeamHistoryNextMatchId;
    private EntityId awayTeamHistoryPrevMatchId;
    private EntityId awayTeamHistoryNextMatchId;
    private Integer p1Home, p1Away, ftHome, ftAway, otHome, otAway;
    private Integer cupRoundMatchNumber;
    private Integer cupRoundNumberOfMatches;
    private Integer matchDifficultyRatingHome, matchDifficultyRatingAway;
    private String oddsClientMatchId;
    private EntityId oddsBookmakerId;
    private EntityId oddsBookmakerBetId;
    private String oddsType;
    private String oddsTypeShort;
    private String oddsTypeId;
    private Boolean oddsLivebet;
    private Boolean oddsIsMatchOdds;
    private String oddsExtra;
    private Boolean oddsActive;
    private Boolean oddsBetstop;
    private Long oddsUpdated;
    private String status;
    private EntityId nextMatchiId;
    private EntityIdList teamForms = new EntityIdList();
    private  Integer  coverageLineup ;
    private  Integer  coverageFormations ;
    private  Long  coverageLiveTable ;
    private  Integer  coverageInjuries ;
    private  Boolean  coverageBallSpotting ;
    private  Boolean  coverageCornersOnly ;
    private  Boolean  coverageMultiCast ;
    private  Integer  coverageScoutMatch ;
    private  Integer  coverageScoutCoverageStatus ;
    private  Boolean  coverageScoutConnected ;
    private  Boolean  coverageLiveOdds ;
    private  Boolean  coverageDeeperCoverage ;
    private  Boolean  coverageTacticalLineup ;
    private  Boolean  coverageBasicLineup ;
    private  Boolean  coverageHasStats ;
    private  Boolean  coverageInLiveScore ;
    private  Integer  coveragePenaltyShootout ;
    private  Boolean  coverageScoutTest ;
    private  Integer  coverageLmtSupport ;
    private  Boolean  coverageVenue ;
    private  Boolean  coverageMatchDataComplete ;
    private  Boolean  coverageMediaCoverage ;
    private  Boolean  coverageSubstitutions ;
    private  Long  updatedTime ;
    private  Long  endedTime ;
    private  Long  pTime ;
    private  Boolean  timeInfoRunning ;
    private  Boolean  removed ;
    private  Boolean  facts ;
    private  Boolean  localDerby ;
    private  Integer  distance ;
    private  Integer  weather ;
    private  Integer  pitchCondition ;
    private  Integer  windAdvantage ;
    private String  matchStatus ;
    private EntityId matchStatusId;
    private  Boolean  cancelled ;

    public MatchEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("teams.home")) {
            this.teamHomeId = childEntity.getId();
            this.teamHomeUid = ((TeamEntity)childEntity).getUid();
        }
        else if (entityName.equals("teams.away")) {
            this.teamAwayId = childEntity.getId();
            this.teamAwayUid = ((TeamEntity)childEntity).getUid();
        }
        else if (entityName.equals("referee[]")) {
            this.referees.add(childEntity.getId());
        }
        else if (entityName.equals("manager.home")) {
            this.managerHomeId = childEntity.getId();
        }
        else if (entityName.equals("manager.away")) {
            this.managerAwayId = childEntity.getId();
        }
        else if (entityName.equals("roundname")) {
            this.roundNameId = childEntity.getId();
        }
        else if (entityName.equals("tournament")) {
            //this.tournamentId = childEntity.getId();
        }
        else if (entityName.equals("form[]")) {
            this.teamForms.add(childEntity.getId());
        }
        else if (entityName.equals("stadium")) {
            this.stadiumId = childEntity.getId();
        }
        else if (entityName.equals("status")) {
            this.matchStatusId = childEntity.getId();
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("form[]")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_id", this.getRoot().getNext());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_rcid": this.realCategoryId = new EntityId(node.asLong()); break;
            case "_tid": this.tournamentId = new EntityId(node.asLong()); break;
            case "_utid": this.uniqueTournamentId = new EntityId(node.asLong()); break;
            case "_seasonid": this.seasonId = new EntityId(node.asLong()); break;
            case "time.uts":
            case "_dt.uts":
                this.time = node.asLong(); break;
            case "week": this.week = node.asText(); break;
            case "round": this.round = node.asInt(); break;
            case "result.home": this.resultHome = node.isNull() ? null : node.asInt(); break;
            case "result.away": this.resultAway = node.isNull() ? null : node.asInt(); break;
            case "result.period": this.resultPeriod = node.asText(); break;
            case "result.winner": this.resultWinner = node.asText(); break;
            case "result.bettingWinner": this.resultBettingWinner = node.asText(); break;
            case "neutralground": this.neutralGround = node.asBoolean(); break;
            case "comment": this.comment = node.asText(); break;
            case "status": this.status = node.asText(); break;
            case "nextmatchid": this.nextMatchiId = new EntityId(node.asLong()); break;
            case "tobeannounced": this.toBeAnnounced = node.asBoolean(); break;
            case "postponed": this.postponed = node.asBoolean(); break;
            case "canceled": this.canceled = node.asBoolean(); break;
            case "inlivescore": this.inlivescore = node.asBoolean(); break;
            case "stadiumid": this.stadiumid = node.asLong() > 0 ? new EntityId(node.asLong()) : null; break;
            case "walkover": this.walkover = node.asBoolean(); break;
            case "retired": this.retired = node.asBoolean(); break;
            case "disqualified": this.disqualified = node.asBoolean(); break;
            case "history.previous": this.historyPreviousMatchId = node.isNull() ? null : new EntityId(node.asLong()); break;
            case "history.next": this.historyNextMatchId = node.isNull() ? null : new EntityId(node.asLong()); break;
            case "periods.p1.home": this.p1Home = node.asInt(); break;
            case "periods.p1.away": this.p1Away = node.asInt(); break;
            case "periods.ft.home": this.ftHome = node.asInt(); break;
            case "periods.ft.away": this.ftAway = node.asInt(); break;
            case "periods.ot.home": this.otHome = node.asInt(); break;
            case "periods.ot.away": this.otAway = node.asInt(); break;
            case "cuproundmatchnumber": this.cupRoundMatchNumber = node.asInt(); break;
            case "cuproundnumberofmatches": this.cupRoundNumberOfMatches = node.asInt(); break;
            case "matchdifficultyrating.home": this.matchDifficultyRatingHome = node.asInt(); break;
            case "matchdifficultyrating.away": this.matchDifficultyRatingAway = node.asInt(); break;

            case "odds.clientmatchid": this.oddsClientMatchId = node.asText(); break;
            case "odds.bookmakerid": this.oddsBookmakerId = new EntityId(node.asLong()); break;
            case "odds.bookmakerbetid": this.oddsBookmakerBetId = new EntityId(node.asLong()); break;
            case "odds.oddstype": this.oddsType = node.asText(); break;
            case "odds.oddstypeshort": this.oddsTypeShort = node.asText(); break;
            case "odds.oddstypeid": this.oddsTypeId = node.asText(); break;
            case "odds.livebet": this.oddsLivebet = node.asBoolean(); break;
            case "odds.ismatchodds": this.oddsIsMatchOdds = node.asBoolean(); break;
            case "odds.extra": this.oddsExtra = node.asText(); break;
            case "odds.active": this.oddsActive = node.asBoolean(); break;
            case "odds.betstop": this.oddsBetstop = node.asBoolean(); break;
            case "odds.updated_uts": this.oddsUpdated = node.asLong(); break;

            // MatchTimeline
            case "coverage.lineup": this.coverageLineup = node.asInt(); break;
            case "coverage.formations": this.coverageFormations = node.asInt(); break;
            case "coverage.livetable": this.coverageLiveTable = node.asLong(); break;
            case "coverage.injuries": this.coverageInjuries = node.asInt(); break;
            case "coverage.ballspotting": this.coverageBallSpotting = node.asBoolean(); break;
            case "coverage.cornersonly": this.coverageCornersOnly = node.asBoolean(); break;
            case "coverage.multicast": this.coverageMultiCast = node.asBoolean(); break;
            case "coverage.scoutmatch": this.coverageScoutMatch = node.asInt(); break;
            case "coverage.scoutcoveragestatus": this.coverageScoutCoverageStatus = node.asInt(); break;
            case "coverage.scoutconnected": this.coverageScoutConnected = node.asBoolean(); break;
            case "coverage.liveodds": this.coverageLiveOdds = node.asBoolean(); break;
            case "coverage.deepercoverage": this.coverageDeeperCoverage = node.asBoolean(); break;
            case "coverage.tacticallineup": this.coverageTacticalLineup = node.asBoolean(); break;
            case "coverage.basiclineup": this.coverageBasicLineup = node.asBoolean(); break;
            case "coverage.hasstats": this.coverageHasStats = node.asBoolean(); break;
            case "coverage.inlivescore": this.coverageInLiveScore = node.asBoolean(); break;
            case "coverage.penaltyshootout": this.coveragePenaltyShootout = node.asInt(); break;
            case "coverage.scouttest": this.coverageScoutTest = node.asBoolean(); break;
            case "coverage.lmtsupport": this.coverageLmtSupport = node.asInt(); break;
            case "coverage.venue": this.coverageVenue = node.asBoolean(); break;
            case "coverage.matchdatacomplete": this.coverageMatchDataComplete = node.asBoolean(); break;
            case "coverage.mediacoverage": this.coverageMediaCoverage = node.asBoolean(); break;
            case "coverage.substitutions": this.coverageSubstitutions = node.asBoolean(); break;
            case "updated_uts": this.updatedTime = node.asLong(); break;
            case "ended_uts": this.endedTime = node.asLong(); break;
            case "ptime": this.pTime = node.asLong(); break;
            case "timeinfo.ended": /* ignore */ break;
            case "timeinfo.running": this.timeInfoRunning = node.asBoolean(); break;
            case "removed": this.removed = node.asBoolean(); break;
            case "facts": this.facts = node.asBoolean(); break;
            case "localderby": this.localDerby = node.asBoolean(); break;
            case "distance": this.distance = node.asInt(); break;
            case "weather": this.weather = node.asInt(); break;
            case "pitchcondition": this.pitchCondition = node.asInt(); break;
            case "windadvantage": this.windAdvantage = node.asInt(); break;
            case "matchstatus": this.matchStatus = node.asText(); break;
            case "cancelled": this.cancelled = node.asBoolean(); break;
            case "hf": /* ignore */ break;
            case "p": /* ignore */ break;

            case "bestof":
                if (!node.isNull()) return false;

            case "time._doc":
            case "time.time":
            case "time.date":
            case "time.tz":
            case "time.tzoffset":
            case "_dt._doc":
            case "_dt.time":
            case "_dt.date":
            case "_dt.tz":
            case "_dt.tzoffset":
            case "periodlength":
            case "numberofperiods":
            case "overtimelength":
            case "odds._doc":
            case "odds.matchid":
                break;
            default:
                if (testRegex(nodeName, node))
                    break;
                if (!nodeName.startsWith("jerseys."))
                    return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }


    private Boolean testRegex(String nodeName, JsonNode node) {
        Pattern regEx = Pattern.compile("(\\w+).(\\d+).(\\w+)");
        Matcher matcher = regEx.matcher(nodeName);
        if (matcher.find()) {
            if (!node.isNull() && matcher.group(1).equals("teamhistory")) {
                EntityId teamId = new EntityId(Long.parseLong(matcher.group(2)));
                String prevNext = matcher.group(3);
                EntityId matchId = new EntityId(node.asLong());
                if (teamId == this.teamHomeUid) {
                    if (prevNext.equals("previous")) this.homeTeamHistoryPrevMatchId = matchId;
                    if (prevNext.equals("next")) this.homeTeamHistoryNextMatchId = matchId;
                }
                else if (teamId == this.teamAwayUid) {
                    if (prevNext.equals("previous")) this.awayTeamHistoryPrevMatchId = matchId;
                    if (prevNext.equals("next")) this.awayTeamHistoryNextMatchId = matchId;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchEntity{");
        sb.append("id=").append(getId());
        sb.append(", realCategoryId=").append(realCategoryId);
        sb.append(", tournamentId=").append(tournamentId);
        sb.append(", uniqueTournamentId=").append(uniqueTournamentId);
        sb.append(", time=").append(time);
        sb.append(", week='").append(week).append('\'');
        sb.append(", round=").append(round);
        sb.append(", resultHome=").append(resultHome);
        sb.append(", resultAway=").append(resultAway);
        sb.append(", resultPeriod='").append(resultPeriod).append('\'');
        sb.append(", resultWinner='").append(resultWinner).append('\'');
        sb.append(", resultBettingWinner='").append(resultBettingWinner).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append(", teamHomeId=").append(teamHomeId);
        sb.append(", teamAwayId=").append(teamAwayId);
        sb.append(", teamHomeUid=").append(teamHomeUid);
        sb.append(", teamAwayUid=").append(teamAwayUid);
        sb.append(", neutralGround=").append(neutralGround);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", toBeAnnounced=").append(toBeAnnounced);
        sb.append(", postponed=").append(postponed);
        sb.append(", canceled=").append(canceled);
        sb.append(", inlivescore=").append(inlivescore);
        sb.append(", stadiumid=").append(stadiumid);
        sb.append(", walkover=").append(walkover);
        sb.append(", retired=").append(retired);
        sb.append(", disqualified=").append(disqualified);
        sb.append(", referees=").append(referees);
        sb.append(", managerHomeId=").append(managerHomeId);
        sb.append(", managerAwayId=").append(managerAwayId);
        sb.append(", roundNameId=").append(roundNameId);
        sb.append(", stadiumId=").append(stadiumId);
        sb.append(", historyPreviousMatchId=").append(historyPreviousMatchId);
        sb.append(", historyNextMatchId=").append(historyNextMatchId);
        sb.append(", homeTeamHistoryPrevMatchId=").append(homeTeamHistoryPrevMatchId);
        sb.append(", homeTeamHistoryNextMatchId=").append(homeTeamHistoryNextMatchId);
        sb.append(", awayTeamHistoryPrevMatchId=").append(awayTeamHistoryPrevMatchId);
        sb.append(", awayTeamHistoryNextMatchId=").append(awayTeamHistoryNextMatchId);
        sb.append(", p1Home=").append(p1Home);
        sb.append(", p1Away=").append(p1Away);
        sb.append(", ftHome=").append(ftHome);
        sb.append(", ftAway=").append(ftAway);
        sb.append(", otHome=").append(otHome);
        sb.append(", otAway=").append(otAway);
        sb.append(", cupRoundMatchNumber=").append(cupRoundMatchNumber);
        sb.append(", cupRoundNumberOfMatches=").append(cupRoundNumberOfMatches);
        sb.append(", matchDifficultyRatingHome=").append(matchDifficultyRatingHome);
        sb.append(", matchDifficultyRatingAway=").append(matchDifficultyRatingAway);
        sb.append(", oddsClientMatchId='").append(oddsClientMatchId).append('\'');
        sb.append(", oddsBookmakerId=").append(oddsBookmakerId);
        sb.append(", oddsBookmakerBetId=").append(oddsBookmakerBetId);
        sb.append(", oddsType='").append(oddsType).append('\'');
        sb.append(", oddsTypeShort='").append(oddsTypeShort).append('\'');
        sb.append(", oddsTypeId='").append(oddsTypeId).append('\'');
        sb.append(", oddsLivebet=").append(oddsLivebet);
        sb.append(", oddsIsMatchOdds=").append(oddsIsMatchOdds);
        sb.append(", oddsExtra='").append(oddsExtra).append('\'');
        sb.append(", oddsActive=").append(oddsActive);
        sb.append(", oddsBetstop=").append(oddsBetstop);
        sb.append(", oddsUpdated=").append(oddsUpdated);
        sb.append(", status='").append(status).append('\'');
        sb.append(", nextMatchiId=").append(nextMatchiId);
        sb.append(", teamForms=").append(teamForms);
        sb.append(", coverageLineup=").append(coverageLineup);
        sb.append(", coverageFormations=").append(coverageFormations);
        sb.append(", coverageLiveTable=").append(coverageLiveTable);
        sb.append(", coverageInjuries=").append(coverageInjuries);
        sb.append(", coverageBallSpotting=").append(coverageBallSpotting);
        sb.append(", coverageCornersOnly=").append(coverageCornersOnly);
        sb.append(", coverageMultiCast=").append(coverageMultiCast);
        sb.append(", coverageScoutMatch=").append(coverageScoutMatch);
        sb.append(", coverageScoutCoverageStatus=").append(coverageScoutCoverageStatus);
        sb.append(", coverageScoutConnected=").append(coverageScoutConnected);
        sb.append(", coverageLiveOdds=").append(coverageLiveOdds);
        sb.append(", coverageDeeperCoverage=").append(coverageDeeperCoverage);
        sb.append(", coverageTacticalLineup=").append(coverageTacticalLineup);
        sb.append(", coverageBasicLineup=").append(coverageBasicLineup);
        sb.append(", coverageHasStats=").append(coverageHasStats);
        sb.append(", coverageInLiveScore=").append(coverageInLiveScore);
        sb.append(", coveragePenaltyShootout=").append(coveragePenaltyShootout);
        sb.append(", coverageScoutTest=").append(coverageScoutTest);
        sb.append(", coverageLmtSupport=").append(coverageLmtSupport);
        sb.append(", coverageVenue=").append(coverageVenue);
        sb.append(", coverageMatchDataComplete=").append(coverageMatchDataComplete);
        sb.append(", coverageMediaCoverage=").append(coverageMediaCoverage);
        sb.append(", coverageSubstitutions=").append(coverageSubstitutions);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", endedTime=").append(endedTime);
        sb.append(", pTime=").append(pTime);
        sb.append(", timeInfoRunning=").append(timeInfoRunning);
        sb.append(", removed=").append(removed);
        sb.append(", facts=").append(facts);
        sb.append(", localDerby=").append(localDerby);
        sb.append(", distance=").append(distance);
        sb.append(", weather=").append(weather);
        sb.append(", pitchCondition=").append(pitchCondition);
        sb.append(", windAdvantage=").append(windAdvantage);
        sb.append(", matchStatus='").append(matchStatus).append('\'');
        sb.append(", matchStatusId=").append(matchStatusId);
        sb.append(", cancelled=").append(cancelled);
        sb.append('}');
        return sb.toString();
    }
}
