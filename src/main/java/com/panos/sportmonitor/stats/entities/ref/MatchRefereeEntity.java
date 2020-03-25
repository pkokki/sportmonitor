package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class MatchRefereeEntity extends BaseEntity {
    public MatchRefereeEntity(BaseEntity parent, EntityId matchId, EntityId playerId) {
        super(parent, new EntityId(MatchRefereeEntity.class, matchId, playerId));
    }
}
