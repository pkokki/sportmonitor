package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;

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
        super(parent, new EntityId(TeamFormTableEntity.class, id, timeStamp));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("nextopponent.team")) {
            this.nextOpponentTeamId = new EntityId(childEntity);
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
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_id", this.getRoot().getNext());
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
        return "TeamFormTableEntity{" + "id=" + getId() +
                ", positionTotal=" + positionTotal +
                ", positionHome=" + positionHome +
                ", positionAway=" + positionAway +
                ", playedTotal=" + playedTotal +
                ", playedTotalHome=" + playedTotalHome +
                ", playedTotalAway=" + playedTotalAway +
                ", playedHome=" + playedHome +
                ", playedAway=" + playedAway +
                ", winTotal=" + winTotal +
                ", winTotalHome=" + winTotalHome +
                ", winTotalAway=" + winTotalAway +
                ", winHome=" + winHome +
                ", winAway=" + winAway +
                ", drawTotal=" + drawTotal +
                ", drawTotalHome=" + drawTotalHome +
                ", drawTotalAway=" + drawTotalAway +
                ", drawHome=" + drawHome +
                ", drawAway=" + drawAway +
                ", lossTotal=" + lossTotal +
                ", lossTotalHome=" + lossTotalHome +
                ", lossTotalAway=" + lossTotalAway +
                ", lossHome=" + lossHome +
                ", lossAway=" + lossAway +
                ", goalsForTotal=" + goalsForTotal +
                ", goalsForTotalHome=" + goalsForTotalHome +
                ", goalsForTotalAway=" + goalsForTotalAway +
                ", goalsForHome=" + goalsForHome +
                ", goalsForAway=" + goalsForAway +
                ", goalsAgainstTotal=" + goalsAgainstTotal +
                ", goalsAgainstTotalHome=" + goalsAgainstTotalHome +
                ", goalsAgainstTotalAway=" + goalsAgainstTotalAway +
                ", goalsAgainstHome=" + goalsAgainstHome +
                ", goalsAgainstAway=" + goalsAgainstAway +
                ", goalDiffTotal=" + goalDiffTotal +
                ", goalDiffTotalHome=" + goalDiffTotalHome +
                ", goalDiffTotalAway=" + goalDiffTotalAway +
                ", goalDiffHome=" + goalDiffHome +
                ", goalDiffAway=" + goalDiffAway +
                ", pointsTotal=" + pointsTotal +
                ", pointsTotalHome=" + pointsTotalHome +
                ", pointsTotalAway=" + pointsTotalAway +
                ", pointsHome=" + pointsHome +
                ", pointsAway=" + pointsAway +
                ", nextOpponentTeamId=" + nextOpponentTeamId +
                ", nextOpponentTime=" + nextOpponentTime +
                ", nextOpponentMatchDifficultyRatingHome=" + nextOpponentMatchDifficultyRatingHome +
                ", nextOpponentMatchDifficultyRatingAway=" + nextOpponentMatchDifficultyRatingAway +
                ", formEntries=" + formEntries +
                '}';
    }
}
