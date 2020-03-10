package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchEntity extends BaseEntity {
    private long realCategoryId;
    private long tournamentId;
    private long uniqueTournamentId;
    private long time;
    private String week;
    private int round;
    private Integer resultHome, resultAway;
    private String resultPeriod;
    private String resultWinner;
    private long seasonId;
    private long teamHomeId, teamAwayId;
    private long teamHomeUid, teamAwayUid;
    private boolean neutralGround;
    private String comment;
    private boolean toBeAnnounced;
    private boolean postponed;
    private boolean canceled;
    private boolean inlivescore;
    private long stadiumid;
    private boolean walkover;
    private boolean retired;
    private boolean disqualified;
    private List<Long> referees = new ArrayList<>();
    private long managerHomeId;
    private long managerAwayId;
    private long roundNameId;
    private long stadiumId;
    private Long historyPreviousMatchId;
    private Long historyNextMatchId;
    private Long homeTeamHistoryPrevMatchId;
    private Long homeTeamHistoryNextMatchId;
    private Long awayTeamHistoryPrevMatchId;
    private Long awayTeamHistoryNextMatchId;
    private int p1Home, p1Away, ftHome, ftAway;
    private int cupRoundMatchNumber;
    private int cupRoundNumberOfMatches;
    private int matchDifficultyRatingHome, matchDifficultyRatingAway;
    private String oddsClientMatchId;
    private long oddsBookmakerId;
    private long oddsBookmakerBetId;
    private String oddsType;
    private String oddsTypeShort;
    private String oddsTypeId;
    private boolean oddsLivebet;
    private boolean oddsIsMatchOdds;
    private String oddsExtra;
    private boolean oddsActive;
    private boolean oddsBetstop;
    private long oddsUpdated;
    private List<Long> teamForms = new ArrayList<>();

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
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_rcid": this.realCategoryId = node.asLong(); break;
            case "_tid": this.tournamentId = node.asLong(); break;
            case "_utid": this.uniqueTournamentId = node.asLong(); break;
            case "_seasonid": this.seasonId = node.asLong(); break;
            case "time.uts": this.time = node.asLong(); break;
            case "week": this.week = node.asText(); break;
            case "round": this.round = node.asInt(); break;
            case "result.home": this.resultHome = node.isNull() ? null : node.asInt(); break;
            case "result.away": this.resultAway = node.isNull() ? null : node.asInt(); break;
            case "result.period": this.resultPeriod = node.asText(); break;
            case "result.winner": this.resultWinner = node.asText(); break;
            case "neutralground": this.neutralGround = node.asBoolean(); break;
            case "comment": this.comment = node.asText(); break;
            case "tobeannounced": this.toBeAnnounced = node.asBoolean(); break;
            case "postponed": this.postponed = node.asBoolean(); break;
            case "canceled": this.canceled = node.asBoolean(); break;
            case "inlivescore": this.inlivescore = node.asBoolean(); break;
            case "stadiumid": this.stadiumid = node.asLong(); break;
            case "walkover": this.walkover = node.asBoolean(); break;
            case "retired": this.retired = node.asBoolean(); break;
            case "disqualified": this.disqualified = node.asBoolean(); break;
            case "history.previous": this.historyPreviousMatchId = node.isNull() ? null : node.asLong(); break;
            case "history.next": this.historyNextMatchId = node.isNull() ? null :node.asLong(); break;
            case "periods.p1.home": this.p1Home = node.asInt(); break;
            case "periods.p1.away": this.p1Away = node.asInt(); break;
            case "periods.ft.home": this.ftHome = node.asInt(); break;
            case "periods.ft.away": this.ftAway = node.asInt(); break;
            case "cuproundmatchnumber": this.cupRoundMatchNumber = node.asInt(); break;
            case "cuproundnumberofmatches": this.cupRoundNumberOfMatches = node.asInt(); break;
            case "matchdifficultyrating.home": this.matchDifficultyRatingHome = node.asInt(); break;
            case "matchdifficultyrating.away": this.matchDifficultyRatingAway = node.asInt(); break;

            case "odds.clientmatchid": this.oddsClientMatchId = node.asText(); break;
            case "odds.bookmakerid": this.oddsBookmakerId = node.asLong(); break;
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

            case "periods":
            case "status":
            case "bestof":
                if (!node.isNull()) return false;

            case "time._doc":
            case "time.time":
            case "time.date":
            case "time.tz":
            case "time.tzoffset":
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


    private boolean testRegex(String nodeName, JsonNode node) {
        Pattern regEx = Pattern.compile("(\\w+).(\\d+).(\\w+)");
        Matcher matcher = regEx.matcher(nodeName);
        if (matcher.find()) {
            if (!node.isNull() && matcher.group(1).equals("teamhistory")) {
                long teamId = Long.parseLong(matcher.group(2));
                String prevNext = matcher.group(3);
                long matchId = node.asLong();
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
        sb.append(", teamForms=").append(teamForms);
        sb.append('}');
        return sb.toString();
    }
}
