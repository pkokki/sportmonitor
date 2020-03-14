package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityIdList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStatsSeasonTop extends BaseRootEntity {
    protected BigInteger seasonId;
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
