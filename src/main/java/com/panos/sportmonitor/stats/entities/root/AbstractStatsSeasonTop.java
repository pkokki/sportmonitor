package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public abstract class AbstractStatsSeasonTop extends BaseRootEntity {
    protected EntityId seasonId;

    public AbstractStatsSeasonTop(BaseRootEntityType type, long timeStamp) {
        super(type, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); break;
            case "players[]": break;
            default:
                if (entityName.startsWith("teams."))
                    return true;
                else
                    return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }
}
