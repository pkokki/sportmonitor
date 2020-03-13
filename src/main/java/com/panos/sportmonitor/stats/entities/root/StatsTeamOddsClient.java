package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;

public class StatsTeamOddsClient extends BaseRootEntity {
    private Long uniqueTeamId;

    public StatsTeamOddsClient(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.uniqueTeamId = childEntity.getId();
        }
        else if (entityName.startsWith("odds.")) {
            //String oddId = entityName.substring(5, entityName.indexOf('['));
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }
}
