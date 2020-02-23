package com.panos.sportmonitor.spark.pipelines.sessions.models;

import com.panos.sportmonitor.spark.dto.RawOverviewEvent;

public class MatchOverviewEvent extends MatchEvent {
    public MatchOverviewEvent(RawOverviewEvent raw) {
        super(raw.getBetRadarId(), raw.getTimestamp());
    }
}
