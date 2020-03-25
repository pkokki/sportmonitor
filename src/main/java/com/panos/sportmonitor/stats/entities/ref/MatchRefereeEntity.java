package com.panos.sportmonitor.stats.entities.ref;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class MatchRefereeEntity extends BaseEntity {
    public MatchRefereeEntity(BaseEntity parent, EntityId matchId, EntityId playerId) {
        super(parent, new EntityId(MatchRefereeEntity.class, matchId, playerId));
    }

//    private static EntityId createId(EntityId matchId, EntityId playerId) {
//        LinkedList<EntityKey> keys = new LinkedList<>(matchId.getKeys());
//        keys.addAll(playerId.getKeys());
//        return new EntityId(MatchRefereeEntity.class, keys);
//    }

}
