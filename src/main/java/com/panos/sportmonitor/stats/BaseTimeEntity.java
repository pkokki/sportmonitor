package com.panos.sportmonitor.stats;


public abstract class BaseTimeEntity extends BaseEntity {
    public BaseTimeEntity(BaseEntity parent, long id, long timeStamp) {
        this(parent, new EntityId(id, timeStamp));
    }

    protected BaseTimeEntity(BaseEntity parent, EntityId id) {
        super(parent, id);
        if (!id.isComposite())
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
