package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public class StatsSeasonOdds extends BaseRootEntity {
    private EntityId seasonId;

    public StatsSeasonOdds(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonOdds, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "": this.seasonId = new EntityId(childEntity); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonOdds{");
        sb.append("name=").append(getName());
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
