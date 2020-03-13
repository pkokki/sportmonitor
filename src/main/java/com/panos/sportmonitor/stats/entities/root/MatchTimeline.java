package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;

import java.util.ArrayList;
import java.util.List;

public class MatchTimeline extends BaseRootEntity {
    private long matchId;
    private List<Long> events = new ArrayList<>();

    public MatchTimeline(String name, long timeStamp) {
        super(name, timeStamp);
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
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", matchId=").append(matchId);
        sb.append(", events=").append(events);
        sb.append('}');
        return sb.toString();
    }
}
