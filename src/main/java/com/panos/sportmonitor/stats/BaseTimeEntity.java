package com.panos.sportmonitor.stats;

public abstract class BaseTimeEntity extends BaseEntity {
    private final long timeStamp;

    public BaseTimeEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, id);
        this.timeStamp = timeStamp;
    }

    public final long getTimeStamp() {
        return timeStamp;
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
