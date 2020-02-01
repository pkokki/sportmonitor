package com.panos.sportmonitor.spark.pipelines.overview;

import java.io.Serializable;

/**
 * User-defined data type for storing an event information as state in mapGroupsWithState.
 */
class EventState implements Serializable {
    private int numEvents = 0;
    private long startTimestampMs = -1;
    private long endTimestampMs = -1;

    int getNumEvents() { return numEvents; }
    void setNumEvents(int numEvents) { this.numEvents = numEvents; }

    long getStartTimestampMs() { return startTimestampMs; }
    void setStartTimestampMs(long startTimestampMs) {
        this.startTimestampMs = startTimestampMs;
    }

    long getEndTimestampMs() { return endTimestampMs; }
    void setEndTimestampMs(long endTimestampMs) { this.endTimestampMs = endTimestampMs; }

    long calculateDuration() { return endTimestampMs - startTimestampMs; }

    @Override public String toString() {
        return "EventInfo(numEvents = " + numEvents +
                ", timestamps = " + startTimestampMs + " to " + endTimestampMs + ")";
    }
}
