package com.panos.sportmonitor.stats.entities.time;

import com.panos.sportmonitor.stats.BaseEntity;

public class StatsTeamOverUnderEntity extends StatsOverUnderEntity {
    private long teamId;

    public StatsTeamOverUnderEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, id, timeStamp);
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
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamOverUnderEntity{");
        sb.append(super.toString());
        sb.append(", teamId=").append(teamId);
        sb.append('}');
        return sb.toString();
    }
}
