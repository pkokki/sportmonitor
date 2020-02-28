package com.panos.sportmonitor.spark.dto;

import org.apache.commons.math3.util.Precision;

import java.io.Serializable;

public class DataPoint implements Serializable {
    private final long matchId;
    private final long stamp;
    private final String key;
    private final double value;
    private final Double diff;

    public DataPoint(long matchId, long stamp, String key, double value, Double diff) {
        this.matchId = matchId;
        this.stamp = stamp;
        this.key = key;
        this.value = Precision.round(value, 2);
        this.diff = diff != null ? Precision.round(diff, 2) : null;
    }

    public long getMatchId() {
        return matchId;
    }

    public long getStamp() {
        return stamp;
    }

    public String getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }

    public Double getDiff() {
        return diff;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "matchId=" + matchId +
                ", stamp=" + stamp +
                ", key='" + key + '\'' +
                ", value=" + value +
                ", diff=" + diff +
                '}';
    }
}
