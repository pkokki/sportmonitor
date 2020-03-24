package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class NullEntity extends BaseEntity {
    public NullEntity(BaseEntity parent) {
        super(parent, new EntityId(NullEntity.class, Long.MAX_VALUE));
    }


}
