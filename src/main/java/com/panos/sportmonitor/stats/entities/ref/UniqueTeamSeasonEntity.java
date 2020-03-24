package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class UniqueTeamSeasonEntity extends BaseEntity {
    private final long __teamId;
    private final long __seasonId;
    private Double averageStartingXiAge;

    public static EntityId createId(long teamId, long seasonId) {
        return new EntityId(UniqueTeamSeasonEntity.class,
                new EntityKey("teamId", teamId),
                new EntityKey("seasonId", seasonId)
        );
    }

    public UniqueTeamSeasonEntity(BaseEntity parent, long teamId, long seasonId) {
        super(parent, createId(teamId, seasonId));
        this.__teamId = teamId;
        this.__seasonId = seasonId;
    }

    public long getTeamId() {
        return __teamId;
    }

    public long getSeasonId() {
        return __seasonId;
    }

    public void setAverageStartingXiAge(double age) {
        this.averageStartingXiAge = age;
    }

    @Override
    public String toString() {
        return "UniqueTeamSeasonEntity{" + "id=" + getId() +
                ", averageStartingXiAge=" + averageStartingXiAge +
                '}';
    }
}
