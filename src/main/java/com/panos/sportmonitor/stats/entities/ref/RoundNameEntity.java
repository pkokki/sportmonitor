package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class RoundNameEntity extends BaseEntity {
    public RoundNameEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(RoundNameEntity.class, id));
    }
}
