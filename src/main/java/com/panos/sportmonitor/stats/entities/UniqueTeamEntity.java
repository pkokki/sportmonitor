package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.ref.RealCategoryEntity;

public class UniqueTeamEntity extends BaseEntity {
    private String name;
    private String suffix;
    private String abbr;
    private String nickname;
    private String mediumName;
    private Boolean isCountry;
    private String founded;
    private String website;
    private String sex;
    private EntityId realCategoryId;
    private Long teamTypeId;
    private EntityId countryCodeId;
    private EntityId stadiumId;
    private EntityId homeRealCategoryId;

    private Integer statsMatches;
    private Integer statsHomeMatches;
    private Integer statsAwayMatches;
    private Integer statsScoredFTTotal;
    private Double statsScoredFTAverage;
    private Integer statsScoredFTAtleastonegoal;
    private Integer statsScoredFTMatches;
    private Integer statsScoredFThomeTotal;
    private Double statsScoredFThomeAverage;
    private Integer statsScoredFThomeAtleastonegoal;
    private Integer statsScoredFThomeMatches;
    private Integer statsScoredFTawayTotal;
    private Double statsScoredFTawayAverage;
    private Integer statsScoredFTawayAtleastonegoal;
    private Integer statsScoredFTawayMatches;
    private Integer statsScoredP1Total;
    private Double statsScoredP1Average;
    private Integer statsScoredP1Atleastonegoal;
    private Integer statsScoredP1Matches;
    private Integer statsScoredP1homeTotal;
    private Double statsScoredP1homeAverage;
    private Integer statsScoredP1homeAtleastonegoal;
    private Integer statsScoredP1homeMatches;
    private Integer statsScoredP1awayTotal;
    private Double statsScoredP1awayAverage;
    private Integer statsScoredP1awayAtleastonegoal;
    private Integer statsScoredP1awayMatches;
    private Integer statsScoredP2Total;
    private Double statsScoredP2Average;
    private Integer statsScoredP2Atleastonegoal;
    private Integer statsScoredP2Matches;
    private Integer statsScoredP2homeTotal;
    private Double statsScoredP2homeAverage;
    private Integer statsScoredP2homeAtleastonegoal;
    private Integer statsScoredP2homeMatches;
    private Integer statsScoredP2awayTotal;
    private Double statsScoredP2awayAverage;
    private Integer statsScoredP2awayAtleastonegoal;
    private Integer statsScoredP2awayMatches;
    private Integer statsConcededFtTotal;
    private Double statsConcededFtAverage;
    private Integer statsConcededFtCleansheets;
    private Integer statsConcededFtMatches;
    private Integer statsConcededFthomeTotal;
    private Double statsConcededFthomeAverage;
    private Integer statsConcededFthomeCleansheets;
    private Integer statsConcededFthomeMatches;
    private Integer statsConcededFtawayTotal;
    private Double statsConcededFtawayAverage;
    private Integer statsConcededFtawayCleansheets;
    private Integer statsConcededFtawayMatches;
    private Integer statsConcededP1Total;
    private Double statsConcededP1Average;
    private Integer statsConcededP1Cleansheets;
    private Integer statsConcededP1Matches;
    private Integer statsConcededP1homeTotal;
    private Double statsConcededP1homeAverage;
    private Integer statsConcededP1homeCleansheets;
    private Integer statsConcededP1homeMatches;
    private Integer statsConcededP1awayTotal;
    private Double statsConcededP1awayAverage;
    private Integer statsConcededP1awayCleansheets;
    private Integer statsConcededP1awayMatches;
    private Integer statsConcededP2Total;
    private Double statsConcededP2Average;
    private Integer statsConcededP2Cleansheets;
    private Integer statsConcededP2Matches;
    private Integer statsConcededP2homeTotal;
    private Double statsConcededP2homeAverage;
    private Integer statsConcededP2homeCleansheets;
    private Integer statsConcededP2homeMatches;
    private Integer statsConcededP2awayTotal;
    private Double statsConcededP2awayAverage;
    private Integer statsConcededP2awayCleansheets;
    private Integer statsConcededP2awayMatches;
    private Integer statsTotalFt05Over;
    private Integer statsTotalFt05Under;
    private Integer statsTotalFt15Over;
    private Integer statsTotalFt15Under;
    private Integer statsTotalFt25Over;
    private Integer statsTotalFt25Under;
    private Integer statsTotalFt35Over;
    private Integer statsTotalFt35Under;
    private Integer statsTotalFt45Over;
    private Integer statsTotalFt45Under;
    private Integer statsTotalFt55Over;
    private Integer statsTotalFt55Under;
    private Integer statsTotalP105Over;
    private Integer statsTotalP105Under;
    private Integer statsTotalP115Over;
    private Integer statsTotalP115Under;
    private Integer statsTotalP125Over;
    private Integer statsTotalP125Under;
    private Integer statsTotalP135Over;
    private Integer statsTotalP135Under;
    private Integer statsTotalP145Over;
    private Integer statsTotalP145Under;
    private Integer statsTotalP155Over;
    private Integer statsTotalP155Under;
    private Integer statsTotalP205Over;
    private Integer statsTotalP205Under;
    private Integer statsTotalP215Over;
    private Integer statsTotalP215Under;
    private Integer statsTotalP225Over;
    private Integer statsTotalP225Under;
    private Integer statsTotalP235Over;
    private Integer statsTotalP235Under;
    private Integer statsTotalP245Over;
    private Integer statsTotalP245Under;
    private Integer statsTotalP255Over;
    private Integer statsTotalP255Under;
    private Integer statsHomeFt05Over;
    private Integer statsHomeFt05Under;
    private Integer statsHomeFt15Over;
    private Integer statsHomeFt15Under;
    private Integer statsHomeFt25Over;
    private Integer statsHomeFt25Under;
    private Integer statsHomeFt35Over;
    private Integer statsHomeFt35Under;
    private Integer statsHomeFt45Over;
    private Integer statsHomeFt45Under;
    private Integer statsHomeFt55Over;
    private Integer statsHomeFt55Under;
    private Integer statsHomeP105Over;
    private Integer statsHomeP105Under;
    private Integer statsHomeP115Over;
    private Integer statsHomeP115Under;
    private Integer statsHomeP125Over;
    private Integer statsHomeP125Under;
    private Integer statsHomeP135Over;
    private Integer statsHomeP135Under;
    private Integer statsHomeP145Over;
    private Integer statsHomeP145Under;
    private Integer statsHomeP155Over;
    private Integer statsHomeP155Under;
    private Integer statsHomeP205Over;
    private Integer statsHomeP205Under;
    private Integer statsHomeP215Over;
    private Integer statsHomeP215Under;
    private Integer statsHomeP225Over;
    private Integer statsHomeP225Under;
    private Integer statsHomeP235Over;
    private Integer statsHomeP235Under;
    private Integer statsHomeP245Over;
    private Integer statsHomeP245Under;
    private Integer statsHomeP255Over;
    private Integer statsHomeP255Under;
    private Integer statsAwayFt05Over;
    private Integer statsAwayFt05Under;
    private Integer statsAwayFt15Over;
    private Integer statsAwayFt15Under;
    private Integer statsAwayFt25Over;
    private Integer statsAwayFt25Under;
    private Integer statsAwayFt35Over;
    private Integer statsAwayFt35Under;
    private Integer statsAwayFt45Over;
    private Integer statsAwayFt45Under;
    private Integer statsAwayFt55Over;
    private Integer statsAwayFt55Under;
    private Integer statsAwayP105Over;
    private Integer statsAwayP105Under;
    private Integer statsAwayP115Over;
    private Integer statsAwayP115Under;
    private Integer statsAwayP125Over;
    private Integer statsAwayP125Under;
    private Integer statsAwayP135Over;
    private Integer statsAwayP135Under;
    private Integer statsAwayP145Over;
    private Integer statsAwayP145Under;
    private Integer statsAwayP155Over;
    private Integer statsAwayP155Under;
    private Integer statsAwayP205Over;
    private Integer statsAwayP205Under;
    private Integer statsAwayP215Over;
    private Integer statsAwayP215Under;
    private Integer statsAwayP225Over;
    private Integer statsAwayP225Under;
    private Integer statsAwayP235Over;
    private Integer statsAwayP235Under;
    private Integer statsAwayP245Over;
    private Integer statsAwayP245Under;
    private Integer statsAwayP255Over;
    private Integer statsAwayP255Under;


