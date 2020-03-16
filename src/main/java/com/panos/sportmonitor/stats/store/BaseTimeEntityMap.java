package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import scala.Tuple3;

public class BaseTimeEntityMap extends AbstractEntityMap<BaseTimeEntity>  {

    public BaseTimeEntityMap(String name, boolean flagDDL) {
        super(name, new SqlBuilderListener<>(flagDDL));
    }

    @Override
    protected Object getKey(BaseTimeEntity entity) {
        return new Tuple3<>(entity.getClass().getSimpleName(), entity.getId(), entity.getTimeStamp());
    }
}
