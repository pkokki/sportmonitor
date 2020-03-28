package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class SeasonOverUnderEntity extends AbstractOverUnderEntity {
    private EntityId seasonId;

    public SeasonOverUnderEntity(BaseEntity parent, EntityId seasonId, long timeStamp) {
        super(parent, new EntityId(SeasonOverUnderEntity.class,
                new EntityId[] { seasonId },
                new EntityKey[] { EntityKey.Timestamp(timeStamp) }));
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
