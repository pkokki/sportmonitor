package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import org.apache.commons.lang3.builder.Diff;

import java.util.List;

public interface IStatsStoreListener<T extends BaseEntity> {
    void onSubmit(T entity);
    void onCreate(T entity);
    void onUpdate(final T existing, final T submitted, final List<Diff<?>> changes);
    void onDiscard(T existing, T submitted);
}
