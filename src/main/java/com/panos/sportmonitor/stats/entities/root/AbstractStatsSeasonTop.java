package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.*;

public abstract class AbstractStatsSeasonTop extends BaseRootEntity {
    protected EntityId seasonId;
    protected EntityIdList players = new EntityIdList();
    protected EntityIdList uniqueTeams = new EntityIdList();

    public AbstractStatsSeasonTop(BaseRootEntityType type, long timeStamp) {
        super(type, timeStamp);
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
