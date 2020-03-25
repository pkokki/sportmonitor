package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityIdList;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.ref.*;

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
    private Boolean walkover;
    private Boolean retired;
    private Boolean disqualified;
    private Boolean dbfa;
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
    private Integer p1Home, p1Away, ftHome, ftAway, otHome, otAway, apHome, apAway;
    private Integer cupRoundMatchNumber;
    private Integer cupRoundNumberOfMatches;
    private Integer matchDifficultyRatingHome, matchDifficultyRatingAway;
    private String oddsClientMatchId;
    private EntityId oddsBookmakerId;
    private Long oddsBookmakerBetId;
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
    private Integer cardsHomeYellow, cardsHomeRed, cardsAwayYellow, cardsAwayRed;

    public MatchEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(MatchEntity.class, id));
    }

    public static EntityId createId(long id) {
        return new EntityId(MatchEntity.class, id);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "teams.home":
                this.teamHomeId = new EntityId(childEntity);
                this.teamHomeUid = ((TeamEntity) childEntity).getUid();
                break;
            case "teams.away":
                this.teamAwayId = new EntityId(childEntity);
                this.teamAwayUid = ((TeamEntity) childEntity).getUid();
                break;
            case "referee[]":
                this.referees.add(childEntity.getId());
                break;
            case "manager.home":
                this.managerHomeId = new EntityId(childEntity);
                break;
            case "manager.away":
                this.managerAwayId = new EntityId(childEntity);
                break;
            case "roundname":
                this.roundNameId = new EntityId(childEntity);
                break;
            case "tournament":
                //this.tournamentId = childEntity.getId();
                break;
            case "form[]":
                this.teamForms.add(childEntity.getId());
                break;
            case "stadium":
                this.stadiumId = new EntityId(childEntity);
                break;
            case "status":
                this.matchStatusId = new EntityId(childEntity);
                break;
            default:
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
            case "_rcid": this.realCategoryId = new EntityId(RealCategoryEntity.class, node.asLong()); break;
            case "_tid": this.tournamentId = new EntityId(TournamentEntity.class, node.asLong()); break;
            case "_utid": this.uniqueTournamentId = new EntityId(UniqueTournamentEntity.class, node.asLong()); break;
            case "_seasonid": this.seasonId = new EntityId(SeasonEntity.class, node.asLong()); break;
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
            case "nextmatchid": this.nextMatchiId = new EntityId(MatchEntity.class, node.asLong()); break;
            case "tobeannounced": this.toBeAnnounced = node.asBoolean(); break;
            case "postponed": this.postponed = node.asBoolean(); break;
            case "canceled": this.canceled = node.asBoolean(); break;
            case "inlivescore": this.inlivescore = node.asBoolean(); break;
            case "stadiumid":
            case "stadiumId":
                this.stadiumId = node.asLong() > 0 ? new EntityId(StadiumEntity.class, node.asLong()) : null; break;
            case "walkover": this.walkover = node.asBoolean(); break;
            case "retired": this.retired = node.asBoolean(); break;
            case "disqualified": this.disqualified = node.asBoolean(); break;
            case "dbfa": this.dbfa = node.asBoolean(); break;
            case "history.previous": this.historyPreviousMatchId = node.isNull() ? null : new EntityId(MatchEntity.class, node.asLong()); break;
            case "history.next": this.historyNextMatchId = node.isNull() ? null : new EntityId(MatchEntity.class, node.asLong()); break;
            case "periods.p1.home": this.p1Home = node.asInt(); break;
            case "periods.p1.away": this.p1Away = node.asInt(); break;
            case "periods.ft.home": this.ftHome = node.asInt(); break;
            case "periods.ft.away": this.ftAway = node.asInt(); break;
            case "periods.ot.home": this.otHome = node.asInt(); break;
            case "periods.ot.away": this.otAway = node.asInt(); break;
            case "periods.ap.home": this.apHome = node.asInt(); break;
            case "periods.ap.away": this.apAway = node.asInt(); break;
            case "cuproundmatchnumber": this.cupRoundMatchNumber = node.asInt(); break;
            case "cuproundnumberofmatches": this.cupRoundNumberOfMatches = node.asInt(); break;
            case "matchdifficultyrating.home": this.matchDifficultyRatingHome = node.asInt(); break;
            case "matchdifficultyrating.away": this.matchDifficultyRatingAway = node.asInt(); break;

            case "cards.home.yellow_count": this.cardsHomeYellow = node.asInt(); break;
            case "cards.home.red_count": this.cardsHomeRed = node.asInt(); break;
            case "cards.away.yellow_count": this.cardsAwayYellow = node.asInt(); break;
            case "cards.away.red_count": this.cardsAwayRed = node.asInt(); break;

            case "odds.clientmatchid": this.oddsClientMatchId = node.asText(); break;
            case "odds.bookmakerid": this.oddsBookmakerId = new EntityId(BookmakerEntity.class, node.asLong()); break;
            case "odds.bookmakerbetid": this.oddsBookmakerBetId = node.asLong(); break;
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
            case "timeinfo.ended": /* ignore */
            case "p":
            case "hf":
                break;
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
            case "decidedbyfa":
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
                EntityId teamId = new EntityId(UniqueTeamEntity.class, Long.parseLong(matcher.group(2)));
                String prevNext = matcher.group(3);
                EntityId matchId = new EntityId(MatchEntity.class, node.asLong());
                if (teamId.equals(this.teamHomeUid)) {
                    if (prevNext.equals("previous")) this.homeTeamHistoryPrevMatchId = matchId;
                    if (prevNext.equals("next")) this.homeTeamHistoryNextMatchId = matchId;
                }
                else if (teamId.equals(this.teamAwayUid)) {
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
        return "MatchEntity{" + "id=" + getId() +
                ", realCategoryId=" + realCategoryId +
                ", tournamentId=" + tournamentId +
                ", uniqueTournamentId=" + uniqueTournamentId +
                ", time=" + time +
                ", week='" + week + '\'' +
                ", round=" + round +
                ", resultHome=" + resultHome +
                ", resultAway=" + resultAway +
                ", resultPeriod='" + resultPeriod + '\'' +
                ", resultWinner='" + resultWinner + '\'' +
                ", resultBettingWinner='" + resultBettingWinner + '\'' +
                ", seasonId=" + seasonId +
                ", teamHomeId=" + teamHomeId +
                ", teamAwayId=" + teamAwayId +
                ", teamHomeUid=" + teamHomeUid +
                ", teamAwayUid=" + teamAwayUid +
                ", neutralGround=" + neutralGround +
                ", comment='" + comment + '\'' +
                ", toBeAnnounced=" + toBeAnnounced +
                ", postponed=" + postponed +
                ", canceled=" + canceled +
                ", inlivescore=" + inlivescore +
                ", walkover=" + walkover +
                ", retired=" + retired +
                ", disqualified=" + disqualified +
                ", dbfa=" + dbfa +
                ", referees=" + referees +
                ", managerHomeId=" + managerHomeId +
                ", managerAwayId=" + managerAwayId +
                ", roundNameId=" + roundNameId +
                ", stadiumId=" + stadiumId +
                ", historyPreviousMatchId=" + historyPreviousMatchId +
                ", historyNextMatchId=" + historyNextMatchId +
                ", homeTeamHistoryPrevMatchId=" + homeTeamHistoryPrevMatchId +
                ", homeTeamHistoryNextMatchId=" + homeTeamHistoryNextMatchId +
                ", awayTeamHistoryPrevMatchId=" + awayTeamHistoryPrevMatchId +
                ", awayTeamHistoryNextMatchId=" + awayTeamHistoryNextMatchId +
                ", p1Home=" + p1Home +
                ", p1Away=" + p1Away +
                ", ftHome=" + ftHome +
                ", ftAway=" + ftAway +
                ", otHome=" + otHome +
                ", otAway=" + otAway +
                ", apHome=" + apHome +
                ", apAway=" + apAway +
                ", cupRoundMatchNumber=" + cupRoundMatchNumber +
                ", cupRoundNumberOfMatches=" + cupRoundNumberOfMatches +
                ", matchDifficultyRatingHome=" + matchDifficultyRatingHome +
                ", matchDifficultyRatingAway=" + matchDifficultyRatingAway +
                ", oddsClientMatchId='" + oddsClientMatchId + '\'' +
                ", oddsBookmakerId=" + oddsBookmakerId +
                ", oddsBookmakerBetId=" + oddsBookmakerBetId +
                ", oddsType='" + oddsType + '\'' +
                ", oddsTypeShort='" + oddsTypeShort + '\'' +
                ", oddsTypeId='" + oddsTypeId + '\'' +
                ", oddsLivebet=" + oddsLivebet +
                ", oddsIsMatchOdds=" + oddsIsMatchOdds +
                ", oddsExtra='" + oddsExtra + '\'' +
                ", oddsActive=" + oddsActive +
                ", oddsBetstop=" + oddsBetstop +
                ", oddsUpdated=" + oddsUpdated +
                ", status='" + status + '\'' +
                ", nextMatchiId=" + nextMatchiId +
                ", teamForms=" + teamForms +
                ", coverageLineup=" + coverageLineup +
                ", coverageFormations=" + coverageFormations +
                ", coverageLiveTable=" + coverageLiveTable +
                ", coverageInjuries=" + coverageInjuries +
                ", coverageBallSpotting=" + coverageBallSpotting +
                ", coverageCornersOnly=" + coverageCornersOnly +
                ", coverageMultiCast=" + coverageMultiCast +
                ", coverageScoutMatch=" + coverageScoutMatch +
                ", coverageScoutCoverageStatus=" + coverageScoutCoverageStatus +
                ", coverageScoutConnected=" + coverageScoutConnected +
                ", coverageLiveOdds=" + coverageLiveOdds +
                ", coverageDeeperCoverage=" + coverageDeeperCoverage +
                ", coverageTacticalLineup=" + coverageTacticalLineup +
                ", coverageBasicLineup=" + coverageBasicLineup +
                ", coverageHasStats=" + coverageHasStats +
                ", coverageInLiveScore=" + coverageInLiveScore +
                ", coveragePenaltyShootout=" + coveragePenaltyShootout +
                ", coverageScoutTest=" + coverageScoutTest +
                ", coverageLmtSupport=" + coverageLmtSupport +
                ", coverageVenue=" + coverageVenue +
                ", coverageMatchDataComplete=" + coverageMatchDataComplete +
                ", coverageMediaCoverage=" + coverageMediaCoverage +
                ", coverageSubstitutions=" + coverageSubstitutions +
                ", updatedTime=" + updatedTime +
                ", endedTime=" + endedTime +
                ", pTime=" + pTime +
                ", timeInfoRunning=" + timeInfoRunning +
                ", removed=" + removed +
                ", facts=" + facts +
                ", localDerby=" + localDerby +
                ", distance=" + distance +
                ", weather=" + weather +
                ", pitchCondition=" + pitchCondition +
                ", windAdvantage=" + windAdvantage +
                ", matchStatus='" + matchStatus + '\'' +
                ", matchStatusId=" + matchStatusId +
                ", cancelled=" + cancelled +
                ", cardsHomeYellow=" + cardsHomeYellow +
                ", cardsHomeRed=" + cardsHomeRed +
                ", cardsAwayYellow=" + cardsAwayYellow +
                ", cardsAwayRed=" + cardsAwayRed +
                '}';
    }
}
