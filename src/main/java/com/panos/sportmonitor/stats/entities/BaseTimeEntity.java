package com.panos.sportmonitor.stats.entities;

public class BaseTimeEntity extends BaseEntity {
    private final long timeStamp;

    public BaseTimeEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, id);
        this.timeStamp = timeStamp;
    }

    public final long getTimeStamp() {
        return timeStamp;
    }
}
