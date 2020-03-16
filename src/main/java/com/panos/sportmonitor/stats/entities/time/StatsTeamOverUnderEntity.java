package com.panos.sportmonitor.stats.entities.time;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class StatsTeamOverUnderEntity extends StatsOverUnderEntity {
    private EntityId teamId;

    public StatsTeamOverUnderEntity(BaseEntity parent, long timeStamp) {
        super(parent, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.teamId = childEntity.getId();
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        //this.teamId = auxEntityId;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamOverUnderEntity{");
        sb.append(super.toString());
        sb.append(", teamId=").append(teamId);
        sb.append('}');
        return sb.toString();
    }
}
