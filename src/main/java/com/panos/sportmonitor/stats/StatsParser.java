package com.panos.sportmonitor.stats;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.entities.*;
import com.panos.sportmonitor.stats.entities.root.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatsParser {
    public void parse(final String jsonFilePath) throws IOException {
        File source = new File(jsonFilePath);
        parse(source);
    }

    public void parse(final File jsonFile) throws IOException {
        System.out.println(String.format("Parsing '%s'", jsonFile.getName()));
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = mapper.readTree(jsonFile);
        parse(rootNode);
    }

    public void parse(final JsonNode rootNode) {
        long timeStamp = rootNode.get("_dob").asLong();
        final RootEntity rootEntity = createRootEntity(rootNode.get("event").asText(), timeStamp);
        traverse(1, timeStamp,"", rootNode.get("data"), rootEntity);
        //rootEntity.print();
    }

    private void traverse(final int level, final long timeStamp, final String currentNodeName, final JsonNode currentNode, final BaseEntity parentEntity) {
        JsonNodeType currentNodeType = currentNode.getNodeType();
        if (currentNodeType == JsonNodeType.ARRAY) {
            int index = 0;
            for (JsonNode childNode : currentNode) {
                childNode = parentEntity.transformChildNode(currentNodeName, index, childNode);
                traverse(level, timeStamp, currentNodeName + "[]", childNode, parentEntity);
                ++index;
            }
        }
        else if (currentNodeType == JsonNodeType.OBJECT) {
            JsonNode transformedNode = parentEntity.transformChildNode(currentNodeName, -1, currentNode);
            traverseObject(level, timeStamp, currentNodeName, transformedNode, parentEntity);
        }
        else {
            traverseProperty(level, currentNodeName, currentNodeType, currentNode, parentEntity);
        }
    }

    private void traverseProperty(final int level, final String currentNodeName, final JsonNodeType currentNodeType,
                                  final JsonNode currentNode, final BaseEntity parentEntity) {
        boolean r = parentEntity.setProperty(currentNodeName, currentNodeType, currentNode);
        if (!r)
            System.err.println(String.format("%s [UNHANDLED PROPERTY]: %s --- %s --- %s",
                    parentEntity.getClass().getSimpleName(),
                    currentNodeName,
                    currentNodeType,
                    currentNode.asText("<empty>")));
    }

    private void traverseObject(final int level, final long timeStamp, final String currentNodeName, final JsonNode currentNode, final BaseEntity parentEntity) {
        BaseEntity childEntity = null;
        String childPrefix = currentNodeName;
        if (currentNode.has("_doc")) {
            String docType = currentNode.get("_doc").asText();
            if (isEntityNode(currentNodeName, docType, currentNode)) {
                long auxEntityId = getAuxEntityId(currentNodeName);
                long childEntityId = getEntityId(currentNodeName, auxEntityId, docType, currentNode);
                childEntity = createEntity(parentEntity, docType, childEntityId, timeStamp);
                childEntity.setAuxId(auxEntityId);
                if (!childEntity.handleAuxId(auxEntityId)) {
                    System.err.println(String.format("%s [UNHANDLED AUX ID]: '%s' --- id=%s, aux=%s",
                            childEntity.getClass().getSimpleName(),
                            currentNodeName,
                            childEntity.getId(),
                            auxEntityId));
                }
                childPrefix = "";
                parentEntity.getRoot().register(level, childEntity);
            }
        }
        for (Iterator<Map.Entry<String, JsonNode>> it = currentNode.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> childEntry = it.next();
            String childName = childEntry.getKey();
            JsonNode childNode = childEntry.getValue();
            traverse(level + 1, timeStamp,
                    (childPrefix.length() > 0 ? childPrefix + "." : "") + childName,
                    childNode,
                    childEntity != null ? childEntity : parentEntity);
        }
        if (parentEntity != null && childEntity != null && !parentEntity.setEntity(currentNodeName, childEntity)) {
            System.err.println(String.format("%s [UNHANDLED CHILD ENTITY]: '%s' --- %s",
                    parentEntity.getClass().getSimpleName(),
                    currentNodeName,
                    childEntity.getClass().getSimpleName()));
        }
    }

    private boolean isEntityNode(final String nodeName, final String nodeType, final JsonNode node) {
        return node.has("_id")
                || node.has("_mid")
                || nodeType.equals("uniqueteamform")
                || nodeType.equals("seasonpos")
                ;
    }
    private long getEntityId(final String nodeName, final long auxEntityId, final String nodeType, final JsonNode node) {
        if (nodeType.equals("uniqueteamform"))
            return Long.parseLong(node.get("uniqueteamid").asText() + node.get("matchid").asText());
        else if (nodeType.equals("seasonpos")) {
            if (auxEntityId == 0)
                throw new NumberFormatException("auxEntityId not found!");
            String composite = String.format("%08d%08d%02d", node.get("seasonid").asInt(), auxEntityId, node.get("round").asInt());
            return Long.parseLong(composite);
        }
        return node.has("_id") ? node.get("_id").asLong() : node.get("_mid").asLong();
    }
    private long getAuxEntityId(final String nodeName) {
        Pattern regEx = Pattern.compile("[^0-9]*(\\d+)[^0-9]*");
        Matcher matcher = regEx.matcher(nodeName);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return 0;
    }

    public RootEntity createRootEntity(final String name, final long timeStamp) {
        RootEntity entity;
        switch (name) {
            case "stats_season_meta": entity = new StatsSeasonMeta(name, timeStamp); break;
            case "stats_match_get": entity = new StatsMatchGet(name, timeStamp); break;
            case "stats_team_versus": entity = new StatsTeamVersus(name, timeStamp); break;
            case "stats_season_teams2": entity = new StatsSeasonTeams2(name, timeStamp); break;
            case "stats_team_odds_client": entity = new StatsTeamOddsClient(name, timeStamp); break;
            case "stats_team_info": entity = new StatsTeamInfo(name, timeStamp); break;
            case "stats_team_lastx": entity = new StatsTeamLastX(name, timeStamp); break;
            case "stats_team_nextx": entity = new StatsTeamNextX(name, timeStamp); break;
            case "stats_season_lastx": entity = new StatsSeasonLastX(name, timeStamp); break;
            case "stats_season_nextx": entity = new StatsSeasonNextX(name, timeStamp); break;
            case "stats_season_tables": entity = new StatsSeasonTables(name, timeStamp); break;
            case "stats_season_overunder": entity = new StatsSeasonOverUnder(name, timeStamp); break;
            case "stats_season_teampositionhistory": entity = new StatsSeasonTeamPositionHistory(name, timeStamp); break;
            case "stats_formtable": entity = new StatsFormTable(name, timeStamp); break;
            case "stats_season_topgoals": entity = new StatsSeasonTopGoals(name, timeStamp); break;
            case "stats_season_topassists": entity = new StatsSeasonTopAssists(name, timeStamp); break;
            case "stats_season_topcards": entity = new StatsSeasonTopCards(name, timeStamp); break;
            case "stats_season_injuries": entity = new StatsSeasonInjuries(name, timeStamp); break;
            case "stats_season_leaguesummary": entity = new StatsSeasonLeagueSummary(name, timeStamp); break;
            case "stats_season_goals": entity = new StatsSeasonGoals(name, timeStamp); break;
            case "stats_season_uniqueteamstats": entity = new StatsSeasonUniqueTeamStats(name, timeStamp); break;
            case "stats_season_odds": entity = new StatsSeasonOdds(name, timeStamp); break;
            case "stats_season_fixtures": entity = new StatsSeasonFixtures(name, timeStamp); break;
            case "match_funfacts": entity = new MatchFunFacts(name, timeStamp); break;
            default: System.err.println("createRootEntity [UNKNOWN]: " + name); entity = new RootEntity(name, timeStamp);
        }
        return entity;
    }

    public BaseEntity createEntity(final BaseEntity parent, final String docType, final long id, final long timeStamp) {
        BaseEntity entity;
        switch (docType) {
            case "season": entity = new SeasonEntity(parent, id); break;
            case "match": entity = new MatchEntity(parent, id); break;
            case "player": entity = new PlayerEntity(parent, id); break;
            case "team":
            case "teams.home":
            case "teams.away":
            case "hometeams[]":
                entity = new TeamEntity(parent, id); break;
            case "uniqueteam":
                entity = new UniqueTeamEntity(parent, id); break;
            case "sport": entity = new SportEntity(parent, id); break;
            case "realcategory": entity = new RealCategoryEntity(parent, id); break;
            case "countrycode":
            case "nationality":
                entity = new CountryCodeEntity(parent, id); break;
            case "stadium":
                entity = new StadiumEntity(parent, id); break;
            case "uniquetournament": entity = new UniqueTournamentEntity(parent, id); break;
            case "tournament": entity = new TournamentEntity(parent, id); break;
            case "roundname": entity = new RoundNameEntity(parent, id); break;
            case "tableround": entity = new TableRoundEntity(parent, id); break;
            case "cupround": entity = new CupRoundEntity(parent, id); break;
            case "statistics_table": entity = new StatisticsTableEntity(parent, id); break;
            case "bookmaker": entity = new BookmakerEntity(parent, id); break;
            case "odds": entity = new OddsEntity(parent, id); break;
            case "uniqueteamform": entity = new UniqueTeamFormEntity(parent, id); break;
            case "statistics_leaguetable": entity = new LeagueTableEntity(parent, id); break;
            case "tiebreakrule": entity = new TieBreakRuleEntity(parent, id); break;
            case "tablerow": entity = new TableRowEntity(parent, id); break;
            case "promotion": entity = new PromotionEntity(parent, id); break;
            case "matchtype": entity = new MatchTypeEntity(parent, id); break;
            case "tabletype": entity = new TableTypeEntity(parent, id); break;
            case "seasonpos": entity = new SeasonPosEntity(parent, id); break;
            case "team_form_table": entity = new TeamFormTableEntity(parent, id, timeStamp); break;
            case "team_form_entry": entity = new TeamFormEntryEntity(parent, id, timeStamp); break;
            case "toplistentry": entity = new TopListEntryEntity(parent, id, timeStamp); break;
            case "player_position_type": entity = new PlayerPositionTypeEntity(parent, id); break;
            case "team_player_top_list_entry": entity = new TeamPlayerTopListEntryEntity(parent, id, timeStamp); break;
            case "playerstatus": entity = new PlayerStatusEntity(parent, id); break;
            case "team_goal_stats": entity = new TeamGoalStatsEntity(parent, id, timeStamp); break;
            case "unique_team_stats": entity = new UniqueTeamStatsEntity(parent, id, timeStamp); break;
            case "match_funfact": entity = new MatchFunFactEntity(parent, id, timeStamp); break;
            default: System.err.println("createEntity [UNKNOWN]: " + docType); entity = new NullEntity(parent);
        }
        return entity;
    }
}
