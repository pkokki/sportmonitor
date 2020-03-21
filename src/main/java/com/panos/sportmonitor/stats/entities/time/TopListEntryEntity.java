package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.PlayerEntity;

public class TopListEntryEntity extends BaseTimeEntity {
    private EntityId playerId;
    private Integer totalGoals, totalAssists;
    private Integer totalMatches;
    private Integer totalPenalties;
    private Integer goalPoints;
    private Integer minutesPlayed;
    private Integer substitutedIn;
    private Integer firstGoals;
    private Integer lastGoals;
    private Integer homeGoals;
    private Integer awayGoals;
    private Integer firstHalfGoals;
    private Integer secondHalfGoals;
    private EntityIdList teamsEntries = new EntityIdList();
    private Integer totalYellowCards;
    private Integer totalYellowRedCards;
    private Integer totalRedCards;
    private Integer totalFirstHalfCards;
    private Integer totalSecondHalfCards;

    public TopListEntryEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, new EntityId(id, timeStamp, TopListEntryEntity.class));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if ("player".equals(entityName)) {
            this.playerId = new EntityId(childEntity);
        } else {
            if (entityName.startsWith("teams.")) {
                this.teamsEntries.add(childEntity.getId());
                return true;
            }
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.startsWith("teams.")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "team_player_top_list_entry");
            objNode.put("_id", this.getRoot().getNext());
            objNode.put("playerid", playerId.getId());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "playerid": this.playerId = new EntityId(node.asLong(), PlayerEntity.class); break;
            case "total.goals": this.totalGoals = node.asInt(); break;
            case "total.assists": this.totalAssists = node.asInt(); break;
            case "total.matches": this.totalMatches = node.asInt(); break;
            case "total.penalties": this.totalPenalties = node.asInt(); break;
            case "total.goal_points": this.goalPoints = node.asInt(); break;
            case "total.minutes_played": this.minutesPlayed = node.asInt(); break;
            case "total.substituted_in": this.substitutedIn = node.asInt(); break;
            case "total.first_goals": this.firstGoals = node.asInt(); break;
            case "total.last_goals": this.lastGoals = node.asInt(); break;
            case "home.goals": this.homeGoals = node.asInt(); break;
            case "away.goals": this.awayGoals = node.asInt(); break;
            case "firsthalf.goals": this.firstHalfGoals = node.asInt(); break;
            case "secondhalf.goals": this.secondHalfGoals = node.asInt(); break;
            case "total.yellow_cards": this.totalYellowCards = node.asInt(); break;
            case "total.yellowred_cards": this.totalYellowRedCards = node.asInt(); break;
            case "total.red_cards": this.totalRedCards = node.asInt(); break;
            case "total.number_of_cards_1st_half": this.totalFirstHalfCards = node.asInt(); break;
            case "total.number_of_cards_2nd_half": this.totalSecondHalfCards = node.asInt(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "TopListEntryEntity{" + "id=" + getId() +
                ", playerId=" + playerId +
                ", totalGoals=" + totalGoals +
                ", totalAssists=" + totalAssists +
                ", totalMatches=" + totalMatches +
                ", totalPenalties=" + totalPenalties +
                ", goalPoints=" + goalPoints +
                ", minutesPlayed=" + minutesPlayed +
                ", substitutedIn=" + substitutedIn +
                ", firstGoals=" + firstGoals +
                ", lastGoals=" + lastGoals +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", firstHalfGoals=" + firstHalfGoals +
                ", secondHalfGoals=" + secondHalfGoals +
                ", teamsEntries=" + teamsEntries +
                ", totalYellowCards=" + totalYellowCards +
                ", totalYellowRedCards=" + totalYellowRedCards +
                ", totalRedCards=" + totalRedCards +
                ", totalFirstHalfCards=" + totalFirstHalfCards +
                ", totalSecondHalfCards=" + totalSecondHalfCards +
                '}';
    }
}
