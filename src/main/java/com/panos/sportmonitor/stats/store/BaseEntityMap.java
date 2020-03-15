package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import scala.Tuple2;

public class BaseEntityMap extends AbstractEntityMap<BaseEntity> {
    @Override
    protected Object getKey(BaseEntity entity) {
        return new Tuple2<>(entity.getClass().getSimpleName(), entity.getId());
    }
}
