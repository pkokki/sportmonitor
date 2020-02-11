package com.panos.sportmonitor.spark.pipelines.sessions.models;

import java.io.Serializable;

public abstract class MatchEvent implements Serializable {
    private final long matchid;
    private final long timestamp;

    public MatchEvent(long matchid, long timestamp) {
        this.matchid = matchid;
        this.timestamp = timestamp;
    }

    public long getMatchid() {
        return matchid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s(%d @ %d)", this.getClass().getSimpleName(), getMatchid(), getTimestamp());
    }
}
