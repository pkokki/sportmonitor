package com.panos.sportmonitor.stats;


import com.panos.sportmonitor.stats.store.*;

public class StatsStore {
    private final BaseRootEntityMap rootEntities;
    private final BaseEntityMap entities;
    private final BaseTimeEntityMap timeEntities;
    private final boolean flagDDL;

    public StatsStore(boolean flagDDL) {
        this.flagDDL = flagDDL;

        rootEntities = new BaseRootEntityMap("RootEntities", flagDDL);
        entities = new BaseEntityMap("BaseEntities", flagDDL);
        timeEntities = new BaseTimeEntityMap("TimeEntities", flagDDL);
    }

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
        print(entities);
        print(timeEntities);
        print(rootEntities);
    }

    private void print(AbstractEntityMap<?> map) {
        StatsConsole.printlnState(String.format("StatsStore %s (%s): %s", map.getName(), map.getClass().getSimpleName(), map.getCounters()));
        if (flagDDL) {
            StatsConsole.printlnState(map.getSqlBuilderListener().print());
        }
    }
}
