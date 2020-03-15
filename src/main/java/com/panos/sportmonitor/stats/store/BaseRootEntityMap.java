package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import scala.Tuple3;

public class BaseRootEntityMap extends AbstractEntityMap<BaseRootEntity> {
    @Override
    protected Object getKey(BaseRootEntity entity) {
        return new Tuple3<>(entity.getClass().getSimpleName(), entity.getId(), entity.getTimeStamp());
    }
}
