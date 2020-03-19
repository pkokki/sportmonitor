package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;


public class MatchTimeline extends BaseRootEntity {
    private EntityId matchId;
    private EntityIdList events = new EntityIdList();

    public MatchTimeline(long timeStamp) {
        super(BaseRootEntityType.MatchTimeline, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("match")) {
            this.matchId = childEntity.getId();
            return true;
        } else if (entityName.equals("events[]")) {
            this.events.add(childEntity.getId());
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchTimeline{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", matchId=").append(matchId);
        sb.append(", events=").append(events);
        sb.append('}');
        return sb.toString();
    }
}
