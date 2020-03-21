package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public class AbstractStatsSeasonX extends BaseRootEntity {
    private EntityId seasonId;
    private EntityIdList matches = new EntityIdList();
    private EntityIdList tournaments = new EntityIdList();

    public AbstractStatsSeasonX(BaseRootEntityType type, long timeStamp) {
        super(type, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); break;
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
        sb.append(", seasonId=").append(seasonId);
        sb.append(", matches=").append(matches);
        sb.append(", tournaments=").append(tournaments);
        sb.append('}');
        return sb.toString();
    }
}
