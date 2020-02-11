package com.panos.sportmonitor.spark.pipelines.sessions.models;

public class MatchOverviewEvent extends MatchEvent {
    public MatchOverviewEvent(RawOverviewEvent raw) {
        super(raw.getBetRadarId(), raw.getTimestamp());
    }
}
