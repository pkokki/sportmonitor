package com.panos.sportmonitor.stats;

import java.util.Objects;

public class BaseRootEntityType {
    public static final BaseRootEntityType NullRoot = new BaseRootEntityType(0, "null");
    public static final BaseRootEntityType MatchDetailsExtended = new BaseRootEntityType(1, "MatchDetailsExtended");
    public static final BaseRootEntityType MatchFunFacts = new BaseRootEntityType(2, "MatchFunFacts");
    public static final BaseRootEntityType MatchTimeline = new BaseRootEntityType(3, "MatchTimeline");
    public static final BaseRootEntityType StatsFormTable = new BaseRootEntityType(4, "StatsFormTable");
    public static final BaseRootEntityType StatsMatchGet = new BaseRootEntityType(5, "StatsMatchGet");
    public static final BaseRootEntityType StatsMatchSituation = new BaseRootEntityType(6, "StatsMatchSituation");
    public static final BaseRootEntityType StatsSeasonFixtures = new BaseRootEntityType(7, "StatsSeasonFixtures");
    public static final BaseRootEntityType StatsSeasonGoals = new BaseRootEntityType(8, "StatsSeasonGoals");
    public static final BaseRootEntityType StatsSeasonInjuries = new BaseRootEntityType(9, "StatsSeasonInjuries");
    public static final BaseRootEntityType StatsSeasonLastX = new BaseRootEntityType(10, "StatsSeasonLastX");
    public static final BaseRootEntityType StatsSeasonNextX = new BaseRootEntityType(11, "StatsSeasonNextX");
    public static final BaseRootEntityType StatsSeasonLeagueSummary = new BaseRootEntityType(12, "StatsSeasonLeagueSummary");
    public static final BaseRootEntityType StatsSeasonMeta = new BaseRootEntityType(13, "StatsSeasonMeta");
    public static final BaseRootEntityType StatsSeasonOdds = new BaseRootEntityType(14, "StatsSeasonOdds");
    public static final BaseRootEntityType StatsSeasonOverUnder = new BaseRootEntityType(15, "StatsSeasonOverUnder");
    public static final BaseRootEntityType StatsSeasonTables = new BaseRootEntityType(16, "StatsSeasonTables");
    public static final BaseRootEntityType StatsSeasonTeamPositionHistory = new BaseRootEntityType(17, "StatsSeasonTeamPositionHistory");
    public static final BaseRootEntityType StatsSeasonTeams2 = new BaseRootEntityType(18, "StatsSeasonTeams2");
    public static final BaseRootEntityType StatsSeasonTopGoals = new BaseRootEntityType(19, "StatsSeasonTopGoals");
    public static final BaseRootEntityType StatsSeasonTopAssists = new BaseRootEntityType(20, "StatsSeasonTopAssists");
    public static final BaseRootEntityType StatsSeasonTopCards = new BaseRootEntityType(21, "StatsSeasonTopCards");
    public static final BaseRootEntityType StatsSeasonUniqueTeamStats = new BaseRootEntityType(22, "StatsSeasonUniqueTeamStats");
    public static final BaseRootEntityType StatsTeamInfo = new BaseRootEntityType(23, "StatsTeamInfo");
    public static final BaseRootEntityType StatsTeamLastX = new BaseRootEntityType(24, "StatsTeamLastX");
    public static final BaseRootEntityType StatsTeamNextX = new BaseRootEntityType(25, "StatsTeamNextX");
    public static final BaseRootEntityType StatsTeamOddsClient = new BaseRootEntityType(26, "StatsTeamOddsClient");
    public static final BaseRootEntityType StatsTeamVersus = new BaseRootEntityType(27, "StatsTeamVersus");
    public static final BaseRootEntityType StatsTeamSquad = new BaseRootEntityType(28, "StatsTeamSquad");
    public static final BaseRootEntityType StatsTeamPlayerFacts = new BaseRootEntityType(29, "StatsTeamPlayerFacts");
    public static final BaseRootEntityType MatchInfo = new BaseRootEntityType(30, "MatchInfo");

    private final int id;
    private final String name;
    private BaseRootEntityType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseRootEntityType that = (BaseRootEntityType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
