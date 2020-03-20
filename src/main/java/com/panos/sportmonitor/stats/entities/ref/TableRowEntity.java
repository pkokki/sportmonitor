package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class TableRowEntity extends BaseEntity {
    private EntityId promotionId;
    private EntityId teamId;
    private Integer changeTotal, changeHome, changeAway, drawTotal, drawHome, drawAway, goalDiffTotal, goalDiffHome, goalDiffAway;
    private Integer goalsAgainstTotal, goalsAgainstHome, goalsAgainstAway, goalsForTotal, goalsForHome, goalsForAway;
    private Integer lossTotal, lossHome, lossAway, total, home, away, pointsTotal, pointsHome, pointsAway, pos, posHome, posAway;
    private Integer sortPositionTotal, sortPositionHome, sortPositionAway, winTotal, winHome, winAway;
    private Integer pointsGivenTotal, maxPointsTotal, goalsTotal,
            suddenDeathWinTotal, gamePointsForTotal, gamePointsAgainstTotal,
            suddenDeathWinHome, gamePointsForHome, gamePointsAgainstHome, maxPointsHome,
            suddenDeathWinAway, gamePointsForAway, gamePointsAgainstAway, maxPointsAway,
            lastTenGamesWin, lastTenGamesLoss, lastTenGamesAllLoss,
            streak, streakLoss,
            currentlyPlaying, fullTimeWinTotal, fullTimeDrawTotal, fullTimeLossTotal,
            fullTimeWinHome, fullTimeDrawHome, fullTimeLossHome, fullTimeWinAway, fullTimeDrawAway, fullTimeLossAway, lastTenGamesDraw;
    private Double pctTotal, pctGoalsTotal, pctGamePointsTotal,
            pctHome, pctGoalsHome, pctGamePointsHome,
            pctAway, pctGoalsAway, pctGamePointsAway;

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
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("promotion")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("code", childNode.get("_id").asInt());
            objNode.put("_id", this.getRoot().getNext());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
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
            case "total": this.total = node.asInt(); break;
            case "home": this.home = node.asInt(); break;
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

            case "pointsGivenTotal": this.pointsGivenTotal = node.asInt(); break;
            case "maxPointsTotal": this.maxPointsTotal = node.asInt(); break;
            case "goalsTotal": this.goalsTotal = node.asInt(); break;
            case "pctTotal": this.pctTotal = node.asDouble(); break;
            case "pctGoalsTotal": this.pctGoalsTotal = node.asDouble(); break;
            case "pctGamePointsTotal": this.pctGamePointsTotal = node.asDouble(); break;
            case "suddenDeathWinTotal": this.suddenDeathWinTotal = node.asInt(); break;
            case "gamePointsForTotal": this.gamePointsForTotal = node.asInt(); break;
            case "gamePointsAgainstTotal": this.gamePointsAgainstTotal = node.asInt(); break;
            case "pctHome": this.pctHome = node.asDouble(); break;
            case "pctGoalsHome": this.pctGoalsHome = node.asDouble(); break;
            case "pctGamePointsHome": this.pctGamePointsHome = node.asDouble(); break;
            case "suddenDeathWinHome": this.suddenDeathWinHome = node.asInt(); break;
            case "gamePointsForHome": this.gamePointsForHome = node.asInt(); break;
            case "gamePointsAgainstHome": this.gamePointsAgainstHome = node.asInt(); break;
            case "maxPointsHome": this.maxPointsHome = node.asInt(); break;
            case "pctAway": this.pctAway = node.asDouble(); break;
            case "pctGoalsAway": this.pctGoalsAway = node.asDouble(); break;
            case "pctGamePointsAway": this.pctGamePointsAway = node.asDouble(); break;
            case "suddenDeathWinAway": this.suddenDeathWinAway = node.asInt(); break;
            case "gamePointsForAway": this.gamePointsForAway = node.asInt(); break;
            case "gamePointsAgainstAway": this.gamePointsAgainstAway = node.asInt(); break;
            case "maxPointsAway": this.maxPointsAway = node.asInt(); break;
            case "lastTenGamesWin": this.lastTenGamesWin = node.asInt(); break;
            case "lastTenGamesLoss": this.lastTenGamesLoss = node.asInt(); break;
            case "lastTenGamesAllLoss": this.lastTenGamesAllLoss = node.asInt(); break;
            case "streakLoss": this.streakLoss = node.asInt(); break;
            case "streak": this.streak = node.asInt(); break;
            case "currentlyPlaying": this.currentlyPlaying = node.asInt(); break;
            case "fullTimeWinTotal": this.fullTimeWinTotal = node.asInt(); break;
            case "fullTimeDrawTotal": this.fullTimeDrawTotal = node.asInt(); break;
            case "fullTimeLossTotal": this.fullTimeLossTotal = node.asInt(); break;
            case "fullTimeWinHome": this.fullTimeWinHome = node.asInt(); break;
            case "fullTimeDrawHome": this.fullTimeDrawHome = node.asInt(); break;
            case "fullTimeLossHome": this.fullTimeLossHome = node.asInt(); break;
            case "fullTimeWinAway": this.fullTimeWinAway = node.asInt(); break;
            case "fullTimeDrawAway": this.fullTimeDrawAway = node.asInt(); break;
            case "fullTimeLossAway": this.fullTimeLossAway = node.asInt(); break;
            case "lastTenGamesDraw": this.lastTenGamesDraw = node.asInt(); break;

            case "streakText.text":
            case "streakText.value":
                break;
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
        sb.append(", pointsGivenTotal=").append(pointsGivenTotal);
        sb.append(", maxPointsTotal=").append(maxPointsTotal);
        sb.append(", goalsTotal=").append(goalsTotal);
        sb.append(", suddenDeathWinTotal=").append(suddenDeathWinTotal);
        sb.append(", gamePointsForTotal=").append(gamePointsForTotal);
        sb.append(", gamePointsAgainstTotal=").append(gamePointsAgainstTotal);
        sb.append(", suddenDeathWinHome=").append(suddenDeathWinHome);
        sb.append(", gamePointsForHome=").append(gamePointsForHome);
        sb.append(", gamePointsAgainstHome=").append(gamePointsAgainstHome);
        sb.append(", maxPointsHome=").append(maxPointsHome);
        sb.append(", suddenDeathWinAway=").append(suddenDeathWinAway);
        sb.append(", gamePointsForAway=").append(gamePointsForAway);
        sb.append(", gamePointsAgainstAway=").append(gamePointsAgainstAway);
        sb.append(", maxPointsAway=").append(maxPointsAway);
        sb.append(", lastTenGamesWin=").append(lastTenGamesWin);
        sb.append(", lastTenGamesLoss=").append(lastTenGamesLoss);
        sb.append(", lastTenGamesAllLoss=").append(lastTenGamesAllLoss);
        sb.append(", streak=").append(streak);
        sb.append(", streakLoss=").append(streakLoss);
        sb.append(", currentlyPlaying=").append(currentlyPlaying);
        sb.append(", fullTimeWinTotal=").append(fullTimeWinTotal);
        sb.append(", fullTimeDrawTotal=").append(fullTimeDrawTotal);
        sb.append(", fullTimeLossTotal=").append(fullTimeLossTotal);
        sb.append(", fullTimeWinHome=").append(fullTimeWinHome);
        sb.append(", fullTimeDrawHome=").append(fullTimeDrawHome);
        sb.append(", fullTimeLossHome=").append(fullTimeLossHome);
        sb.append(", fullTimeWinAway=").append(fullTimeWinAway);
        sb.append(", fullTimeDrawAway=").append(fullTimeDrawAway);
        sb.append(", fullTimeLossAway=").append(fullTimeLossAway);
        sb.append(", lastTenGamesDraw=").append(lastTenGamesDraw);
        sb.append(", pctTotal=").append(pctTotal);
        sb.append(", pctGoalsTotal=").append(pctGoalsTotal);
        sb.append(", pctGamePointsTotal=").append(pctGamePointsTotal);
        sb.append(", pctHome=").append(pctHome);
        sb.append(", pctGoalsHome=").append(pctGoalsHome);
        sb.append(", pctGamePointsHome=").append(pctGamePointsHome);
        sb.append(", pctAway=").append(pctAway);
        sb.append(", pctGoalsAway=").append(pctGoalsAway);
        sb.append(", pctGamePointsAway=").append(pctGamePointsAway);
        sb.append('}');
        return sb.toString();
    }
}
