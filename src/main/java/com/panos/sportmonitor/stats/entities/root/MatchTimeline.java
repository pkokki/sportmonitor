package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;


public class MatchTimeline extends BaseRootEntity {
    private EntityId matchId;

    public MatchTimeline(long timeStamp) {
        super(BaseRootEntityType.MatchTimeline, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("match")) {
            this.matchId = new EntityId(childEntity);
            return true;
        } else if (entityName.equals("events[]")) {
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchTimeline{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", matchId=").append(matchId);
        sb.append('}');
        return sb.toString();
    }
}
