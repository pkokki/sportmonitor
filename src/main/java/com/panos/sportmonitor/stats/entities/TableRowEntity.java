package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class TableRowEntity extends BaseEntity {
    private Long promotionId;
    private Long teamId;
    private Integer changeTotal, changeHome, changeAway, drawTotal, drawHome, drawAway, goalDiffTotal, goalDiffHome, goalDiffAway;
    private Integer goalsAgainstTotal, goalsAgainstHome, goalsAgainstAway, goalsForTotal, goalsForHome, goalsForAway;
    private Integer lossTotal, lossHome, lossAway, total, home, away, pointsTotal, pointsHome, pointsAway, pos, posHome, posAway;
    private Integer sortPositionTotal, sortPositionHome, sortPositionAway, winTotal, winHome, winAway;

    public TableRowEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "promotion": this.promotionId = childEntity.getId(); return true;
            case "team": this.teamId = childEntity.getId(); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "changeTotal": this.changeTotal = node.asInt(); break;
            case "changeHome": this.changeHome = node.asInt(); break;
            case "changeAway": this.changeAway = node.asInt(); break;
            case "drawTotal": this.drawTotal = node.asInt(); break;
            case "drawHome": this.drawHome = node.asInt(); break;
            case "drawAway": this.drawAway = node.asInt(); break;
            case "goalDiffTotal": this.goalDiffTotal = node.asInt(); break;
            case "goalDiffHome": this.goalDiffHome = node.asInt(); break;
            case "goalDiffAway": this.goalDiffAway = node.asInt(); break;
            case "goalsAgainstTotal": this.goalsAgainstTotal = node.asInt(); break;
            case "goalsAgainstHome": this.goalsAgainstHome = node.asInt(); break;
            case "goalsAgainstAway": this.goalsAgainstAway = node.asInt(); break;
            case "goalsForTotal": this.goalsForTotal = node.asInt(); break;
            case "goalsForHome": this.goalsForHome = node.asInt(); break;
            case "goalsForAway": this.goalsForAway = node.asInt(); break;
            case "lossTotal": this.lossTotal = node.asInt(); break;
            case "lossHome": this.lossHome = node.asInt(); break;
            case "lossAway": this.lossAway = node.asInt(); break;
            case "total": this.goalsForHome = node.asInt(); break;
            case "home": this.goalsForHome = node.asInt(); break;
            case "away": this.away = node.asInt(); break;
            case "pointsTotal": this.pointsTotal = node.asInt(); break;
            case "pointsHome": this.pointsHome = node.asInt(); break;
            case "pointsAway": this.pointsAway = node.asInt(); break;
            case "pos": this.pos = node.asInt(); break;
            case "posHome": this.posHome = node.asInt(); break;
            case "posAway": this.posAway = node.asInt(); break;
            case "sortPositionTotal": this.sortPositionTotal = node.asInt(); break;
            case "sortPositionHome": this.sortPositionHome = node.asInt(); break;
            case "sortPositionAway": this.sortPositionAway = node.asInt(); break;
            case "winTotal": this.winTotal = node.asInt(); break;
            case "winHome": this.winHome = node.asInt(); break;
            case "winAway": this.winAway = node.asInt(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableRowEntity{");
        sb.append("id=").append(getId());
        sb.append(", promotionId=").append(promotionId);
        sb.append(", teamId=").append(teamId);
        sb.append(", changeTotal=").append(changeTotal);
        sb.append(", changeHome=").append(changeHome);
        sb.append(", changeAway=").append(changeAway);
        sb.append(", drawTotal=").append(drawTotal);
        sb.append(", drawHome=").append(drawHome);
        sb.append(", drawAway=").append(drawAway);
        sb.append(", goalDiffTotal=").append(goalDiffTotal);
        sb.append(", goalDiffHome=").append(goalDiffHome);
        sb.append(", goalDiffAway=").append(goalDiffAway);
        sb.append(", goalsAgainstTotal=").append(goalsAgainstTotal);
        sb.append(", goalsAgainstHome=").append(goalsAgainstHome);
        sb.append(", goalsAgainstAway=").append(goalsAgainstAway);
        sb.append(", goalsForTotal=").append(goalsForTotal);
        sb.append(", goalsForHome=").append(goalsForHome);
        sb.append(", goalsForAway=").append(goalsForAway);
        sb.append(", lossTotal=").append(lossTotal);
        sb.append(", lossHome=").append(lossHome);
        sb.append(", lossAway=").append(lossAway);
        sb.append(", total=").append(total);
        sb.append(", home=").append(home);
        sb.append(", away=").append(away);
        sb.append(", pointsTotal=").append(pointsTotal);
        sb.append(", pointsHome=").append(pointsHome);
        sb.append(", pointsAway=").append(pointsAway);
        sb.append(", pos=").append(pos);
        sb.append(", posHome=").append(posHome);
        sb.append(", posAway=").append(posAway);
        sb.append(", sortPositionTotal=").append(sortPositionTotal);
        sb.append(", sortPositionHome=").append(sortPositionHome);
        sb.append(", sortPositionAway=").append(sortPositionAway);
        sb.append(", winTotal=").append(winTotal);
        sb.append(", winHome=").append(winHome);
        sb.append(", winAway=").append(winAway);
        sb.append('}');
        return sb.toString();
    }
}
