package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class StadiumUniqueTeamEntity extends BaseEntity {
    public StadiumUniqueTeamEntity(StadiumEntity parent, EntityId stadiumId, EntityId teamId) {
        super(parent, new EntityId(StadiumUniqueTeamEntity.class, stadiumId, teamId));
    }
}
