package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public class StatsTeamLastX extends BaseRootEntity {
    private EntityId uniqueTeamId;
    private EntityIdList matches = new EntityIdList();
    private EntityIdList tournaments = new EntityIdList();
    private EntityIdList uniqueTournaments = new EntityIdList();
    private EntityIdList realCategories = new EntityIdList();

    public StatsTeamLastX(long timeStamp) {
        super(BaseRootEntityType.StatsTeamLastX, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "team": this.uniqueTeamId = new EntityId(childEntity); return true;
            case "matches[]": this.matches.add(childEntity.getId()); return true;
            default:
                if (entityName.matches("tournaments\\.\\d+")) {
                    this.tournaments.add(childEntity.getId()); return true;
                } else if (entityName.matches("uniquetournaments\\.\\d+")) {
                    this.uniqueTournaments.add(childEntity.getId()); return true;
                } else if (entityName.matches("realcategories\\.\\d+")) {
                    this.realCategories.add(childEntity.getId()); return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamLastX{");
        sb.append("name=").append(getName());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append(", matchIds=").append(matches);
        sb.append(", tournamentIds=").append(tournaments);
        sb.append(", uniqueTournamentIds=").append(uniqueTournaments);
        sb.append(", realCategoriesIds=").append(realCategories);
        sb.append('}');
        return sb.toString();
    }
}
