package com.panos.sportmonitor.spark.dto;

import com.fasterxml.jackson.databind.JsonNode;
import scala.Tuple2;

import java.io.Serializable;
import java.util.*;

public class DataPointState implements Serializable {
    private final Hashtable<String, Double> values;

    public DataPointState() {
        values = new Hashtable<>();
    }

    public List<DataPoint> update(RawOverviewEvent ev) {
        List<DataPoint> dataPoints = new ArrayList<>();
        long matchId = ev.getBetRadarId();
        long stamp = ev.getTimestamp();
        tryUpdate(dataPoints, matchId, stamp, DataPointKeys.HOME_SCORE, ev.getHomeScoreAsInt());
        tryUpdate(dataPoints, matchId, stamp, DataPointKeys.AWAY_SCORE, ev.getAwayScoreAsInt());
        tryUpdate(dataPoints, matchId, stamp, DataPointKeys.HOME_RED_CARDS, ev.getHomeRedCards());
        tryUpdate(dataPoints, matchId, stamp, DataPointKeys.AWAY_RED_CARDS, ev.getAwayRedCards());
        for (RawOverviewMarket market : ev.getMarkets()) {
            String type = market.getType();
            for (RawOverviewSelection sel : market.getSelections()) {
                String selDescription = sel.getDescription();
                String key = resolveSelectionKey(type, selDescription, ev.getHomeTeam(), ev.getAwayTeam());
                if (key != null) {
                    tryUpdate(dataPoints, matchId, stamp, key, sel.getPrice());
                }
            }
        }
        return dataPoints;
    }

    private String resolveSelectionKey(String marketType, String selection, String home, String away) {
        String key = null;
        switch (marketType) {
            case DataPointKeys.SELECTION_MRES:
            case DataPointKeys.SELECTION_DBLC:
                key = String.format("%s_%s", marketType, selection);
                break;
            case DataPointKeys.SELECTION_INTS:
            case DataPointKeys.SELECTION_DNOB:
                if (selection.equals(home))
                    key = String.format("%s_H", marketType);
                else if (selection.equals(away))
                    key = String.format("%s_A", marketType);
                else if (selection.equals("Κανένα γκολ"))
                    key = String.format("%s_0", marketType);
                else
                    System.out.println(String.format("Unknown selection: %s %s %s", selection, home, away));
                break;
            case DataPointKeys.SELECTION_HCTG:
                break;
        }
        return key;
    }

    private void tryUpdate(List<DataPoint> dataPoints, long matchId, long stamp, String key, double value) {
        if (values.containsKey(key)) {
            double prevValue = values.get(key);
            if (Math.abs(prevValue - value) > 0.01 ) {
                dataPoints.add(new DataPoint(matchId, stamp, key, value, value - prevValue));
                values.put(key, value);
            }
        }
        else {
            dataPoints.add(new DataPoint(matchId, stamp, key, value, null));
            values.put(key, value);
        }
    }

    public List<DataPoint> update(RawRadarEvent ev) {
        List<DataPoint> dataPoints = new ArrayList<>();
        long matchId = ev.getMatchId();
        long stamp = ev.getDob();
        switch (ev.getEvent()) {
            case RawRadarEvent.MATCH_SITUATION:
                //MatchSituationEvent mse = new MatchSituationEvent(matchId, stamp, ev.getData());
                break;
            case RawRadarEvent.MATCH_DETAILS:
                Iterator<Map.Entry<String, JsonNode>> fields = ev.getData().path("values").fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    MatchDetailsEvent mde = new MatchDetailsEvent(matchId, stamp, entry.getKey(), entry.getValue());
                    if (mde.getHome() != null)
                        tryUpdate(dataPoints, matchId, stamp, String.format("MDE_%s_H", mde.getTypeId()), mde.getHome());
                    if (mde.getAway() != null)
                        tryUpdate(dataPoints, matchId, stamp, String.format("MDE_%s_A", mde.getTypeId()), mde.getAway());
                }
                break;
            case RawRadarEvent.MATCH_TIMELINE:
                //Iterator<JsonNode> events = ev.getData().path("events").elements();
                //while (events.hasNext()) {
                //    JsonNode evNode = events.next();
                //    if (evNode != null) {
                //        MatchTimelineEvent mte = new MatchTimelineEvent(evNode);
                //        tryUpdate(dataPoints, matchId, stamp, String.format("MDE_%s_H", mte.getTypeId()), mte.getHome());
                //    }
                //}
                break;
        }
        return dataPoints;
    }
}
