package com.panos.sportmonitor.spark.pipelines.sessions.models;

public class MatchTimelineRadarEvent extends MatchEvent {
    public MatchTimelineRadarEvent(RawRadarEvent raw) {
        super(raw.getData().path("matchid").asLong(), raw.getData().path("uts").asLong());
    }
}
