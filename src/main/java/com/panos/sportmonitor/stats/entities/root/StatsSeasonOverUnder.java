package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.entities.BaseEntity;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;

import java.util.HashMap;

public class StatsSeasonOverUnder extends RootEntity {
    private long seasonId;
    private transient HashMap<Long, BaseEntity> teams = new HashMap<>();

    private Integer statsTotalsMatches;
    private Double statsTotalsGoalsscoredFtAverage;
    private Integer statsTotalsGoalsscoredFtTotal;
    private Integer statsTotalsGoalsscoredFtMatches;
    private Double statsTotalsGoalsscoredP1Average;
    private Integer statsTotalsGoalsscoredP1Total;
    private Integer statsTotalsGoalsscoredP1Matches;
    private Double statsTotalsGoalsscoredP2Average;
    private Integer statsTotalsGoalsscoredP2Total;
    private Integer statsTotalsGoalsscoredP2Matches;
    private Double statsTotalsConcededFtAverage;
    private Integer statsTotalsConcededFtTotal;
    private Integer statsTotalsConcededFtMatches;
    private Double statsTotalsConcededP1Average;
    private Integer statsTotalsConcededP1Total;
    private Integer statsTotalsConcededP1Matches;
    private Double statsTotalsConcededP2Average;
    private Integer statsTotalsConcededP2Total;
    private Integer statsTotalsConcededP2Matches;
    private Integer statsTotalsFt05Totalover;
    private Integer statsTotalsFt05Over;
    private Integer statsTotalsFt05Under;
    private Integer statsTotalsFt15Totalover;
    private Integer statsTotalsFt15Over;
    private Integer statsTotalsFt15Under;
    private Integer statsTotalsFt25Totalover;
    private Integer statsTotalsFt25Over;
    private Integer statsTotalsFt25Under;
    private Integer statsTotalsFt35Totalover;
    private Integer statsTotalsFt35Over;
    private Integer statsTotalsFt35Under;
    private Integer statsTotalsFt45Totalover;
    private Integer statsTotalsFt45Over;
    private Integer statsTotalsFt45Under;
    private Integer statsTotalsFt55Totalover;
    private Integer statsTotalsFt55Over;
    private Integer statsTotalsFt55Under;
    private Integer statsTotalsP105Totalover;
    private Integer statsTotalsP105Over;
    private Integer statsTotalsP105Under;
    private Integer statsTotalsP115Totalover;
    private Integer statsTotalsP115Over;
    private Integer statsTotalsP115Under;
    private Integer statsTotalsP125Totalover;
    private Integer statsTotalsP125Over;
    private Integer statsTotalsP125Under;
    private Integer statsTotalsP135Totalover;
    private Integer statsTotalsP135Over;
    private Integer statsTotalsP135Under;
    private Integer statsTotalsP145Totalover;
    private Integer statsTotalsP145Over;
    private Integer statsTotalsP145Under;
    private Integer statsTotalsP155Totalover;
    private Integer statsTotalsP155Over;
    private Integer statsTotalsP155Under;
    private Integer statsTotalsP205Totalover;
    private Integer statsTotalsP205Over;
    private Integer statsTotalsP205Under;
    private Integer statsTotalsP215Totalover;
    private Integer statsTotalsP215Over;
    private Integer statsTotalsP215Under;
    private Integer statsTotalsP225Totalover;
    private Integer statsTotalsP225Over;
    private Integer statsTotalsP225Under;
    private Integer statsTotalsP235Totalover;
    private Integer statsTotalsP235Over;
    private Integer statsTotalsP235Under;
    private Integer statsTotalsP245Totalover;
    private Integer statsTotalsP245Over;
    private Integer statsTotalsP245Under;
    private Integer statsTotalsP255Totalover;
    private Integer statsTotalsP255Over;
    private Integer statsTotalsP255Under;

