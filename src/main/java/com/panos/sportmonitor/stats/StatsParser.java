package com.panos.sportmonitor.stats;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.entities.*;
import com.panos.sportmonitor.stats.entities.ref.*;
import com.panos.sportmonitor.stats.entities.root.*;
import com.panos.sportmonitor.stats.entities.time.*;
import org.apache.commons.lang.WordUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatsParser {
    private final StatsStore statsStore;
    private final List<StatsParserListener> listeners = new LinkedList<>();

    public StatsParser(StatsStore statsStore) {
        this.statsStore = statsStore;
    }

    public void parse(final String jsonFilePath) throws IOException {
        File source = new File(jsonFilePath);
        parse(source);
    }

    public void parse(final File jsonFile) throws IOException {
        StatsConsole.printlnInfo(String.format("Parsing file '%s'", jsonFile.getName()));
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = mapper.readTree(jsonFile);
        parse(rootNode);
    }

    public void parse(JsonNode rootNode) {
        if (rootNode.has("doc") && rootNode.get("doc").getNodeType() == JsonNodeType.ARRAY)
            rootNode = rootNode.get("doc").iterator().next();
        if (rootNode.has("_dob")) {
            final long timeStamp = rootNode.get("_dob").asLong();
            final String name = rootNode.get("event").asText();
            final BaseRootEntity baseRootEntity = createRootEntity(name, timeStamp, rootNode.get("data"), rootNode.path("radarUrl").asText(""));
            if (baseRootEntity != null) {
                StatsConsole.printlnInfo(String.format("Traversing root entity '%s'", baseRootEntity.getName()));
                traverse(1, timeStamp, "", rootNode.get("data"), baseRootEntity);
                baseRootEntity.endTraverse();
                statsStore.submit(baseRootEntity);
            } else
                StatsConsole.printlnWarn(String.format("StatsParser.parse [IGNORED ROOT TYPE]: %s", name));
        } else {
            String json = rootNode.toString();
            String message = String.format("StatsParser.parse [INVALID ROOT NODE]: %s", json.substring(Math.min(100, json.length())));
            StatsConsole.printlnWarn(message);
        }
    }

    private void traverse(final int level, final long timeStamp, final String currentNodeName, final JsonNode currentNode, final BaseEntity parentEntity) {
        JsonNodeType currentNodeType = currentNode.getNodeType();
        if (currentNodeType == JsonNodeType.ARRAY) {
            int index = 0;
            for (final JsonNode childNode : currentNode) {
                final JsonNode transformedNode = parentEntity.transformChildNode(currentNodeName, index, childNode);
                traverse(level, timeStamp, currentNodeName + "[]", transformedNode, parentEntity);
                ++index;
            }
        }
        else if (currentNodeType == JsonNodeType.OBJECT) {
            JsonNode transformedNode = parentEntity.transformChildNode(currentNodeName, -1, currentNode);
            traverseObject(level, timeStamp, currentNodeName, transformedNode, parentEntity);
        }
        else {
            traverseProperty(currentNodeName, currentNodeType, currentNode, parentEntity);
        }
    }

    private void traverseProperty(final String currentNodeName, final JsonNodeType currentNodeType,
                                  final JsonNode currentNode, final BaseEntity parentEntity) {
        boolean r = parentEntity.setProperty(currentNodeName, currentNodeType, currentNode);
        if (!r) {
            String message = String.format("%s %s [UNHANDLED PROPERTY]: %s --- %s --- %s >> %s",
                    parentEntity.getClass().getSimpleName(),
                    parentEntity.getId(),
                    currentNodeName,
                    currentNodeType,
                    currentNode.asText("<empty>"),
                    String.format("case \"%s\": this.%s = node.%s(); break;", currentNodeName, getJavaName(currentNodeName), getJsonNodeFunc(currentNode, currentNodeType)));
            StatsConsole.printlnError(message);
            listeners.forEach(listener -> listener.onParserError(StatsParserListener.UNHANDLED_PROPERTY, message));
        }
    }

    private String getJavaName(String name) {
        return WordUtils.uncapitalize(
                WordUtils.capitalize(name.replace(".", " ").replace("_", " "))
                        .replace(" ", ""));
    }
    private String getJsonNodeFunc(JsonNode node, JsonNodeType nodeType) {
        switch (nodeType) {
            case NUMBER: return node.asLong() < 1000 ? "asInt" : "asLong";
            case BOOLEAN: return "asBoolean";
            case STRING: return "asText";
            default: return "asXXX";
        }
    }

    private void traverseObject(final int level, final long timeStamp, final String currentNodeName, final JsonNode currentNode, final BaseEntity parentEntity) {
        BaseEntity childEntity = tryCreateChildEntity(timeStamp, currentNodeName, currentNode, parentEntity);
        if (childEntity == null)
            childEntity = parentEntity.tryCreateChildEntity(timeStamp, currentNodeName, currentNode);

        if (childEntity != null) {
            parentEntity.getRoot().addChildEntity(level, childEntity);
            if (!parentEntity.setEntity(currentNodeName, childEntity)) {
                String message = String.format("%s [UNHANDLED CHILD ENTITY]: '%s' --- %s %s",
                        parentEntity.getClass().getSimpleName(),
                        currentNodeName,
                        childEntity.getClass().getSimpleName(),
                        childEntity.getId());
                StatsConsole.printlnError(message);
                listeners.forEach(listener -> listener.onParserError(StatsParserListener.UNHANDLED_CHILD_ENTITY, message));
            }
        }

        for (Iterator<Map.Entry<String, JsonNode>> it = currentNode.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> childEntry = it.next();
            String childName = childEntry.getKey();
            JsonNode childNode = childEntry.getValue();
            traverse(level + 1, timeStamp,
                    childEntity != null ? childName : (currentNodeName.length() > 0 ? currentNodeName + "." : "") + childName,
                    childNode,
                    childEntity != null ? childEntity : parentEntity);
        }
    }

    private BaseEntity tryCreateChildEntity(final long timeStamp, final String currentNodeName, final JsonNode currentNode, final BaseEntity parentEntity) {
        if (currentNode.has("_doc")) {
            String docType = currentNode.get("_doc").asText();
            if (currentNode.has("_id")) {
                long auxEntityId = getAuxEntityId(currentNodeName);
                long childEntityId = currentNode.get("_id").asLong();
                final BaseEntity childEntity = createEntity(parentEntity, docType, childEntityId, timeStamp, currentNode);
                if (childEntity != null && !childEntity.handleAuxId(auxEntityId)) {
                    String message = String.format("%s [UNHANDLED AUX ID]: '%s' --- id=%s, aux=%s",
                            childEntity.getClass().getSimpleName(),
                            currentNodeName,
                            childEntity.getId(),
                            auxEntityId);
                    StatsConsole.printlnError(message);
                    listeners.forEach(listener -> listener.onParserError(StatsParserListener.UNHANDLED_AUX_ID, message));
                }
                return childEntity;
            }
        }
        return null;
    }

    private long getAuxEntityId(final String nodeName) {
        Pattern regEx = Pattern.compile("[^0-9]*(\\d+)[^0-9]*");
        Matcher matcher = regEx.matcher(nodeName);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return 0;
    }

    public BaseRootEntity createRootEntity(final String name, final long timeStamp, final JsonNode currentNode, final String queryUrl) {
        BaseRootEntity entity;
        switch (name) {
            case "config_tree": entity = new NullQuietRootEntity(timeStamp); break;
            case "config_sports": entity = null; break;
            case "match_timeline":
            case "match_timelinedelta":
                entity = new MatchTimeline(timeStamp); break;
            case "match_detailsextended": entity = new MatchDetailsExtended(timeStamp); break;
            case "match_info": entity = new MatchInfo(timeStamp); break;
            case "match_bookmakerodds": entity = null; break;
            case "stats_match_form": entity = null; break;
            case "match_funfacts":
                entity = new MatchFunFacts(timeStamp, currentNode.get("_id").asLong()); break;
            case "stats_match_get": entity = new StatsMatchGet(timeStamp); break;
            case "stats_match_situation": entity = new StatsMatchSituation(timeStamp); break;

            case "stats_formtable":
                entity = new StatsFormTable(timeStamp, currentNode.get("season").get("_id").asLong());
                break;
            case "stats_season_meta":
                entity = new StatsSeasonMeta(Long.parseLong(queryUrl.split("/")[1]), timeStamp);
                break;
            case "stats_season_teams2": entity = new StatsSeasonTeams2(timeStamp); break;
            case "stats_season_lastx": entity = new StatsSeasonLastX(timeStamp); break;
            case "stats_season_nextx": entity = new StatsSeasonNextX(timeStamp); break;
            case "stats_season_tables": entity = new StatsSeasonTables(timeStamp); break;
            case "stats_season_overunder": entity = new StatsSeasonOverUnder(timeStamp); break;
            case "stats_season_teampositionhistory": entity = new StatsSeasonTeamPositionHistory(timeStamp); break;
            case "stats_season_topgoals": entity = new StatsSeasonTopGoals(timeStamp); break;
            case "stats_season_topassists": entity = new StatsSeasonTopAssists(timeStamp); break;
            case "stats_season_topcards": entity = new StatsSeasonTopCards(timeStamp); break;
            case "stats_season_injuries":
                entity = new StatsSeasonInjuries(Long.parseLong(queryUrl.split("/")[1]), timeStamp);
                break;
            case "stats_season_leaguesummary":
                entity = new StatsSeasonLeagueSummary(Long.parseLong(queryUrl.split("/")[1]), timeStamp);
                break;
            case "stats_season_goals": entity = new StatsSeasonGoals(timeStamp); break;
            case "stats_season_uniqueteamstats": entity = new StatsSeasonUniqueTeamStats(timeStamp); break;
            case "stats_season_odds": entity = new StatsSeasonOdds(timeStamp); break;
            case "stats_season_fixtures": entity = new StatsSeasonFixtures(timeStamp); break;

            case "stats_team_tournaments": entity = null; break;
            case "stats_team_odds_client": entity = new StatsTeamOddsClient(timeStamp); break;
            case "stats_team_info": entity = new StatsTeamInfo(timeStamp); break;
            case "stats_team_lastx": entity = new StatsTeamLastX(timeStamp); break;
            case "stats_team_nextx": entity = new StatsTeamNextX(timeStamp); break;
            case "stats_team_squad": entity = new StatsTeamSquad(timeStamp); break;
            case "stats_teamplayer_facts": entity = new StatsTeamPlayerFacts(timeStamp); break;
            case "stats_team_versusrecent":
            case "stats_team_versus":
                entity = new StatsTeamVersus(timeStamp); break;
            default:
                String message = "StatsParser.createRootEntity [UNKNOWN ROOT TYPE]: " + name;
                StatsConsole.printlnError(message);
                listeners.forEach(listener -> listener.onParserError(StatsParserListener.UNKNOWN_ROOT_TYPE, message));
                entity = new NullRootEntity(timeStamp);
        }
        return entity;
    }

    public BaseEntity createEntity(final BaseEntity parent, final String docType, final long id, final long timeStamp, final JsonNode currentNode) {
        BaseEntity entity;
        switch (docType) {
            case "season": entity = new SeasonEntity(parent, id); break;
            case "match": entity = new MatchEntity(parent, id); break;
            case "player":
            case "extendedplayer":
                entity = new PlayerEntity(parent, id); break;
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
                entity = new CountryEntity(parent, id); break;
            case "stadium":
                entity = new StadiumEntity(parent, id); break;
            case "uniquetournament": entity = new UniqueTournamentEntity(parent, id); break;
            case "tournament": entity = new TournamentEntity(parent, id); break;
            case "roundname": entity = new RoundNameEntity(parent, id); break;
            case "tableround": entity = new TableRoundEntity(parent, id); break;
            case "cupround": entity = new CupRoundEntity(parent, id); break;
            case "bookmaker": entity = new BookmakerEntity(parent, id); break;
            case "uniqueteamform": entity = null; /*new UniqueTeamFormEntity(parent, id);*/ break;
            case "statistics_table":
            case "statistics_leaguetable":
                entity = new LeagueTableEntity(parent, id); break;
            case "tiebreakrule": entity = new TieBreakRuleEntity(parent, id); break;
            case "tablerow": entity = null; break;
//            case "tablerow":
//                System.out.println(currentNode.findParent("tables"));
//                entity = new TableRowEntity(parent, tableId, id, timeStamp);
//                break;
            case "promotion": entity = new PromotionEntity(parent, id); break;
            case "matchtype": entity = new MatchTypeEntity(parent, id); break;
            case "tabletype": entity = new TableTypeEntity(parent, id); break;
            case "seasonpos": entity = null;/*new SeasonPosEntity(parent, id);*/ break;
            case "player_position_type": entity = new PlayerPositionTypeEntity(parent, id); break;
            case "playerstatus": entity = new PlayerStatusEntity(parent, id); break;
            case "status": entity = new MatchStatusEntity(parent, id); break;
            case "event": entity = new MatchEventEntity(parent, id); break;
            case "match_situation_entry": entity = /*new MatchSituationEntryEntity(parent, id)*/null; break;
            case "playerrole": entity = new UniqueTeamPlayerEntity(parent,
                    currentNode.get("team").get("_id").asLong(),
                    currentNode.get("_playerid").asLong()
                    );
                break;

            case "match_details_entry": entity = /*new MatchDetailsEntryEntity(parent, id, timeStamp)*/null; break;
            //case "odds": entity = new OddsEntity(parent, id, timeStamp); break;
            case "team_form_table": entity = /*new TeamFormTableEntity(parent, id, timeStamp)*/null; break;
            case "team_form_entry": entity = /*new TeamFormEntryEntity(parent, id, timeStamp)*/null; break;
            case "toplistentry":
                BaseRootEntityType rootType = parent.getRoot().getType();
                if (rootType.equals(BaseRootEntityType.StatsTeamPlayerFacts)) {
                    long teamId = Long.parseLong(currentNode.get("teams").fields().next().getKey());
                    entity = new UniqueTeamPlayerEntity(parent, teamId, id);
                }
                else if (rootType.equals(BaseRootEntityType.StatsSeasonTopAssists) || rootType.equals(BaseRootEntityType.StatsSeasonTopCards) || rootType.equals(BaseRootEntityType.StatsSeasonTopGoals)) {
                    long teamId = Long.parseLong(currentNode.get("teams").fields().next().getKey());
                    long playerId = currentNode.get("playerid").asLong();
                    entity = new TopListEntryEntity(parent, teamId, playerId, rootType.getId(), timeStamp);
                }
                else entity = null;
                break;
            case "team_goal_stats": entity = null;/*new TeamGoalStatsEntity(parent, id, timeStamp);*/ break;
            case "unique_team_stats": entity = null /*new UniqueTeamStatsEntity(parent, id, timeStamp)*/; break;
            case "match_funfact": entity = new MatchFunFactEntity(parent, id); break;
            //case "season_over_under": entity = new SeasonOverUnderEntity(parent, id, timeStamp); break;
            //case "team_over_under": entity = new TeamOverUnderEntity(parent, id, timeStamp); break;
            case "team_player_top_list_entry": entity = new TeamPlayerTopListEntryEntity(parent, id, timeStamp); break;

            default:
                String message = "StatsParser.createEntity [UNKNOWN ENTITY TYPE]: " + docType;
                StatsConsole.printlnError(message);
                listeners.forEach(listener -> listener.onParserError(StatsParserListener.UNKNOWN_ENTITY_TYPE, message));
                entity = new NullEntity(parent);
        }
        return entity;
    }

    public void addListener(StatsParserListener listener) {
        this.listeners.add(listener);
    }
}
