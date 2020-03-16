package com.panos.sportmonitor.stats;


public abstract class BaseTimeEntity extends BaseEntity {
    private final long timeStamp;

    public BaseTimeEntity(BaseEntity parent, long timeStamp) {
        super(parent, combine(parent, timeStamp));
        this.timeStamp = timeStamp;
    }

    protected BaseTimeEntity(BaseEntity parent, EntityId id, long timeStamp) {
        super(parent, id);
        this.timeStamp = timeStamp;
    }

    private static EntityId combine(BaseEntity parent, long timeStamp) {
        try {
            return new EntityId((timeStamp << 6) + parent.getRoot().getNextId());
        } catch (NumberFormatException ex) {
            String parentName = parent.getClass().getSimpleName();
            throw new NumberFormatException(String.format("%s: Unable to combine %d", parentName, timeStamp));
        }
    }
    protected long getRawId() {
        String id = this.getId().toString();
        if (id.length() > 18)
            id = id.substring(id.length() - 18);
        return Long.parseLong(id);
    }

    public final long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        String id = this.getId().toString();
        String aux = Long.toString(auxEntityId);
        if (id.substring(id.length() - aux.length()).equals(aux))
            return true;
        return super.handleAuxId(auxEntityId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append("{");
        sb.append("id=").append(getId());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", ......}");
        return sb.toString();
    }


}
