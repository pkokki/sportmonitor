package com.panos.sportmonitor.spark.pipelines.sessions.models;

import com.fasterxml.jackson.databind.JsonNode;

public class MatchRadarEventFactory {
    public static MatchEvent create(RawRadarEvent raw) {
        String eventType = raw.getEvent();
        switch (eventType) {
            case "stats_match_situation":
                return new MatchSituationRadarEvent(raw);
            case "match_timeline":
                return new MatchTimelineRadarEvent(raw);
            case "match_detailsextended":
                JsonNode events = raw.getData().path("events");
                if (events.elements().hasNext()) {
                    return new MatchExtendedDetailsRadarEvent(raw);
                }
        }
        return null;
    }

    public static MatchEvent create(RawOverviewEvent raw) {
        return new MatchOverviewEvent(raw);
    }
}
