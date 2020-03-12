package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class StatsSeasonTopGoals extends RootEntity {
    private Long seasonId;
    private List<Long> players = new ArrayList<>();
    private List<Long> uniqueTeams = new ArrayList<>();

    public StatsSeasonTopGoals(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = childEntity.getId(); break;
            case "players[]": this.players.add(childEntity.getId()); break;
            default:
                if (entityName.startsWith("teams."))
                    this.uniqueTeams.add(childEntity.getId());
                else
                    return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }
}
