package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import org.apache.commons.lang3.builder.Diff;

import java.util.List;

public class StoreCounterListener<T extends BaseEntity> implements IStatsStoreListener<T> {
    private final StoreCounters counters = new StoreCounters();

    @Override
    public void onSubmit(T entity) {
        ++counters.submitted;
    }

    @Override
    public void onCreate(T entity) {
        ++counters.created;
    }

    @Override
    public void onUpdate(T existing, T submitted, List<Diff<?>> changes) {
        ++counters.updated;
    }

    @Override
    public void onDiscard(T existing, T submitted) {
        ++counters.discarded;
    }

    public StoreCounters getCounters() {
        return counters;
    }
}
