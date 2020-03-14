package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityIdList;

import java.util.ArrayList;
import java.util.List;

public class TeamFormTableEntity extends BaseTimeEntity {
    private Integer positionTotal, positionHome, positionAway, playedTotal, playedTotalHome, playedTotalAway, playedHome, playedAway;
    private Integer winTotal, winTotalHome, winTotalAway, winHome, winAway;
    private Integer drawTotal, drawTotalHome, drawTotalAway, drawHome, drawAway;
    private Integer lossTotal, lossTotalHome, lossTotalAway, lossHome, lossAway;
    private Integer goalsForTotal, goalsForTotalHome, goalsForTotalAway, goalsForHome, goalsForAway;
    private Integer goalsAgainstTotal, goalsAgainstTotalHome, goalsAgainstTotalAway, goalsAgainstHome, goalsAgainstAway;
    private Integer goalDiffTotal, goalDiffTotalHome, goalDiffTotalAway, goalDiffHome, goalDiffAway;
    private Integer pointsTotal, pointsTotalHome, pointsTotalAway, pointsHome, pointsAway;
    private EntityId nextOpponentTeamId;
    private Long nextOpponentTime;
    private Integer nextOpponentMatchDifficultyRatingHome, nextOpponentMatchDifficultyRatingAway;
    private EntityIdList formEntries = new EntityIdList();

