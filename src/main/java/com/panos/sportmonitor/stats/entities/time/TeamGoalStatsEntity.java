package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;

public class TeamGoalStatsEntity extends BaseTimeEntity {
    private EntityId teamId;
    private Integer matches, scoredSum, scored0015, scored1630, scored3145, scored4660, scored6175, scored7690;
    private Integer concededSum, conceded0015, conceded1630, conceded3145, conceded4660, conceded6175, conceded7690;
    private Integer firstGoal, lastGoal, penaltySuccessCount, penaltyFailCount;

    public TeamGoalStatsEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, new EntityId(id, timeStamp, TeamGoalStatsEntity.class));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if ("team".equals(entityName)) {
            this.teamId = new EntityId(childEntity);
        } else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "scored.0-15": this.scored0015 = node.asInt(); break;
            case "scored.16-30": this.scored1630 = node.asInt(); break;
            case "scored.31-45": this.scored3145 = node.asInt(); break;
            case "scored.46-60": this.scored4660 = node.asInt(); break;
            case "scored.61-75": this.scored6175 = node.asInt(); break;
            case "scored.76-90": this.scored7690 = node.asInt(); break;
            case "scoredsum": this.scoredSum = node.asInt(); break;
            case "conceded.0-15": this.conceded0015 = node.asInt(); break;
            case "conceded.16-30": this.conceded1630 = node.asInt(); break;
            case "conceded.31-45": this.conceded3145 = node.asInt(); break;
            case "conceded.46-60": this.conceded4660 = node.asInt(); break;
            case "conceded.61-75": this.conceded6175 = node.asInt(); break;
            case "conceded.76-90": this.conceded7690 = node.asInt(); break;
            case "concededsum": this.concededSum = node.asInt(); break;
            case "firstgoal": this.firstGoal = node.asInt(); break;
            case "lastgoal": this.lastGoal = node.asInt(); break;
            case "matches": this.matches = node.asInt(); break;
            case "penalty_success_count": this.penaltySuccessCount = node.asInt(0); break;
            case "penalty_fail_count": this.penaltyFailCount = node.asInt(0); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamGoalStatsEntity{" + "id=" + getId() +
                ", teamId=" + teamId +
                ", matches=" + matches +
                ", scoredSum=" + scoredSum +
                ", scored0015=" + scored0015 +
                ", scored1630=" + scored1630 +
                ", scored3145=" + scored3145 +
                ", scored4660=" + scored4660 +
                ", scored6175=" + scored6175 +
                ", scored7690=" + scored7690 +
                ", concededSum=" + concededSum +
                ", conceded0015=" + conceded0015 +
                ", conceded1630=" + conceded1630 +
                ", conceded3145=" + conceded3145 +
                ", conceded4660=" + conceded4660 +
                ", conceded6175=" + conceded6175 +
                ", conceded7690=" + conceded7690 +
                ", firstGoal=" + firstGoal +
                ", lastGoal=" + lastGoal +
                ", penaltySuccessCount=" + penaltySuccessCount +
                ", penaltyFailCount=" + penaltyFailCount +
                '}';
    }
}
