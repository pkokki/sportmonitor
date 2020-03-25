package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;

public class StatsSeasonInjuries extends BaseRootEntity {

    public StatsSeasonInjuries(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonInjuries, timeStamp);
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
                '}';
    }
}
