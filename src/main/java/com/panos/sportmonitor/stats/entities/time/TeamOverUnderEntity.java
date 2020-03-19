package com.panos.sportmonitor.stats.entities.time;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class TeamOverUnderEntity extends OverUnderEntryEntity {
    private EntityId teamId;

    public TeamOverUnderEntity(BaseEntity parent, long id, long timeStamp) {
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
    public boolean handleAuxId(long auxEntityId) {
        //this.teamId = auxEntityId;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamOverUnderEntity{");
        sb.append(super.toString());
        sb.append(", teamId=").append(teamId);
        sb.append('}');
        return sb.toString();
    }
}
