package com.panos.sportmonitor.spark.pipelines.overview;

import java.io.Serializable;

/**
 * User-defined data type representing the update information returned by mapGroupsWithState.
 */
class LiveEvent implements Serializable {
    private long id, maxTimestamp;
    private long durationMs;
    private int numEvents;
    private boolean expired;

    public LiveEvent() { }

    LiveEvent(long id, long maxTimestamp, long durationMs, int numEvents, boolean expired) {
        this.id = id;
        this.maxTimestamp = maxTimestamp;
        this.durationMs = durationMs;
        this.numEvents = numEvents;
        this.expired = expired;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getMaxTimestamp() { return maxTimestamp; }
    public void setMaxTimestamp(long maxTimestamp) { this.maxTimestamp = maxTimestamp; }

    public long getDurationMs() { return durationMs; }
    public void setDurationMs(long durationMs) { this.durationMs = durationMs; }

    public int getNumEvents() { return numEvents; }
    public void setNumEvents(int numEvents) { this.numEvents = numEvents; }

    public boolean isExpired() { return expired; }
    public void setExpired(boolean expired) { this.expired = expired; }

    @Override public String toString() {
        return "EventUpdate(id = " + id + ", numEvents = " + numEvents +
                ", durationMs = " + durationMs + ", expired = " + expired + ")";
    }
}
