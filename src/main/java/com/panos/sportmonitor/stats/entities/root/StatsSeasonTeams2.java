package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public class StatsSeasonTeams2 extends BaseRootEntity {
    private EntityId seasonId;

    public StatsSeasonTeams2(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonTeams2, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); return true;
            case "teams[]":
            case "tables[]":
                return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonTeams2{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
