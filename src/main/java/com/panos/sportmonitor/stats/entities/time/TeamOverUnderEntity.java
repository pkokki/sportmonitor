package com.panos.sportmonitor.stats.entities.time;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class TeamOverUnderEntity extends OverUnderEntryEntity {
    private EntityId teamId;

    public TeamOverUnderEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, new EntityId(id, timeStamp, TeamOverUnderEntity.class));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.teamId = new EntityId(childEntity);
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
        return "TeamOverUnderEntity{" + super.toString() +
                ", teamId=" + teamId +
                '}';
    }
}
