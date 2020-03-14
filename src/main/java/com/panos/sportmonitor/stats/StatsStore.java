package com.panos.sportmonitor.stats;


import com.panos.sportmonitor.stats.store.*;

public class StatsStore {
    private final BaseRootEntityMap rootEntities = new BaseRootEntityMap();
    private final BaseEntityMap entities = new BaseEntityMap();
    private final BaseTimeEntityMap timeEntities = new BaseTimeEntityMap();

    public void submit(BaseRootEntity rootEntity) {
        for (BaseEntity entity : rootEntity.getChildEntities()) {
            if (entity instanceof BaseRootEntity) {
                rootEntities.submit((BaseRootEntity)entity);
            }
            else if (entity instanceof BaseTimeEntity) {
                timeEntities.submit((BaseTimeEntity)entity);
            }
            else {
                entities.submit(entity);
            }
        }
    }

    public void print() {
        StatsConsole.printlnState(String.format("StatsStore rootEntities: submitted=%d, created=%d, updated=%d, discarded=%d",
                rootEntities.getCounters().submitted, rootEntities.getCounters().created, rootEntities.getCounters().updated, rootEntities.getCounters().discarded));
        StatsConsole.printlnState(String.format("StatsStore entities: submitted=%d, created=%d, updated=%d, discarded=%d",
                entities.getCounters().submitted, entities.getCounters().created, entities.getCounters().updated, entities.getCounters().discarded));
        StatsConsole.printlnState(String.format("StatsStore timeEntities: submitted=%d, created=%d, updated=%d, discarded=%d",
                timeEntities.getCounters().submitted, timeEntities.getCounters().created, timeEntities.getCounters().updated, timeEntities.getCounters().discarded));
    }
}
