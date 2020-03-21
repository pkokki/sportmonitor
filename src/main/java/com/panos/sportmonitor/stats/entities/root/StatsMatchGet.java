package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public class StatsMatchGet extends BaseRootEntity {
    private EntityId matchId;

    public StatsMatchGet(long timeStamp) {
        super(BaseRootEntityType.StatsMatchGet, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "": this.matchId = new EntityId(childEntity); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsMatchGet{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", matchId=").append(matchId);
        sb.append('}');
        return sb.toString();
    }
}
