package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;

import java.util.ArrayList;
import java.util.List;

public class StatsTeamNextX extends BaseRootEntity {
    private Long uniqueTeamId;
    private List<Long> matchIds = new ArrayList<>();
    private List<Long> tournamentIds = new ArrayList<>();
    private List<Long> uniqueTournamentIds = new ArrayList<>();
    private List<Long> realCategoriesIds = new ArrayList<>();

    public StatsTeamNextX(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "team": this.uniqueTeamId = childEntity.getId(); return true;
            case "matches[]": this.matchIds.add(childEntity.getId()); return true;
            default:
                if (entityName.matches("tournaments\\.\\d+")) {
                    this.tournamentIds.add(childEntity.getId()); return true;
                } else if (entityName.matches("uniquetournaments\\.\\d+")) {
                    this.uniqueTournamentIds.add(childEntity.getId()); return true;
                } else if (entityName.matches("realcategories\\.\\d+")) {
                    this.realCategoriesIds.add(childEntity.getId()); return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamNextX{");
        sb.append("name=").append(getName());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append(", matchIds=").append(matchIds);
        sb.append(", tournamentIds=").append(tournamentIds);
        sb.append(", uniqueTournamentIds=").append(uniqueTournamentIds);
        sb.append(", realCategoriesIds=").append(realCategoriesIds);
        sb.append('}');
        return sb.toString();
    }
}