    public TeamFormTableEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, id, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("nextopponent.team")) {
            this.nextOpponentTeamId = childEntity.getId();
            return true;
        } else if (entityName.startsWith("form.")) {
            this.formEntries.add(childEntity.getId());
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public JsonNode transformChildNode(final String currentNodeName, final int index, final JsonNode childNode) {
        if (currentNodeName.equals("form.total") || currentNodeName.equals("form.home") || currentNodeName.equals("form.away")) {
            int groupId = currentNodeName.equals("form.total") ? 1 : (currentNodeName.equals("form.home") ? 2 : 3);
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_id", Long.parseLong(String.format("%015d%d%02d", this.getRawId(), groupId, index)));
            objNode.put("_doc", "team_form_entry");
            objNode.put("_index", index);
            objNode.put("group", currentNodeName.substring(5));
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "position.total": this.positionTotal = node.asInt(); break;
            case "position.home": this.positionHome = node.asInt(); break;
            case "position.away": this.positionAway = node.asInt(); break;
            case "played.total": this.playedTotal = node.asInt(); break;
            case "played.totalhome": this.playedTotalHome = node.asInt(); break;
            case "played.totalaway": this.playedTotalAway = node.asInt(); break;
            case "played.home": this.playedHome = node.asInt(); break;
            case "played.away": this.playedAway = node.asInt(); break;
            case "win.total": this.winTotal = node.asInt(); break;
            case "win.totalhome": this.winTotalHome = node.asInt(); break;
            case "win.totalaway": this.winTotalAway = node.asInt(); break;
            case "win.home": this.winHome = node.asInt(); break;
            case "win.away": this.winAway = node.asInt(); break;
            case "draw.total": this.drawTotal = node.asInt(); break;
            case "draw.totalhome": this.drawTotalHome = node.asInt(); break;
            case "draw.totalaway": this.drawTotalAway = node.asInt(); break;
            case "draw.home": this.drawHome = node.asInt(); break;
            case "draw.away": this.drawAway = node.asInt(); break;
            case "loss.total": this.lossTotal = node.asInt(); break;
            case "loss.totalhome": this.lossTotalHome = node.asInt(); break;
            case "loss.totalaway": this.lossTotalAway = node.asInt(); break;
            case "loss.home": this.lossHome = node.asInt(); break;
            case "loss.away": this.lossAway = node.asInt(); break;
            case "goalsfor.total": this.goalsForTotal = node.asInt(); break;
            case "goalsfor.totalhome": this.goalsForTotalHome = node.asInt(); break;
            case "goalsfor.totalaway": this.goalsForTotalAway = node.asInt(); break;
            case "goalsfor.home": this.goalsForHome = node.asInt(); break;
            case "goalsfor.away": this.goalsForAway = node.asInt(); break;
            case "goalsagainst.total": this.goalsAgainstTotal = node.asInt(); break;
            case "goalsagainst.totalhome": this.goalsAgainstTotalHome = node.asInt(); break;
            case "goalsagainst.totalaway": this.goalsAgainstTotalAway = node.asInt(); break;
            case "goalsagainst.home": this.goalsAgainstHome = node.asInt(); break;
            case "goalsagainst.away": this.goalsAgainstAway = node.asInt(); break;
            case "goaldifference.total": this.goalDiffTotal = node.asInt(); break;
            case "goaldifference.totalhome": this.goalDiffTotalHome = node.asInt(); break;
            case "goaldifference.totalaway": this.goalDiffTotalAway = node.asInt(); break;
            case "goaldifference.home": this.goalDiffHome = node.asInt(); break;
            case "goaldifference.away": this.goalDiffAway = node.asInt(); break;
            case "points.total": this.pointsTotal = node.asInt(); break;
            case "points.totalhome": this.pointsTotalHome = node.asInt(); break;
            case "points.totalaway": this.pointsTotalAway = node.asInt(); break;
            case "points.home": this.pointsHome = node.asInt(); break;
            case "points.away": this.pointsAway = node.asInt(); break;
            case "nextopponent.date.uts": this.nextOpponentTime = node.asLong(); break;
            case "nextopponent.matchdifficultyrating.home": this.nextOpponentMatchDifficultyRatingHome = node.asInt(); break;
            case "nextopponent.matchdifficultyrating.away": this.nextOpponentMatchDifficultyRatingAway = node.asInt(); break;

            case "nextopponent.date._doc":
            case "nextopponent.date.time":
            case "nextopponent.date.date":
            case "nextopponent.date.tz":
            case "nextopponent.date.tzoffset":
                return true;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamFormTableEntity{");
        sb.append("id=").append(getId());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", positionTotal=").append(positionTotal);
        sb.append(", positionHome=").append(positionHome);
        sb.append(", positionAway=").append(positionAway);
        sb.append(", playedTotal=").append(playedTotal);
        sb.append(", playedTotalHome=").append(playedTotalHome);
        sb.append(", playedTotalAway=").append(playedTotalAway);
        sb.append(", playedHome=").append(playedHome);
        sb.append(", playedAway=").append(playedAway);
        sb.append(", winTotal=").append(winTotal);
        sb.append(", winTotalHome=").append(winTotalHome);
        sb.append(", winTotalAway=").append(winTotalAway);
        sb.append(", winHome=").append(winHome);
        sb.append(", winAway=").append(winAway);
        sb.append(", drawTotal=").append(drawTotal);
        sb.append(", drawTotalHome=").append(drawTotalHome);
        sb.append(", drawTotalAway=").append(drawTotalAway);
        sb.append(", drawHome=").append(drawHome);
        sb.append(", drawAway=").append(drawAway);
        sb.append(", lossTotal=").append(lossTotal);
        sb.append(", lossTotalHome=").append(lossTotalHome);
        sb.append(", lossTotalAway=").append(lossTotalAway);
        sb.append(", lossHome=").append(lossHome);
        sb.append(", lossAway=").append(lossAway);
        sb.append(", goalsForTotal=").append(goalsForTotal);
        sb.append(", goalsForTotalHome=").append(goalsForTotalHome);
        sb.append(", goalsForTotalAway=").append(goalsForTotalAway);
        sb.append(", goalsForHome=").append(goalsForHome);
        sb.append(", goalsForAway=").append(goalsForAway);
        sb.append(", goalsAgainstTotal=").append(goalsAgainstTotal);
        sb.append(", goalsAgainstTotalHome=").append(goalsAgainstTotalHome);
        sb.append(", goalsAgainstTotalAway=").append(goalsAgainstTotalAway);
        sb.append(", goalsAgainstHome=").append(goalsAgainstHome);
        sb.append(", goalsAgainstAway=").append(goalsAgainstAway);
        sb.append(", goalDiffTotal=").append(goalDiffTotal);
        sb.append(", goalDiffTotalHome=").append(goalDiffTotalHome);
        sb.append(", goalDiffTotalAway=").append(goalDiffTotalAway);
        sb.append(", goalDiffHome=").append(goalDiffHome);
        sb.append(", goalDiffAway=").append(goalDiffAway);
        sb.append(", pointsTotal=").append(pointsTotal);
        sb.append(", pointsTotalHome=").append(pointsTotalHome);
        sb.append(", pointsTotalAway=").append(pointsTotalAway);
        sb.append(", pointsHome=").append(pointsHome);
        sb.append(", pointsAway=").append(pointsAway);
        sb.append(", nextOpponentTeamId=").append(nextOpponentTeamId);
        sb.append(", nextOpponentTime=").append(nextOpponentTime);
        sb.append(", nextOpponentMatchDifficultyRatingHome=").append(nextOpponentMatchDifficultyRatingHome);
        sb.append(", nextOpponentMatchDifficultyRatingAway=").append(nextOpponentMatchDifficultyRatingAway);
        sb.append(", formEntries=").append(formEntries);
        sb.append('}');
        return sb.toString();
    }
}
