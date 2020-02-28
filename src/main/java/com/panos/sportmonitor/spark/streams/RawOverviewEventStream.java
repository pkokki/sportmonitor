package com.panos.sportmonitor.spark.streams;

import com.google.common.collect.Iterators;
import com.panos.sportmonitor.spark.dto.*;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function0;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class RawOverviewEventStream extends AbstractJavaStream<RawOverviewEvent> {
    public RawOverviewEventStream(JavaDStream<RawOverviewEvent> stream) {
        super(stream);
    }

    public EventMasterDataStream createEventMasterDataStream(Function0<Long> sessionStamp) {
        JavaPairRDD<String, String> initialRDD = null;
        JavaDStream<EventMasterData> stream = this
                .mapToPair(e -> new Tuple2<>(e.getId(), e))
                .mapWithState(StateSpec
                        .function(RawOverviewEventStream::onlyOneEventSpec)
                        //.initialState(initialRDD)
                )
                .filter(Optional::isPresent)
                .map(r -> r.get())
                .map(r -> new EventMasterData(
                        Long.parseLong(r.getId()),
                        sessionStamp.call(),
                        r.getRegionId(), r.getRegionName(),
                        r.getLeagueId(), r.getLeagueName(),
                        r.getBetRadarId(),
                        r.getTitle(),
                        r.getStartTime(), r.getStartTimeTicks()/1000,
                        r.getHomeTeam(), r.getAwayTeam()
                ));
        return new EventMasterDataStream(stream);
    }

    public static Optional<RawOverviewEvent> onlyOneEventSpec(String id, Optional<RawOverviewEvent> item, State<String> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }

    public EventDataStream createEventDataStream() {
        JavaDStream<EventData> stream = this
                .map(r -> new EventData(
                        Long.parseLong(r.getId()),
                        r.getTimestamp(),
                        r.getClockTime(),
                        r.getIsSuspended(),
                        tryParse(r.getHomeScore(), 0),
                        r.getHomeRedCards(),
                        tryParse(r.getAwayScore(), 0),
                        r.getAwayRedCards()
                ));
        return new EventDataStream(stream);
    }

    private static int tryParse(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public MarketMasterDataStream createMarketMasterDataStream(Function0<Long> sessionStamp) {
        JavaDStream<MarketMasterData> stream = this
                .flatMap(e -> {
                    Long currentSessionStamp = sessionStamp.call();
                    return e.getMarkets().stream().map(m -> new MarketMasterData(
                            Long.parseLong(m.getId()),
                            currentSessionStamp,
                            Long.parseLong(e.getId()),
                            m.getDescription(),
                            m.getType(),
                            m.getHandicap()
                    )).iterator();
                })
                .mapToPair(m -> new Tuple2<>(m.getMarketId(), m))
                .mapWithState(StateSpec.function(RawOverviewEventStream::onlyOneMarketSpec))
                .filter(Optional::isPresent)
                .map(r -> r.get());
        return new MarketMasterDataStream(stream);
    }

    private static Optional<MarketMasterData> onlyOneMarketSpec(Long id, Optional<MarketMasterData> item, State<Long> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }

    public SelectionMasterDataStream createSelectionMasterDataStream(Function0<Long> sessionStamp) {
        JavaDStream<SelectionMasterData> stream = this
                .flatMap(e -> {
                    Long currentSessionStamp = sessionStamp.call();
                    List<SelectionMasterData> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> m.getSelections().forEach(s -> {
                        list.add(new SelectionMasterData(
                                Long.parseLong(s.getId()),
                                currentSessionStamp,
                                s.getDescription(),
                                Long.parseLong(m.getId())
                        ));
                    }));
                    return list.iterator();
                })
                .mapToPair(s -> new Tuple2<>(s.getSelectionId(), s))
                .mapWithState(StateSpec.function(RawOverviewEventStream::onlyOneSelectionSpec))
                .filter(Optional::isPresent)
                .map(r -> r.get());
        return new SelectionMasterDataStream(stream);
    }

    private static Optional<SelectionMasterData> onlyOneSelectionSpec(Long id, Optional<SelectionMasterData> item, State<Long> state) {
        if (state.isTimingOut() || state.exists()) {
            return Optional.empty();
        }
        else {
            state.update(id);
            return item;
        }
    }

    public SelectionDataStream createSelectionDataStream() {
        JavaDStream<SelectionData> stream = this
                .flatMap(e -> {
                    List<SelectionData> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> m.getSelections().forEach(s -> {
                        list.add(new SelectionData(Long.parseLong(s.getId()), e.getTimestamp(),
                                !(e.getIsSuspended() || m.getIsSuspended()), s.getPrice()));
                    }));
                    return list.iterator();
                })
                .mapToPair(e -> new Tuple2<>(e.getSelectionId(), e))
                .mapWithState(StateSpec.function(RawOverviewEventStream::updateSelectionPrevPriceMapping).timeout(Durations.minutes(120)))
                .filter(Objects::nonNull);
        return new SelectionDataStream(stream);
    }

    private static SelectionData updateSelectionPrevPriceMapping(long selectionId, Optional<SelectionData> newSelection, State<Double> state) {
        if (!state.isTimingOut() && newSelection.isPresent()) {
            SelectionData newEvent = newSelection.get();
            double newPrice = newEvent.getCurrentPrice();
            if (state.exists()) {
                double prevPrice = state.get();
                if (prevPrice != newPrice) {
                    state.update(newPrice);
                }
                newEvent.setPrevPrice(prevPrice);
            }
            else {
                state.update(newPrice);
            }
            return newEvent;
        }
        return null;
    }

    public DataPointStream createDataPointStream() {
        JavaDStream<DataPoint> stream = this
                .filter(e -> e.getBetRadarId() > 0)
                .mapToPair(e -> new Tuple2<>(e.getBetRadarId(), e))
                .mapWithState(StateSpec
                                .function(RawOverviewEventStream::overviewToDataPointMapping)
                        //.initialState(initialRDD)
                )
                .flatMap(i -> i.iterator());
        return new DataPointStream(stream);
    }

    public static List<DataPoint> overviewToDataPointMapping(Long id, Optional<RawOverviewEvent> rawOverviewEvent, State<DataPointState> state) {
        if (state.isTimingOut()) {
            state.remove();
        }
        else {
            DataPointState innerState = state.exists() ? state.get() : new DataPointState();
            if (rawOverviewEvent.isPresent()) {
                List<DataPoint> points = innerState.update(rawOverviewEvent.get());
                state.update(innerState);
                return points;
            }
        }
        return new ArrayList<>();
    }
}
