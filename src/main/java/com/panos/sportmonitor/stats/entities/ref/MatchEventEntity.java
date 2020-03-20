package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityIdList;

import java.util.ArrayList;
import java.util.List;

public class MatchEventEntity extends BaseEntity {
    private EntityId matchId;
    private EntityId playerId, scorerId, playerOutId, playerInId, statusId;
    private Integer typeId, minute, seconds;
    private String type, name, goalType;
    private Long eventTime, updatedTime;
    private Boolean disabled, header, ownGoal, penalty;
    private Integer minutes, injuryTime, period, periodScoreHome, periodScoreAway, resultHome, resultAway;
    private String resultWinner, team, card, periodName;
    private EntityIdList assists = new EntityIdList();

    public MatchEventEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "typeid":
            case "_typeid":
                this.typeId = node.asInt(); break;
            case "uts": this.eventTime = node.asLong(); break;
            case "updated_uts": this.updatedTime = node.asLong(); break;
            case "type": this.type = node.asText(); break;
            case "matchid": this.matchId = new EntityId(node.asLong()); break;
            case "disabled": this.disabled = node.asBoolean(); break;
            case "time": this.minute = node.asInt(); break;
            case "seconds": this.seconds = node.asInt(); break;
            case "name": this.name = node.asText(); break;
            case "goaltypeid": this.goalType = node.asText(); break;
            case "injurytime": this.injuryTime = node.asInt(); break;
            case "team": this.team = node.asText(); break;
            case "period": this.period = node.asInt(); break;
            case "periodname": this.periodName = node.asText(); break;
            case "periodscore.home": this.periodScoreHome = node.asInt(); break;
            case "periodscore.away": this.periodScoreAway = node.asInt(); break;
            case "result.home": this.resultHome = node.asInt(); break;
            case "result.away": this.resultAway = node.asInt(); break;
            case "result.winner": this.resultWinner = node.asText(); break;
            case "header":
            case "head":
                this.header = node.asBoolean(); break;
            case "owngoal": this.ownGoal = node.asBoolean(); break;
            case "penalty": this.penalty = node.asBoolean(); break;
            case "card": this.card = node.asText(); break;
            case "minutes": this.minutes = node.asInt(); break;

            case "_rcid":
            case "_tid":
            case "periodnumber":
            case "situation":
            case "_dc":
            case "player.name":
            case "_scoutid":
            case "params.1":
            case "side":
            case "sideid":
            case "shirtnumbers.in":
            case "shirtnumbers.out":
            case "X":
            case "Y":
            case "coordinates[].team":
            case "coordinates[].X":
            case "coordinates[].Y":
                /* ignore */
                break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "player": this.playerId = childEntity.getId(); break;
            case "status":
            case "matchStatus":
                this.statusId = childEntity.getId(); break;
            case "scorer": this.scorerId = childEntity.getId(); break;
            case "assists[]": this.assists.add(childEntity.getId()); break;
            case "playerout": this.playerOutId = childEntity.getId(); break;
            case "playerin": this.playerInId = childEntity.getId(); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchEventEntity{");
        sb.append("id=").append(getId());
        sb.append(", typeId=").append(typeId);
        sb.append(", minute=").append(minute);
        sb.append(", seconds=").append(seconds);
        sb.append(", type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", goalType='").append(goalType).append('\'');
        sb.append(", matchId=").append(matchId);
        sb.append(", eventTime=").append(eventTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", disabled=").append(disabled);
        sb.append(", header=").append(header);
        sb.append(", ownGoal=").append(ownGoal);
        sb.append(", penalty=").append(penalty);
        sb.append(", minutes=").append(minutes);
        sb.append(", injuryTime=").append(injuryTime);
        sb.append(", period=").append(period);
        sb.append(", periodScoreHome=").append(periodScoreHome);
        sb.append(", periodScoreAway=").append(periodScoreAway);
        sb.append(", resultHome=").append(resultHome);
        sb.append(", resultAway=").append(resultAway);
        sb.append(", resultWinner='").append(resultWinner).append('\'');
        sb.append(", team='").append(team).append('\'');
        sb.append(", card='").append(card).append('\'');
        sb.append(", periodName='").append(periodName).append('\'');
        sb.append(", playerId=").append(playerId);
        sb.append(", scorerId=").append(scorerId);
        sb.append(", playerOutId=").append(playerOutId);
        sb.append(", playerInId=").append(playerInId);
        sb.append(", statusId=").append(statusId);
        sb.append(", assists=").append(assists);
        sb.append('}');
        return sb.toString();
    }
}
