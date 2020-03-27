package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

public class TopListEntryEntity extends BaseEntity {
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
    private Integer totalYellowCards;
    private Integer totalYellowRedCards;
    private Integer totalRedCards;
    private Integer totalFirstHalfCards;
    private Integer totalSecondHalfCards;

    public TopListEntryEntity(BaseEntity parent, long teamId, long playerId, int entryType, long timeStamp) {
        super(parent, new EntityId(TopListEntryEntity.class,
                new EntityKey("teamId", teamId),
                new EntityKey("playerId", playerId),
                new EntityKey("entryType", entryType),
                new EntityKey(EntityId.KEY_TIMESTAMP, timeStamp)
        ));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if ("player".equals(entityName)) {
            return true;
        } else {
            if (entityName.startsWith("teams.")) {
                return true;
            }
            return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
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
            case "playerid":
                break;
            default:
                if (nodeName.startsWith("teams."))
                    return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "TopListEntryEntity{" + "id=" + getId() +
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
                ", totalYellowCards=" + totalYellowCards +
                ", totalYellowRedCards=" + totalYellowRedCards +
                ", totalRedCards=" + totalRedCards +
                ", totalFirstHalfCards=" + totalFirstHalfCards +
                ", totalSecondHalfCards=" + totalSecondHalfCards +
                '}';
    }
}
