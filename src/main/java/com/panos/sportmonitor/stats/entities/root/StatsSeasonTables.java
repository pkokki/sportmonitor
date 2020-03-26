package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public class StatsSeasonTables extends BaseRootEntity {
    private EntityId seasonId;

    public StatsSeasonTables(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonTables, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "": this.seasonId = new EntityId(childEntity); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        return "StatsSeasonTables{" + "name='" + getName() + '\'' +
                ", seasonId=" + seasonId +
                '}';
    }
}
