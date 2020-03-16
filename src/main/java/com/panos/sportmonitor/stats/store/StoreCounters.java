package com.panos.sportmonitor.stats.store;

public class StoreCounters {
    public int submitted, created, updated, discarded;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StoreCounters{");
        sb.append("submitted=").append(submitted);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", discarded=").append(discarded);
        sb.append('}');
        return sb.toString();
    }
}
