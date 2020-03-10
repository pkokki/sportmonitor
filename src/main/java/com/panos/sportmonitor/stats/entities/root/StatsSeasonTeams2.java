package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class StatsSeasonTeams2 extends RootEntity {
    private long seasonId;
    private List<Long> teams = new ArrayList<>();
    private List<Long> statsTables = new ArrayList<>();

    public StatsSeasonTeams2(String name) {
        super(name);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); return true;
            case "teams[]": this.teams.add(childEntity.getId()); return true;
            case "tables[]": this.statsTables.add(childEntity.getId()); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonTeams2{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append(", teams=").append(teams);
        sb.append(", statsTables=").append(statsTables);
        sb.append('}');
        return sb.toString();
    }
}
