package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

import java.util.ArrayList;
import java.util.List;

public class StatsSeasonMeta extends BaseRootEntity {
    private Boolean statsCoverageComplexStat;
    private Boolean statsCoverageLiveTable;
    private Boolean statsCoverageHalftimeTable;
    private Boolean statsCoverageOverUnder;
    private Boolean statsCoverageOverUnderHalftime;
    private Boolean statsCoverageFixtures;
    private Boolean statsCoverageLeagueTable;
    private Boolean statsCoverageTableRules;
    private Boolean statsCoverageHeadToHead;
    private Boolean statsCoverageFormTable;
    private Boolean statsCoverageSeconHalfTables;
    private Boolean statsCoverageDivisionView;
    private Boolean statsMatchDetails;
    private Boolean statsCoverageLineups;
    private Boolean statsCoverageFormations;
    private Boolean statsCoverageTopGoals;
    private Boolean statsCoverageTopAssists;
    private Boolean statsCoverageDisciplinary;
    private Boolean statsCoverageInjuryList;
    private Boolean statsCoverageRedCards;
    private Boolean statsCoverageYellowCards;
    private Boolean statsCoverageGoalMinute;
    private Boolean statsCoverageGoalMinScorer;
    private Boolean statsCoverageSubstitutions;
    private Boolean statsCoverageSquadService;
    private Boolean statsCoverageTransferHistory;
    private Boolean statsCoverageLiveScoreEventThrowin;
    private Boolean statsCoverageLiveScoreEventGoalkick;
    private Boolean statsCoverageLiveScoreEventFreekick;
    private Boolean statsCoverageLiveScoreEventShotsOffGoal;
    private Boolean statsCoverageLiveScoreEventShotsOnGoal;
    private Boolean statsCoverageLiveScoreEventGoalkeeperSave;
    private Boolean statsCoverageLiveScoreEventCornerkick;
    private Boolean statsCoverageLiveScoreEventOffside;
    private Boolean statsCoverageLiveScoreEventFouls;
    private Boolean statsCoverageLiveScoreEventPossession;
    private Boolean statsCoverageReferee;
    private Boolean statsCoverageStadium;
    private Boolean statsCoverageStaffManagers;
    private Boolean statsCoverageStaffTeamOfficials;
    private Boolean statsCoverageStaffAssistantCoaches;
    private Boolean statsCoverageJerseys;
    private EntityIdList tournamentIds = new EntityIdList();
    private EntityIdList tableIds = new EntityIdList();
    private EntityId seasonId;
    private EntityId realCategoryId;
    private EntityId uniqueTournamentId;

