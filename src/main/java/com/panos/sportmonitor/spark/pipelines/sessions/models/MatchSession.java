package com.panos.sportmonitor.spark.pipelines.sessions.models;

import java.io.Serializable;

public class MatchSession implements Serializable {
    private long matchid;
    private long timestamp;

    public long getMatchid() {
        return matchid;
    }

    public void setMatchid(long matchid) {
        this.matchid = matchid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
