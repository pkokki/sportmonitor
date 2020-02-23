package com.panos.sportmonitor.spark.pipelines.sessions.models;

import com.panos.sportmonitor.spark.dto.RawRadarEvent;

public class MatchSituationRadarEvent extends MatchEvent {
    public MatchSituationRadarEvent(RawRadarEvent raw) {
        super(Long.parseLong(raw.getData().path("matchid").asText()), raw.getDob());
    }
}