    public StatsSeasonMeta(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonMeta, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); return true;
            case "sport": return true;
            case "realcategory": this.realCategoryId = childEntity.getId(); return true;
            case "uniquetournament": this.uniqueTournamentId = childEntity.getId(); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "tournamentids[]": tournamentIds.add(new EntityId(node.asLong())); break;
            case "tableids[]": tableIds.add(new EntityId(node.asLong())); break;
            case "statscoverage.complexstat": this.statsCoverageComplexStat = node.asBoolean(); break;
            case "statscoverage.livetable": this.statsCoverageLiveTable = node.asBoolean(); break;
            case "statscoverage.halftimetable": this.statsCoverageHalftimeTable = node.asBoolean(); break;
            case "statscoverage.overunder": this.statsCoverageOverUnder = node.asBoolean(); break;
            case "statscoverage.overunderhalftime": this.statsCoverageOverUnderHalftime = node.asBoolean(); break;
            case "statscoverage.fixtures": this.statsCoverageFixtures = node.asBoolean(); break;
            case "statscoverage.leaguetable": this.statsCoverageLeagueTable = node.asBoolean(); break;
            case "statscoverage.tablerules": this.statsCoverageTableRules = node.asBoolean(); break;
            case "statscoverage.headtohead": this.statsCoverageHeadToHead = node.asBoolean(); break;
            case "statscoverage.formtable": this.statsCoverageFormTable = node.asBoolean(); break;
            case "statscoverage.secondhalftables": this.statsCoverageSeconHalfTables = node.asBoolean(); break;
            case "statscoverage.divisionview": this.statsCoverageDivisionView = node.asBoolean(); break;
            case "statscoverage.matchdetails": this.statsMatchDetails = node.asBoolean(); break;
            case "statscoverage.lineups": this.statsCoverageLineups = node.asBoolean(); break;
            case "statscoverage.formations": this.statsCoverageFormations = node.asBoolean(); break;
            case "statscoverage.topgoals": this.statsCoverageTopGoals = node.asBoolean(); break;
            case "statscoverage.topassists": this.statsCoverageTopAssists = node.asBoolean(); break;
            case "statscoverage.disciplinary": this.statsCoverageDisciplinary = node.asBoolean(); break;
            case "statscoverage.injurylist": this.statsCoverageInjuryList = node.asBoolean(); break;
            case "statscoverage.redcards": this.statsCoverageRedCards = node.asBoolean(); break;
            case "statscoverage.yellowcards": this.statsCoverageYellowCards = node.asBoolean(); break;
            case "statscoverage.goalminute": this.statsCoverageGoalMinute = node.asBoolean(); break;
            case "statscoverage.goalminscorer": this.statsCoverageGoalMinScorer = node.asBoolean(); break;
            case "statscoverage.substitutions": this.statsCoverageSubstitutions = node.asBoolean(); break;
            case "statscoverage.squadservice": this.statsCoverageSquadService = node.asBoolean(); break;
            case "statscoverage.transferhistory": this.statsCoverageTransferHistory = node.asBoolean(); break;
            case "statscoverage.livescoreeventthrowin": this.statsCoverageLiveScoreEventThrowin = node.asBoolean(); break;
            case "statscoverage.livescoreeventgoalkick": this.statsCoverageLiveScoreEventGoalkick = node.asBoolean(); break;
            case "statscoverage.livescoreeventfreekick": this.statsCoverageLiveScoreEventFreekick = node.asBoolean(); break;
            case "statscoverage.livescoreeventshotsoffgoal": this.statsCoverageLiveScoreEventShotsOffGoal = node.asBoolean(); break;
            case "statscoverage.livescoreeventshotsongoal": this.statsCoverageLiveScoreEventShotsOnGoal = node.asBoolean(); break;
            case "statscoverage.livescoreeventgoalkeepersave": this.statsCoverageLiveScoreEventGoalkeeperSave = node.asBoolean(); break;
            case "statscoverage.livescoreeventcornerkick": this.statsCoverageLiveScoreEventCornerkick = node.asBoolean(); break;
            case "statscoverage.livescoreeventoffside": this.statsCoverageLiveScoreEventOffside = node.asBoolean(); break;
            case "statscoverage.livescoreeventfouls": this.statsCoverageLiveScoreEventFouls = node.asBoolean(); break;
            case "statscoverage.livescoreeventpossesion": this.statsCoverageLiveScoreEventPossession = node.asBoolean(); break;
            case "statscoverage.referee": this.statsCoverageReferee = node.asBoolean(); break;
            case "statscoverage.stadium": this.statsCoverageStadium = node.asBoolean(); break;
            case "statscoverage.staffmanagers": this.statsCoverageStaffManagers = node.asBoolean(); break;
            case "statscoverage.staffteamofficials": this.statsCoverageStaffTeamOfficials = node.asBoolean(); break;
            case "statscoverage.staffassistantcoaches": this.statsCoverageStaffAssistantCoaches = node.asBoolean(); break;
            case "statscoverage.jerseys": this.statsCoverageJerseys = node.asBoolean(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonMeta{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append(", realCategoryId=").append(realCategoryId);
        sb.append(", uniqueTournamentId=").append(uniqueTournamentId);
        sb.append(", statsCoverageComplexStat=").append(statsCoverageComplexStat);
        sb.append(", statsCoverageLiveTable=").append(statsCoverageLiveTable);
        sb.append(", statsCoverageHalftimeTable=").append(statsCoverageHalftimeTable);
        sb.append(", statsCoverageOverUnder=").append(statsCoverageOverUnder);
        sb.append(", statsCoverageOverUnderHalftime=").append(statsCoverageOverUnderHalftime);
        sb.append(", statsCoverageFixtures=").append(statsCoverageFixtures);
        sb.append(", statsCoverageLeagueTable=").append(statsCoverageLeagueTable);
        sb.append(", statsCoverageTableRules=").append(statsCoverageTableRules);
        sb.append(", statsCoverageHeadToHead=").append(statsCoverageHeadToHead);
        sb.append(", statsCoverageFormTable=").append(statsCoverageFormTable);
        sb.append(", statsCoverageSeconHalfTables=").append(statsCoverageSeconHalfTables);
        sb.append(", statsCoverageDivisionView=").append(statsCoverageDivisionView);
        sb.append(", statsMatchDetails=").append(statsMatchDetails);
        sb.append(", statsCoverageLineups=").append(statsCoverageLineups);
        sb.append(", statsCoverageFormations=").append(statsCoverageFormations);
        sb.append(", statsCoverageTopGoals=").append(statsCoverageTopGoals);
        sb.append(", statsCoverageTopAssists=").append(statsCoverageTopAssists);
        sb.append(", statsCoverageDisciplinary=").append(statsCoverageDisciplinary);
        sb.append(", statsCoverageInjuryList=").append(statsCoverageInjuryList);
        sb.append(", statsCoverageRedCards=").append(statsCoverageRedCards);
        sb.append(", statsCoverageYellowCards=").append(statsCoverageYellowCards);
        sb.append(", statsCoverageGoalMinute=").append(statsCoverageGoalMinute);
        sb.append(", statsCoverageGoalMinScorer=").append(statsCoverageGoalMinScorer);
        sb.append(", statsCoverageSubstitutions=").append(statsCoverageSubstitutions);
        sb.append(", statsCoverageSquadService=").append(statsCoverageSquadService);
        sb.append(", statsCoverageTransferHistory=").append(statsCoverageTransferHistory);
        sb.append(", statsCoverageLiveScoreEventThrowin=").append(statsCoverageLiveScoreEventThrowin);
        sb.append(", statsCoverageLiveScoreEventGoalkick=").append(statsCoverageLiveScoreEventGoalkick);
        sb.append(", statsCoverageLiveScoreEventFreekick=").append(statsCoverageLiveScoreEventFreekick);
        sb.append(", statsCoverageLiveScoreEventShotsOffGoal=").append(statsCoverageLiveScoreEventShotsOffGoal);
        sb.append(", statsCoverageLiveScoreEventShotsOnGoal=").append(statsCoverageLiveScoreEventShotsOnGoal);
        sb.append(", statsCoverageLiveScoreEventGoalkeeperSave=").append(statsCoverageLiveScoreEventGoalkeeperSave);
        sb.append(", statsCoverageLiveScoreEventCornerkick=").append(statsCoverageLiveScoreEventCornerkick);
        sb.append(", statsCoverageLiveScoreEventOffside=").append(statsCoverageLiveScoreEventOffside);
        sb.append(", statsCoverageLiveScoreEventFouls=").append(statsCoverageLiveScoreEventFouls);
        sb.append(", statsCoverageLiveScoreEventPossesion=").append(statsCoverageLiveScoreEventPossession);
        sb.append(", statsCoverageReferee=").append(statsCoverageReferee);
        sb.append(", statsCoverageStadium=").append(statsCoverageStadium);
        sb.append(", statsCoverageStaffmMnagers=").append(statsCoverageStaffManagers);
        sb.append(", statsCoverageStaffTeamOfficials=").append(statsCoverageStaffTeamOfficials);
        sb.append(", statsCoverageStaffAssistantCoaches=").append(statsCoverageStaffAssistantCoaches);
        sb.append(", statsCoverageJerseys=").append(statsCoverageJerseys);
        sb.append(", tournamentIds=").append(tournamentIds);
        sb.append(", tableIds=").append(tableIds);
        sb.append('}');
        return sb.toString();
    }
}
