package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;

public class StatsSeasonTables extends BaseRootEntity {
    private Long seasonId;

    public StatsSeasonTables(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "": this.seasonId = childEntity.getId(); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonTables{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
