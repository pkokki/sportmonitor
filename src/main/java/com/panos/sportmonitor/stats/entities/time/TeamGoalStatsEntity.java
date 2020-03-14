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
        super(parent, id, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "team": this.teamId = childEntity.getId(); break;
            default: return super.handleChildEntity(entityName, childEntity);
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
        final StringBuilder sb = new StringBuilder("TeamGoalStatsEntity{");
        sb.append("id=").append(getId());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", teamId=").append(teamId);
        sb.append(", matches=").append(matches);
        sb.append(", scoredSum=").append(scoredSum);
        sb.append(", scored0015=").append(scored0015);
        sb.append(", scored1630=").append(scored1630);
        sb.append(", scored3145=").append(scored3145);
        sb.append(", scored4660=").append(scored4660);
        sb.append(", scored6175=").append(scored6175);
        sb.append(", scored7690=").append(scored7690);
        sb.append(", concededSum=").append(concededSum);
        sb.append(", conceded0015=").append(conceded0015);
        sb.append(", conceded1630=").append(conceded1630);
        sb.append(", conceded3145=").append(conceded3145);
        sb.append(", conceded4660=").append(conceded4660);
        sb.append(", conceded6175=").append(conceded6175);
        sb.append(", conceded7690=").append(conceded7690);
        sb.append(", firstGoal=").append(firstGoal);
        sb.append(", lastGoal=").append(lastGoal);
        sb.append(", penaltySuccessCount=").append(penaltySuccessCount);
        sb.append(", penaltyFailCount=").append(penaltyFailCount);
        sb.append('}');
        return sb.toString();
    }
}
