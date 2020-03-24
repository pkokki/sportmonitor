package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityKey;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.Diff;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StoreCounterListener extends StatsStoreListener {
    private final StoreCounters counters = new StoreCounters();
    private final Set<String> entityTypes = new LinkedHashSet<>();

    @Override
    public void onSubmit(BaseEntity entity) {
        ++counters.submitted;
        entityTypes.add(String.format("%s%s",
                entity.getClass().getSimpleName(),
                entity.getId().getKeys().stream().map(EntityKey::getName).collect(Collectors.toList())));
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
        StatsConsole.printlnState("  Submitted entity types: " + entityTypes.toString());
    }
}
