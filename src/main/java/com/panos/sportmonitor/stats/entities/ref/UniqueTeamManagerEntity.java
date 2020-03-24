package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class UniqueTeamManagerEntity extends BaseEntity {
    private final long __teamId;
    private final long __managerId;
    private Long startTime;

    public static EntityId createId(long teamId, long playerId) {
        return new EntityId(UniqueTeamManagerEntity.class,
                new EntityKey("teamId", teamId),
                new EntityKey("managerId", playerId)
        );
    }

    public UniqueTeamManagerEntity(BaseEntity parent, long teamId, long managerId) {
        super(parent, createId(teamId, managerId));
        this.__teamId = teamId;
        this.__managerId = managerId;
    }

    public long getTeamId() {
        return __teamId;
    }

    public long getManagerId() {
        return __managerId;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "UniqueTeamManagerEntity{" + "id=" + getId() +
                ", startTime=" + startTime +
                '}';
    }
}
