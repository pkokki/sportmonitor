package com.panos.sportmonitor.stats.entities.time;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class SeasonOverUnderEntity extends OverUnderEntryEntity {
    private EntityId seasonId;

    public SeasonOverUnderEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, new EntityId(SeasonOverUnderEntity.class, id, timeStamp));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.seasonId = new EntityId(childEntity);
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        return "SeasonOverUnderEntity{" + super.toString() +
                ", seasonId=" + seasonId +
                '}';
    }
}
