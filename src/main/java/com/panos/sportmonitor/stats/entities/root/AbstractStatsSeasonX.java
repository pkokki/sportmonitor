package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public class AbstractStatsSeasonX extends BaseRootEntity {
    private EntityId seasonId;

    public AbstractStatsSeasonX(BaseRootEntityType type, long timeStamp) {
        super(type, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); break;
            case "matches[]": break;
            default:
                if (entityName.startsWith("tournaments."))
                    break;
                return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name=" + getName() +
                ", seasonId=" + seasonId +
                '}';
    }
}
