package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.PlayerEntity;

public class PlayerStatusEntity extends BaseEntity {
    private EntityId playerId;
    private EntityId uniqueTeamId;
    private Long statusStart;
    private Integer statusId;
    private String statusName;
    private String statusComment;
    private Integer statusMissing, statusDoubtful;

    public PlayerStatusEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(PlayerStatusEntity.class, id));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "player": this.playerId = new EntityId(childEntity); break;
            case "uniqueteam": this.uniqueTeamId = new EntityId(childEntity); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "_playerid": this.playerId = new EntityId(PlayerEntity.class, node.asLong()); break;
            case "status._statusid": this.statusId = node.asInt(); break;
            case "status.name": this.statusName = node.asText(); break;
            case "status.comment": this.statusComment = node.asText(); break;
            case "status.missing": this.statusMissing = node.asInt(); break;
            case "status.doubtful": this.statusDoubtful = node.asInt(); break;
            case "status.start.uts": this.statusStart = node.asLong(); break;

            case "status._id":
            case "_tid":
            case "status.status":
            case "status.start._doc":
            case "status.start.time":
            case "status.start.date":
            case "status.start.tz":
            case "status.start.tzoffset":
                break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlayerStatusEntity{" + "id=" + getId() +
                ", playerId=" + playerId +
                ", uniqueTeamId=" + uniqueTeamId +
                ", statusStart=" + statusStart +
                ", statusId=" + statusId +
                ", statusName='" + statusName + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", statusMissing=" + statusMissing +
                ", statusDoubtful=" + statusDoubtful +
                '}';
    }
}
