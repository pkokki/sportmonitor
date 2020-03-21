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
        final StringBuilder sb = new StringBuilder("StatsSeasonTables{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
