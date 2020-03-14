package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityIdList;

import java.util.ArrayList;
import java.util.List;

public class StatsSeasonInjuries extends BaseRootEntity {
    private EntityIdList playerStatuses = new EntityIdList();

    public StatsSeasonInjuries(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonInjuries, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "[]": this.playerStatuses.add(childEntity.getId()); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonInjuries{");
        sb.append("name=").append(getName());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", playerStatuses=").append(playerStatuses);
        sb.append('}');
        return sb.toString();
    }
}
