package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class StatsSeasonLastX extends RootEntity {
    private long seasonId;
    private List<Long> matches = new ArrayList<>();
    private List<Long> tournaments = new ArrayList<>();

    public StatsSeasonLastX(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); break;
            case "matches[]": this.matches.add(childEntity.getId()); break;
            default:
                if (entityName.startsWith("tournaments."))
                    this.tournaments.add(childEntity.getId());
                else return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append("{");
        sb.append("name=").append(getName());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", seasonId=").append(seasonId);
        sb.append(", matches=").append(matches);
        sb.append(", tournaments=").append(tournaments);
        sb.append('}');
        return sb.toString();
    }
}
