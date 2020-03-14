package com.panos.sportmonitor.stats.entities.time;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class StatsSeasonOverUnderEntity extends StatsOverUnderEntity {
    private EntityId seasonId;

    public StatsSeasonOverUnderEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, id, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.seasonId = childEntity.getId();
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonOverUnderEntity{");
        sb.append(super.toString());
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
