package com.panos.sportmonitor.stats;


public abstract class BaseTimeEntity extends BaseEntity {
    protected BaseTimeEntity(BaseEntity parent, EntityId id) {
        super(parent, id);
        if (id.getKeys().stream().noneMatch(e -> e.getName().equals(EntityId.KEY_TIMESTAMP)))
            throw new IllegalArgumentException("A BaseTimeEntity must have composite EntityId.");
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        String id = this.getId().toString();
        String aux = Long.toString(auxEntityId);
        if (id.substring(id.length() - aux.length()).equals(aux))
            return true;
        return super.handleAuxId(auxEntityId);
    }
}
