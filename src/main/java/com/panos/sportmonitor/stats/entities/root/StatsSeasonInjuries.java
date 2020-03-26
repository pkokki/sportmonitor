package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.SeasonEntity;

public class StatsSeasonInjuries extends BaseRootEntity {
    private final EntityId seasonId;

    public StatsSeasonInjuries(long seasonId, long timeStamp) {
        super(BaseRootEntityType.StatsSeasonInjuries, timeStamp);
        this.seasonId = SeasonEntity.createId(seasonId);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if ("[]".equals(entityName)) {
            return true;
        } else {
            return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        return "StatsSeasonInjuries{" + "name=" + getName() +
                ", seasonId=" + seasonId +
                '}';
    }
}