    public StatsSeasonOverUnder(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); return true;
            default:
                if (isTeamStats(entityName)) {
                    long teamId = extractTeamStatsId(entityName);
                    teams.put(teamId, childEntity);
                    return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.startsWith("stats.")) {
            long teamId = extractTeamStatsId(nodeName);
            String propName = nodeName.replace("." + teamId, "");
            return teams.get(teamId).handleRemoteProperty(propName, nodeType, node);
        }
        else if (nodeName.startsWith("league.totals")) {
            switch(nodeName) {
                case "league.totals.matches": this.statsTotalsMatches = node.asInt(); break;
                case "league.totals.goalsscored.ft.average": this.statsTotalsGoalsscoredFtAverage = node.asDouble(); break;
                case "league.totals.goalsscored.ft.total": this.statsTotalsGoalsscoredFtTotal = node.asInt(); break;
                case "league.totals.goalsscored.ft.matches": this.statsTotalsGoalsscoredFtMatches = node.asInt(); break;
                case "league.totals.goalsscored.p1.average": this.statsTotalsGoalsscoredP1Average = node.asDouble(); break;
                case "league.totals.goalsscored.p1.total": this.statsTotalsGoalsscoredP1Total = node.asInt(); break;
                case "league.totals.goalsscored.p1.matches": this.statsTotalsGoalsscoredP1Matches = node.asInt(); break;
                case "league.totals.goalsscored.p2.average": this.statsTotalsGoalsscoredP2Average = node.asDouble(); break;
                case "league.totals.goalsscored.p2.total": this.statsTotalsGoalsscoredP2Total = node.asInt(); break;
                case "league.totals.goalsscored.p2.matches": this.statsTotalsGoalsscoredP2Matches = node.asInt(); break;
                case "league.totals.conceded.ft.average": this.statsTotalsConcededFtAverage = node.asDouble(); break;
                case "league.totals.conceded.ft.total": this.statsTotalsConcededFtTotal = node.asInt(); break;
                case "league.totals.conceded.ft.matches": this.statsTotalsConcededFtMatches = node.asInt(); break;
                case "league.totals.conceded.p1.average": this.statsTotalsConcededP1Average = node.asDouble(); break;
                case "league.totals.conceded.p1.total": this.statsTotalsConcededP1Total = node.asInt(); break;
                case "league.totals.conceded.p1.matches": this.statsTotalsConcededP1Matches = node.asInt(); break;
                case "league.totals.conceded.p2.average": this.statsTotalsConcededP2Average = node.asDouble(); break;
                case "league.totals.conceded.p2.total": this.statsTotalsConcededP2Total = node.asInt(); break;
                case "league.totals.conceded.p2.matches": this.statsTotalsConcededP2Matches = node.asInt(); break;
                case "league.totals.ft.0.5.totalover": this.statsTotalsFt05Totalover = node.asInt(); break;
                case "league.totals.ft.0.5.over": this.statsTotalsFt05Over = node.asInt(); break;
                case "league.totals.ft.0.5.under": this.statsTotalsFt05Under = node.asInt(); break;
                case "league.totals.ft.1.5.totalover": this.statsTotalsFt15Totalover = node.asInt(); break;
                case "league.totals.ft.1.5.over": this.statsTotalsFt15Over = node.asInt(); break;
                case "league.totals.ft.1.5.under": this.statsTotalsFt15Under = node.asInt(); break;
                case "league.totals.ft.2.5.totalover": this.statsTotalsFt25Totalover = node.asInt(); break;
                case "league.totals.ft.2.5.over": this.statsTotalsFt25Over = node.asInt(); break;
                case "league.totals.ft.2.5.under": this.statsTotalsFt25Under = node.asInt(); break;
                case "league.totals.ft.3.5.totalover": this.statsTotalsFt35Totalover = node.asInt(); break;
                case "league.totals.ft.3.5.over": this.statsTotalsFt35Over = node.asInt(); break;
                case "league.totals.ft.3.5.under": this.statsTotalsFt35Under = node.asInt(); break;
                case "league.totals.ft.4.5.totalover": this.statsTotalsFt45Totalover = node.asInt(); break;
                case "league.totals.ft.4.5.over": this.statsTotalsFt45Over = node.asInt(); break;
                case "league.totals.ft.4.5.under": this.statsTotalsFt45Under = node.asInt(); break;
                case "league.totals.ft.5.5.totalover": this.statsTotalsFt55Totalover = node.asInt(); break;
                case "league.totals.ft.5.5.over": this.statsTotalsFt55Over = node.asInt(); break;
                case "league.totals.ft.5.5.under": this.statsTotalsFt55Under = node.asInt(); break;
                case "league.totals.p1.0.5.totalover": this.statsTotalsP105Totalover = node.asInt(); break;
                case "league.totals.p1.0.5.over": this.statsTotalsP105Over = node.asInt(); break;
                case "league.totals.p1.0.5.under": this.statsTotalsP105Under = node.asInt(); break;
                case "league.totals.p1.1.5.totalover": this.statsTotalsP115Totalover = node.asInt(); break;
                case "league.totals.p1.1.5.over": this.statsTotalsP115Over = node.asInt(); break;
                case "league.totals.p1.1.5.under": this.statsTotalsP115Under = node.asInt(); break;
                case "league.totals.p1.2.5.totalover": this.statsTotalsP125Totalover = node.asInt(); break;
                case "league.totals.p1.2.5.over": this.statsTotalsP125Over = node.asInt(); break;
                case "league.totals.p1.2.5.under": this.statsTotalsP125Under = node.asInt(); break;
                case "league.totals.p1.3.5.totalover": this.statsTotalsP135Totalover = node.asInt(); break;
                case "league.totals.p1.3.5.over": this.statsTotalsP135Over = node.asInt(); break;
                case "league.totals.p1.3.5.under": this.statsTotalsP135Under = node.asInt(); break;
                case "league.totals.p1.4.5.totalover": this.statsTotalsP145Totalover = node.asInt(); break;
                case "league.totals.p1.4.5.over": this.statsTotalsP145Over = node.asInt(); break;
                case "league.totals.p1.4.5.under": this.statsTotalsP145Under = node.asInt(); break;
                case "league.totals.p1.5.5.totalover": this.statsTotalsP155Totalover = node.asInt(); break;
                case "league.totals.p1.5.5.over": this.statsTotalsP155Over = node.asInt(); break;
                case "league.totals.p1.5.5.under": this.statsTotalsP155Under = node.asInt(); break;
                case "league.totals.p2.0.5.totalover": this.statsTotalsP205Totalover = node.asInt(); break;
                case "league.totals.p2.0.5.over": this.statsTotalsP205Over = node.asInt(); break;
                case "league.totals.p2.0.5.under": this.statsTotalsP205Under = node.asInt(); break;
                case "league.totals.p2.1.5.totalover": this.statsTotalsP215Totalover = node.asInt(); break;
                case "league.totals.p2.1.5.over": this.statsTotalsP215Over = node.asInt(); break;
                case "league.totals.p2.1.5.under": this.statsTotalsP215Under = node.asInt(); break;
                case "league.totals.p2.2.5.totalover": this.statsTotalsP225Totalover = node.asInt(); break;
                case "league.totals.p2.2.5.over": this.statsTotalsP225Over = node.asInt(); break;
                case "league.totals.p2.2.5.under": this.statsTotalsP225Under = node.asInt(); break;
                case "league.totals.p2.3.5.totalover": this.statsTotalsP235Totalover = node.asInt(); break;
                case "league.totals.p2.3.5.over": this.statsTotalsP235Over = node.asInt(); break;
                case "league.totals.p2.3.5.under": this.statsTotalsP235Under = node.asInt(); break;
                case "league.totals.p2.4.5.totalover": this.statsTotalsP245Totalover = node.asInt(); break;
                case "league.totals.p2.4.5.over": this.statsTotalsP245Over = node.asInt(); break;
                case "league.totals.p2.4.5.under": this.statsTotalsP245Under = node.asInt(); break;
                case "league.totals.p2.5.5.totalover": this.statsTotalsP255Totalover = node.asInt(); break;
                case "league.totals.p2.5.5.over": this.statsTotalsP255Over = node.asInt(); break;
                case "league.totals.p2.5.5.under": this.statsTotalsP255Under = node.asInt(); break;
                default: return false;
            }
            return true;
        }
        else if (nodeName.startsWith("values.")) {
            return true;
        }
        return super.handleProperty(nodeName, nodeType, node);
    }

    private boolean isTeamStats(String name) {
        return name.startsWith("stats.") && name.endsWith(".team");
    }
    private long extractTeamStatsId(String name) {
        return Long.parseLong(name.split("\\.")[1]);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonOverUnder{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append(", statsTotalsMatches=").append(statsTotalsMatches);
        sb.append(", statsTotalsGoalsscoredFtAverage=").append(statsTotalsGoalsscoredFtAverage);
        sb.append(", statsTotalsGoalsscoredFtTotal=").append(statsTotalsGoalsscoredFtTotal);
        sb.append(", statsTotalsGoalsscoredFtMatches=").append(statsTotalsGoalsscoredFtMatches);
        sb.append(", statsTotalsGoalsscoredP1Average=").append(statsTotalsGoalsscoredP1Average);
        sb.append(", statsTotalsGoalsscoredP1Total=").append(statsTotalsGoalsscoredP1Total);
        sb.append(", statsTotalsGoalsscoredP1Matches=").append(statsTotalsGoalsscoredP1Matches);
        sb.append(", statsTotalsGoalsscoredP2Average=").append(statsTotalsGoalsscoredP2Average);
        sb.append(", statsTotalsGoalsscoredP2Total=").append(statsTotalsGoalsscoredP2Total);
        sb.append(", statsTotalsGoalsscoredP2Matches=").append(statsTotalsGoalsscoredP2Matches);
        sb.append(", statsTotalsConcededFtAverage=").append(statsTotalsConcededFtAverage);
        sb.append(", statsTotalsConcededFtTotal=").append(statsTotalsConcededFtTotal);
        sb.append(", statsTotalsConcededFtMatches=").append(statsTotalsConcededFtMatches);
        sb.append(", statsTotalsConcededP1Average=").append(statsTotalsConcededP1Average);
        sb.append(", statsTotalsConcededP1Total=").append(statsTotalsConcededP1Total);
        sb.append(", statsTotalsConcededP1Matches=").append(statsTotalsConcededP1Matches);
        sb.append(", statsTotalsConcededP2Average=").append(statsTotalsConcededP2Average);
        sb.append(", statsTotalsConcededP2Total=").append(statsTotalsConcededP2Total);
        sb.append(", statsTotalsConcededP2Matches=").append(statsTotalsConcededP2Matches);
        sb.append(", statsTotalsFt05Totalover=").append(statsTotalsFt05Totalover);
        sb.append(", statsTotalsFt05Over=").append(statsTotalsFt05Over);
        sb.append(", statsTotalsFt05Under=").append(statsTotalsFt05Under);
        sb.append(", statsTotalsFt15Totalover=").append(statsTotalsFt15Totalover);
        sb.append(", statsTotalsFt15Over=").append(statsTotalsFt15Over);
        sb.append(", statsTotalsFt15Under=").append(statsTotalsFt15Under);
        sb.append(", statsTotalsFt25Totalover=").append(statsTotalsFt25Totalover);
        sb.append(", statsTotalsFt25Over=").append(statsTotalsFt25Over);
        sb.append(", statsTotalsFt25Under=").append(statsTotalsFt25Under);
        sb.append(", statsTotalsFt35Totalover=").append(statsTotalsFt35Totalover);
        sb.append(", statsTotalsFt35Over=").append(statsTotalsFt35Over);
        sb.append(", statsTotalsFt35Under=").append(statsTotalsFt35Under);
        sb.append(", statsTotalsFt45Totalover=").append(statsTotalsFt45Totalover);
        sb.append(", statsTotalsFt45Over=").append(statsTotalsFt45Over);
        sb.append(", statsTotalsFt45Under=").append(statsTotalsFt45Under);
        sb.append(", statsTotalsFt55Totalover=").append(statsTotalsFt55Totalover);
        sb.append(", statsTotalsFt55Over=").append(statsTotalsFt55Over);
        sb.append(", statsTotalsFt55Under=").append(statsTotalsFt55Under);
        sb.append(", statsTotalsP105Totalover=").append(statsTotalsP105Totalover);
        sb.append(", statsTotalsP105Over=").append(statsTotalsP105Over);
        sb.append(", statsTotalsP105Under=").append(statsTotalsP105Under);
        sb.append(", statsTotalsP115Totalover=").append(statsTotalsP115Totalover);
        sb.append(", statsTotalsP115Over=").append(statsTotalsP115Over);
        sb.append(", statsTotalsP115Under=").append(statsTotalsP115Under);
        sb.append(", statsTotalsP125Totalover=").append(statsTotalsP125Totalover);
        sb.append(", statsTotalsP125Over=").append(statsTotalsP125Over);
        sb.append(", statsTotalsP125Under=").append(statsTotalsP125Under);
        sb.append(", statsTotalsP135Totalover=").append(statsTotalsP135Totalover);
        sb.append(", statsTotalsP135Over=").append(statsTotalsP135Over);
        sb.append(", statsTotalsP135Under=").append(statsTotalsP135Under);
        sb.append(", statsTotalsP145Totalover=").append(statsTotalsP145Totalover);
        sb.append(", statsTotalsP145Over=").append(statsTotalsP145Over);
        sb.append(", statsTotalsP145Under=").append(statsTotalsP145Under);
        sb.append(", statsTotalsP155Totalover=").append(statsTotalsP155Totalover);
        sb.append(", statsTotalsP155Over=").append(statsTotalsP155Over);
        sb.append(", statsTotalsP155Under=").append(statsTotalsP155Under);
        sb.append(", statsTotalsP205Totalover=").append(statsTotalsP205Totalover);
        sb.append(", statsTotalsP205Over=").append(statsTotalsP205Over);
        sb.append(", statsTotalsP205Under=").append(statsTotalsP205Under);
        sb.append(", statsTotalsP215Totalover=").append(statsTotalsP215Totalover);
        sb.append(", statsTotalsP215Over=").append(statsTotalsP215Over);
        sb.append(", statsTotalsP215Under=").append(statsTotalsP215Under);
        sb.append(", statsTotalsP225Totalover=").append(statsTotalsP225Totalover);
        sb.append(", statsTotalsP225Over=").append(statsTotalsP225Over);
        sb.append(", statsTotalsP225Under=").append(statsTotalsP225Under);
        sb.append(", statsTotalsP235Totalover=").append(statsTotalsP235Totalover);
        sb.append(", statsTotalsP235Over=").append(statsTotalsP235Over);
        sb.append(", statsTotalsP235Under=").append(statsTotalsP235Under);
        sb.append(", statsTotalsP245Totalover=").append(statsTotalsP245Totalover);
        sb.append(", statsTotalsP245Over=").append(statsTotalsP245Over);
        sb.append(", statsTotalsP245Under=").append(statsTotalsP245Under);
        sb.append(", statsTotalsP255Totalover=").append(statsTotalsP255Totalover);
        sb.append(", statsTotalsP255Over=").append(statsTotalsP255Over);
        sb.append(", statsTotalsP255Under=").append(statsTotalsP255Under);
        sb.append('}');
        return sb.toString();
    }
}
