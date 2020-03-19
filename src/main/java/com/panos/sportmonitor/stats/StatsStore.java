package com.panos.sportmonitor.stats;


import com.panos.sportmonitor.stats.store.*;

public class StatsStore {
    private final EntityMap entities;

    public StatsStore() {
        entities = new EntityMap();
        entities.addListener(new StoreCounterListener());
        entities.addListener(new SqlTableCreator());
        entities.addListener(new SqlExecutor(true));
    }

    public void submit(BaseRootEntity rootEntity) {
        for (BaseEntity entity : rootEntity.getChildEntities()) {
            entities.submit(entity);
        }
    }

    public void submitChanges() {
        entities.submitChanges();
    }
}
