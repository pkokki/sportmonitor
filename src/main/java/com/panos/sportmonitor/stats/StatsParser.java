package com.panos.sportmonitor.stats;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.entities.*;
import com.panos.sportmonitor.stats.entities.root.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StatsParser {
    public void Parse(String jsonFilePath) throws IOException {
        File source = new File(jsonFilePath);
        Parse(source);
    }

    public void Parse(File jsonFile) throws IOException {
        if (true
                //&& !jsonFile.getName().equals("stats_season_meta#67061.json")
                //&& !jsonFile.getName().equals("stats_match_get#21545063.json")
                //&& !jsonFile.getName().equals("stats_team_versus#2711#2701.json")
                //&& !jsonFile.getName().equals("stats_team_odds_client#2711.json")
                //&& !jsonFile.getName().equals("stats_team_info#2711.json")
                //&& !jsonFile.getName().equals("stats_team_lastx#2711#30.json"
                //&& !jsonFile.getName().equals("stats_team_nextx#2711.json")
                //&& !jsonFile.getName().equals("stats_season_tables#67061.json"
                && !jsonFile.getName().equals("stats_season_overunder#67061.json")
        )
            return;
        System.out.println(String.format("Parsing '%s'", jsonFile.getName()));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonFile);
        Parse(rootNode);
    }

    public void Parse(JsonNode rootNode) {
        RootEntity rootEntity = createRootEntity(rootNode.get("event").asText());
        traverse(1,"", rootNode.get("data"), rootEntity);
        if (rootEntity != null)
            rootEntity.print();
    }

    private void traverse(final int level, final String currentNodeName, final JsonNode currentNode, final BaseEntity parentEntity) {
        JsonNodeType nodeType = currentNode.getNodeType();
        if (nodeType == JsonNodeType.ARRAY) {
            for (JsonNode childNode : currentNode) {
                traverse(level, currentNodeName + "[]", childNode, parentEntity);
            }
        }
        else if (nodeType == JsonNodeType.OBJECT) {
            BaseEntity childEntity = null;
            String childPrefix = currentNodeName;
            if (currentNode.has("_doc") && isEntityNode(currentNode)) {
                String docType = currentNode.get("_doc").asText();
                childEntity = createEntity(parentEntity, docType, getEntityId(currentNode));
                parentEntity.getRoot().register(level, childEntity);
                childPrefix = "";
            }
            for (Iterator<Map.Entry<String, JsonNode>> it = currentNode.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> childEntry = it.next();
                String childName = childEntry.getKey();
                JsonNode childNode = childEntry.getValue();
                traverse(level + 1,
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
        else {
            boolean r = parentEntity.setProperty(currentNodeName, nodeType, currentNode);
            if (!r)
                System.err.println(String.format("%s [UNHANDLED]: %s --- %s --- %s",
                        parentEntity.getClass().getSimpleName(),
                        currentNodeName,
                        nodeType,
                        currentNode.asText("<empty>")));
        }
    }

    private boolean isEntityNode(JsonNode node) {
        return node.has("_id") || node.has("_mid") || node.get("_doc").asText().equals("uniqueteamform");
    }
    private long getEntityId(JsonNode node) {
        if (node.get("_doc").asText().equals("uniqueteamform"))
            return Long.parseLong(node.get("uniqueteamid").asText() + node.get("matchid").asText());
        return node.has("_id") ? node.get("_id").asLong() : node.get("_mid").asLong();
    }

    public RootEntity createRootEntity(String name) {
        RootEntity entity;
        switch (name) {
            case "stats_season_meta": entity = new StatsSeasonMeta(name); break;
            case "stats_match_get": entity = new StatsMatchGet(name); break;
            case "stats_team_versus": entity = new StatsTeamVersus(name); break;
            case "stats_season_teams2": entity = new StatsSeasonTeams2(name); break;
            case "stats_team_odds_client": entity = new StatsTeamOddsClient(name); break;
            case "stats_team_info": entity = new StatsTeamInfo(name); break;
            case "stats_team_lastx": entity = new StatsTeamLastX(name); break;
            case "stats_team_nextx": entity = new StatsTeamNextX(name); break;
            case "stats_season_tables": entity = new StatsSeasonTables(name); break;
            case "stats_season_overunder": entity = new StatsSeasonOverUnder(name); break;
            default: System.err.println("createRootEntity [UNKNOWN]: " + name); entity = new RootEntity(name);
        }
        return entity;
    }

    public BaseEntity createEntity(BaseEntity parent, String docType, long id) {
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
            default: System.err.println("createEntity [UNKNOWN]: " + docType); entity = new NullEntity(parent);
        }
        return entity;
    }
}
