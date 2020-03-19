package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.Diff;

import java.util.List;

public class StoreCounterListener extends StatsStoreListener {
    private final StoreCounters counters = new StoreCounters();

    @Override
    public void onSubmit(BaseEntity entity) {
        ++counters.submitted;
    }

    @Override
    public void onCreate(BaseEntity entity) {
        ++counters.created;
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
        ++counters.updated;
    }

    @Override
    public void onDiscard(BaseEntity existing, BaseEntity submitted) {
        ++counters.discarded;
    }

    public void submitChanges() {
        StatsConsole.printlnState(counters.toString());
    }
}