    public UniqueTeamEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, UniqueTeamEntity.class));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "countrycode": this.countryCodeId = new EntityId(childEntity); return true;
            case "stadium": this.stadiumId = new EntityId(childEntity); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_rcid": this.realCategoryId = new EntityId(node.asLong(), RealCategoryEntity.class); break;
            case "homerealcategoryid": this.homeRealCategoryId = new EntityId(node.asLong(), RealCategoryEntity.class); break;
            case "teamtypeid": this.teamTypeId = node.asLong(); break;
            case "name": this.name = node.asText(); break;
            case "suffix": this.suffix = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "nickname": this.nickname = node.asText(); break;
            case "mediumname": this.mediumName = node.asText(); break;
            case "iscountry": this.isCountry = node.asBoolean(); break;
            case "sex": this.sex = node.asText(); break;
            case "website": this.website = node.asText(); break;
            case "founded": this.founded = node.asText(); break;

            case "haslogo":
            case "virtual":
                break;
            default:
                if (nodeName.startsWith("stats.")) return handleStats(nodeName, nodeType, node);
                if (nodeName.startsWith("homejersey.")) return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    private Boolean handleStats(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "stats.matches": this.statsMatches = node.asInt(); break;
            case "stats.homematches": this.statsHomeMatches = node.asInt(); break;
            case "stats.awaymatches": this.statsAwayMatches = node.asInt(); break;
            case "stats.goalsscored.ft.total": this.statsScoredFTTotal = node.asInt(); break;
            case "stats.goalsscored.ft.average": this.statsScoredFTAverage = node.asDouble(); break;
            case "stats.goalsscored.ft.atleastonegoal": this.statsScoredFTAtleastonegoal = node.asInt(); break;
            case "stats.goalsscored.ft.matches": this.statsScoredFTMatches = node.asInt(); break;
            case "stats.goalsscored.fthome.total": this.statsScoredFThomeTotal = node.asInt(); break;
            case "stats.goalsscored.fthome.average": this.statsScoredFThomeAverage = node.asDouble(); break;
            case "stats.goalsscored.fthome.atleastonegoal": this.statsScoredFThomeAtleastonegoal = node.asInt(); break;
            case "stats.goalsscored.fthome.matches": this.statsScoredFThomeMatches = node.asInt(); break;
            case "stats.goalsscored.ftaway.total": this.statsScoredFTawayTotal = node.asInt(); break;
            case "stats.goalsscored.ftaway.average": this.statsScoredFTawayAverage = node.asDouble(); break;
            case "stats.goalsscored.ftaway.atleastonegoal": this.statsScoredFTawayAtleastonegoal = node.asInt(); break;
            case "stats.goalsscored.ftaway.matches": this.statsScoredFTawayMatches = node.asInt(); break;
            case "stats.goalsscored.p1.total": this.statsScoredP1Total = node.asInt(); break;
            case "stats.goalsscored.p1.average": this.statsScoredP1Average = node.asDouble(); break;
            case "stats.goalsscored.p1.atleastonegoal": this.statsScoredP1Atleastonegoal = node.asInt(); break;
            case "stats.goalsscored.p1.matches": this.statsScoredP1Matches = node.asInt(); break;
            case "stats.goalsscored.p1home.total": this.statsScoredP1homeTotal = node.asInt(); break;
            case "stats.goalsscored.p1home.average": this.statsScoredP1homeAverage = node.asDouble(); break;
            case "stats.goalsscored.p1home.atleastonegoal": this.statsScoredP1homeAtleastonegoal = node.asInt(); break;
            case "stats.goalsscored.p1home.matches": this.statsScoredP1homeMatches = node.asInt(); break;
            case "stats.goalsscored.p1away.total": this.statsScoredP1awayTotal = node.asInt(); break;
            case "stats.goalsscored.p1away.average": this.statsScoredP1awayAverage = node.asDouble(); break;
            case "stats.goalsscored.p1away.atleastonegoal": this.statsScoredP1awayAtleastonegoal = node.asInt(); break;
            case "stats.goalsscored.p1away.matches": this.statsScoredP1awayMatches = node.asInt(); break;
            case "stats.goalsscored.p2.total": this.statsScoredP2Total = node.asInt(); break;
            case "stats.goalsscored.p2.average": this.statsScoredP2Average = node.asDouble(); break;
            case "stats.goalsscored.p2.atleastonegoal": this.statsScoredP2Atleastonegoal = node.asInt(); break;
            case "stats.goalsscored.p2.matches": this.statsScoredP2Matches = node.asInt(); break;
            case "stats.goalsscored.p2home.total": this.statsScoredP2homeTotal = node.asInt(); break;
            case "stats.goalsscored.p2home.average": this.statsScoredP2homeAverage = node.asDouble(); break;
            case "stats.goalsscored.p2home.atleastonegoal": this.statsScoredP2homeAtleastonegoal = node.asInt(); break;
            case "stats.goalsscored.p2home.matches": this.statsScoredP2homeMatches = node.asInt(); break;
            case "stats.goalsscored.p2away.total": this.statsScoredP2awayTotal = node.asInt(); break;
            case "stats.goalsscored.p2away.average": this.statsScoredP2awayAverage = node.asDouble(); break;
            case "stats.goalsscored.p2away.atleastonegoal": this.statsScoredP2awayAtleastonegoal = node.asInt(); break;
            case "stats.goalsscored.p2away.matches": this.statsScoredP2awayMatches = node.asInt(); break;
            case "stats.conceded.ft.total": this.statsConcededFtTotal = node.asInt(); break;
            case "stats.conceded.ft.average": this.statsConcededFtAverage = node.asDouble(); break;
            case "stats.conceded.ft.cleansheets": this.statsConcededFtCleansheets = node.asInt(); break;
            case "stats.conceded.ft.matches": this.statsConcededFtMatches = node.asInt(); break;
            case "stats.conceded.fthome.total": this.statsConcededFthomeTotal = node.asInt(); break;
            case "stats.conceded.fthome.average": this.statsConcededFthomeAverage = node.asDouble(); break;
            case "stats.conceded.fthome.cleansheets": this.statsConcededFthomeCleansheets = node.asInt(); break;
            case "stats.conceded.fthome.matches": this.statsConcededFthomeMatches = node.asInt(); break;
            case "stats.conceded.ftaway.total": this.statsConcededFtawayTotal = node.asInt(); break;
            case "stats.conceded.ftaway.average": this.statsConcededFtawayAverage = node.asDouble(); break;
            case "stats.conceded.ftaway.cleansheets": this.statsConcededFtawayCleansheets = node.asInt(); break;
            case "stats.conceded.ftaway.matches": this.statsConcededFtawayMatches = node.asInt(); break;
            case "stats.conceded.p1.total": this.statsConcededP1Total = node.asInt(); break;
            case "stats.conceded.p1.average": this.statsConcededP1Average = node.asDouble(); break;
            case "stats.conceded.p1.cleansheets": this.statsConcededP1Cleansheets = node.asInt(); break;
            case "stats.conceded.p1.matches": this.statsConcededP1Matches = node.asInt(); break;
            case "stats.conceded.p1home.total": this.statsConcededP1homeTotal = node.asInt(); break;
            case "stats.conceded.p1home.average": this.statsConcededP1homeAverage = node.asDouble(); break;
            case "stats.conceded.p1home.cleansheets": this.statsConcededP1homeCleansheets = node.asInt(); break;
            case "stats.conceded.p1home.matches": this.statsConcededP1homeMatches = node.asInt(); break;
            case "stats.conceded.p1away.total": this.statsConcededP1awayTotal = node.asInt(); break;
            case "stats.conceded.p1away.average": this.statsConcededP1awayAverage = node.asDouble(); break;
            case "stats.conceded.p1away.cleansheets": this.statsConcededP1awayCleansheets = node.asInt(); break;
            case "stats.conceded.p1away.matches": this.statsConcededP1awayMatches = node.asInt(); break;
            case "stats.conceded.p2.total": this.statsConcededP2Total = node.asInt(); break;
            case "stats.conceded.p2.average": this.statsConcededP2Average = node.asDouble(); break;
            case "stats.conceded.p2.cleansheets": this.statsConcededP2Cleansheets = node.asInt(); break;
            case "stats.conceded.p2.matches": this.statsConcededP2Matches = node.asInt(); break;
            case "stats.conceded.p2home.total": this.statsConcededP2homeTotal = node.asInt(); break;
            case "stats.conceded.p2home.average": this.statsConcededP2homeAverage = node.asDouble(); break;
            case "stats.conceded.p2home.cleansheets": this.statsConcededP2homeCleansheets = node.asInt(); break;
            case "stats.conceded.p2home.matches": this.statsConcededP2homeMatches = node.asInt(); break;
            case "stats.conceded.p2away.total": this.statsConcededP2awayTotal = node.asInt(); break;
            case "stats.conceded.p2away.average": this.statsConcededP2awayAverage = node.asDouble(); break;
            case "stats.conceded.p2away.cleansheets": this.statsConcededP2awayCleansheets = node.asInt(); break;
            case "stats.conceded.p2away.matches": this.statsConcededP2awayMatches = node.asInt(); break;
            case "stats.total.ft.0.5.over": this.statsTotalFt05Over = node.asInt(); break;
            case "stats.total.ft.0.5.under": this.statsTotalFt05Under = node.asInt(); break;
            case "stats.total.ft.1.5.over": this.statsTotalFt15Over = node.asInt(); break;
            case "stats.total.ft.1.5.under": this.statsTotalFt15Under = node.asInt(); break;
            case "stats.total.ft.2.5.over": this.statsTotalFt25Over = node.asInt(); break;
            case "stats.total.ft.2.5.under": this.statsTotalFt25Under = node.asInt(); break;
            case "stats.total.ft.3.5.over": this.statsTotalFt35Over = node.asInt(); break;
            case "stats.total.ft.3.5.under": this.statsTotalFt35Under = node.asInt(); break;
            case "stats.total.ft.4.5.over": this.statsTotalFt45Over = node.asInt(); break;
            case "stats.total.ft.4.5.under": this.statsTotalFt45Under = node.asInt(); break;
            case "stats.total.ft.5.5.over": this.statsTotalFt55Over = node.asInt(); break;
            case "stats.total.ft.5.5.under": this.statsTotalFt55Under = node.asInt(); break;
            case "stats.total.p1.0.5.over": this.statsTotalP105Over = node.asInt(); break;
            case "stats.total.p1.0.5.under": this.statsTotalP105Under = node.asInt(); break;
            case "stats.total.p1.1.5.over": this.statsTotalP115Over = node.asInt(); break;
            case "stats.total.p1.1.5.under": this.statsTotalP115Under = node.asInt(); break;
            case "stats.total.p1.2.5.over": this.statsTotalP125Over = node.asInt(); break;
            case "stats.total.p1.2.5.under": this.statsTotalP125Under = node.asInt(); break;
            case "stats.total.p1.3.5.over": this.statsTotalP135Over = node.asInt(); break;
            case "stats.total.p1.3.5.under": this.statsTotalP135Under = node.asInt(); break;
            case "stats.total.p1.4.5.over": this.statsTotalP145Over = node.asInt(); break;
            case "stats.total.p1.4.5.under": this.statsTotalP145Under = node.asInt(); break;
            case "stats.total.p1.5.5.over": this.statsTotalP155Over = node.asInt(); break;
            case "stats.total.p1.5.5.under": this.statsTotalP155Under = node.asInt(); break;
            case "stats.total.p2.0.5.over": this.statsTotalP205Over = node.asInt(); break;
            case "stats.total.p2.0.5.under": this.statsTotalP205Under = node.asInt(); break;
            case "stats.total.p2.1.5.over": this.statsTotalP215Over = node.asInt(); break;
            case "stats.total.p2.1.5.under": this.statsTotalP215Under = node.asInt(); break;
            case "stats.total.p2.2.5.over": this.statsTotalP225Over = node.asInt(); break;
            case "stats.total.p2.2.5.under": this.statsTotalP225Under = node.asInt(); break;
            case "stats.total.p2.3.5.over": this.statsTotalP235Over = node.asInt(); break;
            case "stats.total.p2.3.5.under": this.statsTotalP235Under = node.asInt(); break;
            case "stats.total.p2.4.5.over": this.statsTotalP245Over = node.asInt(); break;
            case "stats.total.p2.4.5.under": this.statsTotalP245Under = node.asInt(); break;
            case "stats.total.p2.5.5.over": this.statsTotalP255Over = node.asInt(); break;
            case "stats.total.p2.5.5.under": this.statsTotalP255Under = node.asInt(); break;
            case "stats.home.ft.0.5.over": this.statsHomeFt05Over = node.asInt(); break;
            case "stats.home.ft.0.5.under": this.statsHomeFt05Under = node.asInt(); break;
            case "stats.home.ft.1.5.over": this.statsHomeFt15Over = node.asInt(); break;
            case "stats.home.ft.1.5.under": this.statsHomeFt15Under = node.asInt(); break;
            case "stats.home.ft.2.5.over": this.statsHomeFt25Over = node.asInt(); break;
            case "stats.home.ft.2.5.under": this.statsHomeFt25Under = node.asInt(); break;
            case "stats.home.ft.3.5.over": this.statsHomeFt35Over = node.asInt(); break;
            case "stats.home.ft.3.5.under": this.statsHomeFt35Under = node.asInt(); break;
            case "stats.home.ft.4.5.over": this.statsHomeFt45Over = node.asInt(); break;
            case "stats.home.ft.4.5.under": this.statsHomeFt45Under = node.asInt(); break;
            case "stats.home.ft.5.5.over": this.statsHomeFt55Over = node.asInt(); break;
            case "stats.home.ft.5.5.under": this.statsHomeFt55Under = node.asInt(); break;
            case "stats.home.p1.0.5.over": this.statsHomeP105Over = node.asInt(); break;
            case "stats.home.p1.0.5.under": this.statsHomeP105Under = node.asInt(); break;
            case "stats.home.p1.1.5.over": this.statsHomeP115Over = node.asInt(); break;
            case "stats.home.p1.1.5.under": this.statsHomeP115Under = node.asInt(); break;
            case "stats.home.p1.2.5.over": this.statsHomeP125Over = node.asInt(); break;
            case "stats.home.p1.2.5.under": this.statsHomeP125Under = node.asInt(); break;
            case "stats.home.p1.3.5.over": this.statsHomeP135Over = node.asInt(); break;
            case "stats.home.p1.3.5.under": this.statsHomeP135Under = node.asInt(); break;
            case "stats.home.p1.4.5.over": this.statsHomeP145Over = node.asInt(); break;
            case "stats.home.p1.4.5.under": this.statsHomeP145Under = node.asInt(); break;
            case "stats.home.p1.5.5.over": this.statsHomeP155Over = node.asInt(); break;
            case "stats.home.p1.5.5.under": this.statsHomeP155Under = node.asInt(); break;
            case "stats.home.p2.0.5.over": this.statsHomeP205Over = node.asInt(); break;
            case "stats.home.p2.0.5.under": this.statsHomeP205Under = node.asInt(); break;
            case "stats.home.p2.1.5.over": this.statsHomeP215Over = node.asInt(); break;
            case "stats.home.p2.1.5.under": this.statsHomeP215Under = node.asInt(); break;
            case "stats.home.p2.2.5.over": this.statsHomeP225Over = node.asInt(); break;
            case "stats.home.p2.2.5.under": this.statsHomeP225Under = node.asInt(); break;
            case "stats.home.p2.3.5.over": this.statsHomeP235Over = node.asInt(); break;
            case "stats.home.p2.3.5.under": this.statsHomeP235Under = node.asInt(); break;
            case "stats.home.p2.4.5.over": this.statsHomeP245Over = node.asInt(); break;
            case "stats.home.p2.4.5.under": this.statsHomeP245Under = node.asInt(); break;
            case "stats.home.p2.5.5.over": this.statsHomeP255Over = node.asInt(); break;
            case "stats.home.p2.5.5.under": this.statsHomeP255Under = node.asInt(); break;
            case "stats.away.ft.0.5.over": this.statsAwayFt05Over = node.asInt(); break;
            case "stats.away.ft.0.5.under": this.statsAwayFt05Under = node.asInt(); break;
            case "stats.away.ft.1.5.over": this.statsAwayFt15Over = node.asInt(); break;
            case "stats.away.ft.1.5.under": this.statsAwayFt15Under = node.asInt(); break;
            case "stats.away.ft.2.5.over": this.statsAwayFt25Over = node.asInt(); break;
            case "stats.away.ft.2.5.under": this.statsAwayFt25Under = node.asInt(); break;
            case "stats.away.ft.3.5.over": this.statsAwayFt35Over = node.asInt(); break;
            case "stats.away.ft.3.5.under": this.statsAwayFt35Under = node.asInt(); break;
            case "stats.away.ft.4.5.over": this.statsAwayFt45Over = node.asInt(); break;
            case "stats.away.ft.4.5.under": this.statsAwayFt45Under = node.asInt(); break;
            case "stats.away.ft.5.5.over": this.statsAwayFt55Over = node.asInt(); break;
            case "stats.away.ft.5.5.under": this.statsAwayFt55Under = node.asInt(); break;
            case "stats.away.p1.0.5.over": this.statsAwayP105Over = node.asInt(); break;
            case "stats.away.p1.0.5.under": this.statsAwayP105Under = node.asInt(); break;
            case "stats.away.p1.1.5.over": this.statsAwayP115Over = node.asInt(); break;
            case "stats.away.p1.1.5.under": this.statsAwayP115Under = node.asInt(); break;
            case "stats.away.p1.2.5.over": this.statsAwayP125Over = node.asInt(); break;
            case "stats.away.p1.2.5.under": this.statsAwayP125Under = node.asInt(); break;
            case "stats.away.p1.3.5.over": this.statsAwayP135Over = node.asInt(); break;
            case "stats.away.p1.3.5.under": this.statsAwayP135Under = node.asInt(); break;
            case "stats.away.p1.4.5.over": this.statsAwayP145Over = node.asInt(); break;
            case "stats.away.p1.4.5.under": this.statsAwayP145Under = node.asInt(); break;
            case "stats.away.p1.5.5.over": this.statsAwayP155Over = node.asInt(); break;
            case "stats.away.p1.5.5.under": this.statsAwayP155Under = node.asInt(); break;
            case "stats.away.p2.0.5.over": this.statsAwayP205Over = node.asInt(); break;
            case "stats.away.p2.0.5.under": this.statsAwayP205Under = node.asInt(); break;
            case "stats.away.p2.1.5.over": this.statsAwayP215Over = node.asInt(); break;
            case "stats.away.p2.1.5.under": this.statsAwayP215Under = node.asInt(); break;
            case "stats.away.p2.2.5.over": this.statsAwayP225Over = node.asInt(); break;
            case "stats.away.p2.2.5.under": this.statsAwayP225Under = node.asInt(); break;
            case "stats.away.p2.3.5.over": this.statsAwayP235Over = node.asInt(); break;
            case "stats.away.p2.3.5.under": this.statsAwayP235Under = node.asInt(); break;
            case "stats.away.p2.4.5.over": this.statsAwayP245Over = node.asInt(); break;
            case "stats.away.p2.4.5.under": this.statsAwayP245Under = node.asInt(); break;
            case "stats.away.p2.5.5.over": this.statsAwayP255Over = node.asInt(); break;
            case "stats.away.p2.5.5.under": this.statsAwayP255Under = node.asInt(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }


    @Override
    public String toString() {
        return "UniqueTeamEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", suffix='" + suffix + '\'' +
                ", abbr='" + abbr + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mediumName='" + mediumName + '\'' +
                ", isCountry=" + isCountry +
                ", founded='" + founded + '\'' +
                ", website='" + website + '\'' +
                ", sex='" + sex + '\'' +
                ", realCategoryId=" + realCategoryId +
                ", teamTypeId=" + teamTypeId +
                ", countryCodeId=" + countryCodeId +
                ", stadiumId=" + stadiumId +
                ", homeRealCategoryId=" + homeRealCategoryId +
                ", statsMatches=" + statsMatches +
                ", statsHomeMatches=" + statsHomeMatches +
                ", statsAwayMatches=" + statsAwayMatches +
                ", statsScoredFTTotal=" + statsScoredFTTotal +
                ", statsScoredFTAverage=" + statsScoredFTAverage +
                ", statsScoredFTAtleastonegoal=" + statsScoredFTAtleastonegoal +
                ", statsScoredFTMatches=" + statsScoredFTMatches +
                ", statsScoredFThomeTotal=" + statsScoredFThomeTotal +
                ", statsScoredFThomeAverage=" + statsScoredFThomeAverage +
                ", statsScoredFThomeAtleastonegoal=" + statsScoredFThomeAtleastonegoal +
                ", statsScoredFThomeMatches=" + statsScoredFThomeMatches +
                ", statsScoredFTawayTotal=" + statsScoredFTawayTotal +
                ", statsScoredFTawayAverage=" + statsScoredFTawayAverage +
                ", statsScoredFTawayAtleastonegoal=" + statsScoredFTawayAtleastonegoal +
                ", statsScoredFTawayMatches=" + statsScoredFTawayMatches +
                ", statsScoredP1Total=" + statsScoredP1Total +
                ", statsScoredP1Average=" + statsScoredP1Average +
                ", statsScoredP1Atleastonegoal=" + statsScoredP1Atleastonegoal +
                ", statsScoredP1Matches=" + statsScoredP1Matches +
                ", statsScoredP1homeTotal=" + statsScoredP1homeTotal +
                ", statsScoredP1homeAverage=" + statsScoredP1homeAverage +
                ", statsScoredP1homeAtleastonegoal=" + statsScoredP1homeAtleastonegoal +
                ", statsScoredP1homeMatches=" + statsScoredP1homeMatches +
                ", statsScoredP1awayTotal=" + statsScoredP1awayTotal +
                ", statsScoredP1awayAverage=" + statsScoredP1awayAverage +
                ", statsScoredP1awayAtleastonegoal=" + statsScoredP1awayAtleastonegoal +
                ", statsScoredP1awayMatches=" + statsScoredP1awayMatches +
                ", statsScoredP2Total=" + statsScoredP2Total +
                ", statsScoredP2Average=" + statsScoredP2Average +
                ", statsScoredP2Atleastonegoal=" + statsScoredP2Atleastonegoal +
                ", statsScoredP2Matches=" + statsScoredP2Matches +
                ", statsScoredP2homeTotal=" + statsScoredP2homeTotal +
                ", statsScoredP2homeAverage=" + statsScoredP2homeAverage +
                ", statsScoredP2homeAtleastonegoal=" + statsScoredP2homeAtleastonegoal +
                ", statsScoredP2homeMatches=" + statsScoredP2homeMatches +
                ", statsScoredP2awayTotal=" + statsScoredP2awayTotal +
                ", statsScoredP2awayAverage=" + statsScoredP2awayAverage +
                ", statsScoredP2awayAtleastonegoal=" + statsScoredP2awayAtleastonegoal +
                ", statsScoredP2awayMatches=" + statsScoredP2awayMatches +
                ", statsConcededFtTotal=" + statsConcededFtTotal +
                ", statsConcededFtAverage=" + statsConcededFtAverage +
                ", statsConcededFtCleansheets=" + statsConcededFtCleansheets +
                ", statsConcededFtMatches=" + statsConcededFtMatches +
                ", statsConcededFthomeTotal=" + statsConcededFthomeTotal +
                ", statsConcededFthomeAverage=" + statsConcededFthomeAverage +
                ", statsConcededFthomeCleansheets=" + statsConcededFthomeCleansheets +
                ", statsConcededFthomeMatches=" + statsConcededFthomeMatches +
                ", statsConcededFtawayTotal=" + statsConcededFtawayTotal +
                ", statsConcededFtawayAverage=" + statsConcededFtawayAverage +
                ", statsConcededFtawayCleansheets=" + statsConcededFtawayCleansheets +
                ", statsConcededFtawayMatches=" + statsConcededFtawayMatches +
                ", statsConcededP1Total=" + statsConcededP1Total +
                ", statsConcededP1Average=" + statsConcededP1Average +
                ", statsConcededP1Cleansheets=" + statsConcededP1Cleansheets +
                ", statsConcededP1Matches=" + statsConcededP1Matches +
                ", statsConcededP1homeTotal=" + statsConcededP1homeTotal +
                ", statsConcededP1homeAverage=" + statsConcededP1homeAverage +
                ", statsConcededP1homeCleansheets=" + statsConcededP1homeCleansheets +
                ", statsConcededP1homeMatches=" + statsConcededP1homeMatches +
                ", statsConcededP1awayTotal=" + statsConcededP1awayTotal +
                ", statsConcededP1awayAverage=" + statsConcededP1awayAverage +
                ", statsConcededP1awayCleansheets=" + statsConcededP1awayCleansheets +
                ", statsConcededP1awayMatches=" + statsConcededP1awayMatches +
                ", statsConcededP2Total=" + statsConcededP2Total +
                ", statsConcededP2Average=" + statsConcededP2Average +
                ", statsConcededP2Cleansheets=" + statsConcededP2Cleansheets +
                ", statsConcededP2Matches=" + statsConcededP2Matches +
                ", statsConcededP2homeTotal=" + statsConcededP2homeTotal +
                ", statsConcededP2homeAverage=" + statsConcededP2homeAverage +
                ", statsConcededP2homeCleansheets=" + statsConcededP2homeCleansheets +
                ", statsConcededP2homeMatches=" + statsConcededP2homeMatches +
                ", statsConcededP2awayTotal=" + statsConcededP2awayTotal +
                ", statsConcededP2awayAverage=" + statsConcededP2awayAverage +
                ", statsConcededP2awayCleansheets=" + statsConcededP2awayCleansheets +
                ", statsConcededP2awayMatches=" + statsConcededP2awayMatches +
                ", statsTotalFt05Over=" + statsTotalFt05Over +
                ", statsTotalFt05Under=" + statsTotalFt05Under +
                ", statsTotalFt15Over=" + statsTotalFt15Over +
                ", statsTotalFt15Under=" + statsTotalFt15Under +
                ", statsTotalFt25Over=" + statsTotalFt25Over +
                ", statsTotalFt25Under=" + statsTotalFt25Under +
                ", statsTotalFt35Over=" + statsTotalFt35Over +
                ", statsTotalFt35Under=" + statsTotalFt35Under +
                ", statsTotalFt45Over=" + statsTotalFt45Over +
                ", statsTotalFt45Under=" + statsTotalFt45Under +
                ", statsTotalFt55Over=" + statsTotalFt55Over +
                ", statsTotalFt55Under=" + statsTotalFt55Under +
                ", statsTotalP105Over=" + statsTotalP105Over +
                ", statsTotalP105Under=" + statsTotalP105Under +
                ", statsTotalP115Over=" + statsTotalP115Over +
                ", statsTotalP115Under=" + statsTotalP115Under +
                ", statsTotalP125Over=" + statsTotalP125Over +
                ", statsTotalP125Under=" + statsTotalP125Under +
                ", statsTotalP135Over=" + statsTotalP135Over +
                ", statsTotalP135Under=" + statsTotalP135Under +
                ", statsTotalP145Over=" + statsTotalP145Over +
                ", statsTotalP145Under=" + statsTotalP145Under +
                ", statsTotalP155Over=" + statsTotalP155Over +
                ", statsTotalP155Under=" + statsTotalP155Under +
                ", statsTotalP205Over=" + statsTotalP205Over +
                ", statsTotalP205Under=" + statsTotalP205Under +
                ", statsTotalP215Over=" + statsTotalP215Over +
                ", statsTotalP215Under=" + statsTotalP215Under +
                ", statsTotalP225Over=" + statsTotalP225Over +
                ", statsTotalP225Under=" + statsTotalP225Under +
                ", statsTotalP235Over=" + statsTotalP235Over +
                ", statsTotalP235Under=" + statsTotalP235Under +
                ", statsTotalP245Over=" + statsTotalP245Over +
                ", statsTotalP245Under=" + statsTotalP245Under +
                ", statsTotalP255Over=" + statsTotalP255Over +
                ", statsTotalP255Under=" + statsTotalP255Under +
                ", statsHomeFt05Over=" + statsHomeFt05Over +
                ", statsHomeFt05Under=" + statsHomeFt05Under +
                ", statsHomeFt15Over=" + statsHomeFt15Over +
                ", statsHomeFt15Under=" + statsHomeFt15Under +
                ", statsHomeFt25Over=" + statsHomeFt25Over +
                ", statsHomeFt25Under=" + statsHomeFt25Under +
                ", statsHomeFt35Over=" + statsHomeFt35Over +
                ", statsHomeFt35Under=" + statsHomeFt35Under +
                ", statsHomeFt45Over=" + statsHomeFt45Over +
                ", statsHomeFt45Under=" + statsHomeFt45Under +
                ", statsHomeFt55Over=" + statsHomeFt55Over +
                ", statsHomeFt55Under=" + statsHomeFt55Under +
                ", statsHomeP105Over=" + statsHomeP105Over +
                ", statsHomeP105Under=" + statsHomeP105Under +
                ", statsHomeP115Over=" + statsHomeP115Over +
                ", statsHomeP115Under=" + statsHomeP115Under +
                ", statsHomeP125Over=" + statsHomeP125Over +
                ", statsHomeP125Under=" + statsHomeP125Under +
                ", statsHomeP135Over=" + statsHomeP135Over +
                ", statsHomeP135Under=" + statsHomeP135Under +
                ", statsHomeP145Over=" + statsHomeP145Over +
                ", statsHomeP145Under=" + statsHomeP145Under +
                ", statsHomeP155Over=" + statsHomeP155Over +
                ", statsHomeP155Under=" + statsHomeP155Under +
                ", statsHomeP205Over=" + statsHomeP205Over +
                ", statsHomeP205Under=" + statsHomeP205Under +
                ", statsHomeP215Over=" + statsHomeP215Over +
                ", statsHomeP215Under=" + statsHomeP215Under +
                ", statsHomeP225Over=" + statsHomeP225Over +
                ", statsHomeP225Under=" + statsHomeP225Under +
                ", statsHomeP235Over=" + statsHomeP235Over +
                ", statsHomeP235Under=" + statsHomeP235Under +
                ", statsHomeP245Over=" + statsHomeP245Over +
                ", statsHomeP245Under=" + statsHomeP245Under +
                ", statsHomeP255Over=" + statsHomeP255Over +
                ", statsHomeP255Under=" + statsHomeP255Under +
                ", statsAwayFt05Over=" + statsAwayFt05Over +
                ", statsAwayFt05Under=" + statsAwayFt05Under +
                ", statsAwayFt15Over=" + statsAwayFt15Over +
                ", statsAwayFt15Under=" + statsAwayFt15Under +
                ", statsAwayFt25Over=" + statsAwayFt25Over +
                ", statsAwayFt25Under=" + statsAwayFt25Under +
                ", statsAwayFt35Over=" + statsAwayFt35Over +
                ", statsAwayFt35Under=" + statsAwayFt35Under +
                ", statsAwayFt45Over=" + statsAwayFt45Over +
                ", statsAwayFt45Under=" + statsAwayFt45Under +
                ", statsAwayFt55Over=" + statsAwayFt55Over +
                ", statsAwayFt55Under=" + statsAwayFt55Under +
                ", statsAwayP105Over=" + statsAwayP105Over +
                ", statsAwayP105Under=" + statsAwayP105Under +
                ", statsAwayP115Over=" + statsAwayP115Over +
                ", statsAwayP115Under=" + statsAwayP115Under +
                ", statsAwayP125Over=" + statsAwayP125Over +
                ", statsAwayP125Under=" + statsAwayP125Under +
                ", statsAwayP135Over=" + statsAwayP135Over +
                ", statsAwayP135Under=" + statsAwayP135Under +
                ", statsAwayP145Over=" + statsAwayP145Over +
                ", statsAwayP145Under=" + statsAwayP145Under +
                ", statsAwayP155Over=" + statsAwayP155Over +
                ", statsAwayP155Under=" + statsAwayP155Under +
                ", statsAwayP205Over=" + statsAwayP205Over +
                ", statsAwayP205Under=" + statsAwayP205Under +
                ", statsAwayP215Over=" + statsAwayP215Over +
                ", statsAwayP215Under=" + statsAwayP215Under +
                ", statsAwayP225Over=" + statsAwayP225Over +
                ", statsAwayP225Under=" + statsAwayP225Under +
                ", statsAwayP235Over=" + statsAwayP235Over +
                ", statsAwayP235Under=" + statsAwayP235Under +
                ", statsAwayP245Over=" + statsAwayP245Over +
                ", statsAwayP245Under=" + statsAwayP245Under +
                ", statsAwayP255Over=" + statsAwayP255Over +
                ", statsAwayP255Under=" + statsAwayP255Under +
                '}';
    }
}
