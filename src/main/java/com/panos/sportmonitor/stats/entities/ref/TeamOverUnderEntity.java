package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class TeamOverUnderEntity extends AbstractOverUnderEntity {
    private EntityId teamId;

    public TeamOverUnderEntity(BaseEntity parent, long teamId, long timeStamp) {
        super(parent, new EntityId(TeamOverUnderEntity.class, new EntityKey("teamId", teamId), EntityKey.Timestamp(timeStamp)));
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
        return true;
    }

    @Override
    public String toString() {
        return "TeamOverUnderEntity{" + super.toString() +
                ", teamId=" + teamId +
                '}';
    }
}
