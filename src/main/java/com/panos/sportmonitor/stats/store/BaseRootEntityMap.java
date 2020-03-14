package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseRootEntity;
import scala.Tuple3;

public class BaseRootEntityMap extends AbstractEntityMap<BaseRootEntity> {
    @Override
    protected Object getKey(BaseRootEntity entity) {
        return new Tuple3<>(entity.getClass().getSimpleName(), entity.getName(), entity.getTimeStamp());
    }
}
