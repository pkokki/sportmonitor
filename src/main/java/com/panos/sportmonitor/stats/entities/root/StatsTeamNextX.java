package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityIdList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StatsTeamNextX extends BaseRootEntity {
    private BigInteger uniqueTeamId;
    private EntityIdList matchIds = new EntityIdList();
    private EntityIdList tournamentIds = new EntityIdList();
    private EntityIdList uniqueTournamentIds = new EntityIdList();
    private EntityIdList realCategoriesIds = new EntityIdList();

    public StatsTeamNextX(long timeStamp) {
        super(BaseRootEntityType.StatsTeamNextX, timeStamp);
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
