package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.CompositeId;
import com.panos.sportmonitor.stats.EntityKey;

public class UniqueTeamPlayerEntity extends BaseEntity {
    private Long start, end;
    private Boolean active;
    private String shirt;

    public UniqueTeamPlayerEntity(BaseEntity parent, long teamId, long playerId, long type) {
        super(parent, new CompositeId(UniqueTeamPlayerEntity.class,
                Lists.newArrayList(
                        new EntityKey("teamId", teamId),
                        new EntityKey("playerId", playerId),
                        new EntityKey("type", type)
                )));
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        // UniqueTeamPlayerEntity [UNHANDLED AUX ID]: 'roles.7129[]' --- id=CompositeId{teamId=3252, playerId=7129, type=1}, aux=7129
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "start.uts": this.start = node.asLong(); break;
            case "end.uts": this.end = node.asLong(); break;
            case "active":this.active = node.asBoolean(); break;
            case "shirt":this.shirt = node.asText(); break;
            case "_playerid":
            case "_type":
            case "name":
            case "start._doc":
            case "start.time":
            case "start.date":
            case "start.tz":
            case "start.tzoffset":
            case "end._doc":
            case "end.time":
            case "end.date":
            case "end.tz":
            case "end.tzoffset":
                break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team"))
            return true;
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        return "UniqueTeamPlayerEntity{" + "start=" + start +
                ", end=" + end +
                ", active=" + active +
                ", shirt='" + shirt + '\'' +
                '}';
    }
}
