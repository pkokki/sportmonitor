package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;

public class StatsMatchGet extends BaseRootEntity {
    private Long matchId;

    public StatsMatchGet(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "": this.matchId = childEntity.getId(); return true;
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
