package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import scala.Tuple3;

public class BaseRootEntityMap extends AbstractEntityMap<BaseRootEntity> {
    public BaseRootEntityMap(String name, boolean flagDDL) {
        super(name, new SqlBuilderListener<>(flagDDL));
    }

    @Override
    protected Object getKey(BaseRootEntity entity) {
        return new Tuple3<>(entity.getClass().getSimpleName(), entity.getId(), entity.getTimeStamp());
    }
}
