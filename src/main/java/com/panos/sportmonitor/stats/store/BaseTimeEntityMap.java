package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseTimeEntity;
import scala.Tuple3;

public class BaseTimeEntityMap extends AbstractEntityMap<BaseTimeEntity>  {
    @Override
    protected Object getKey(BaseTimeEntity entity) {
        return new Tuple3<>(entity.getClass().getSimpleName(), entity.getId(), entity.getTimeStamp());
    }
}
