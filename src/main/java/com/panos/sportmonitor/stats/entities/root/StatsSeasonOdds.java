package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;

public class StatsSeasonOdds extends BaseRootEntity {
    private Long seasonId;

    public StatsSeasonOdds(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "": this.seasonId = childEntity.getId(); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonOdds{");
        sb.append("name=").append(getName());
        sb.append(", timeStampt=").append(getTimeStamp());
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
