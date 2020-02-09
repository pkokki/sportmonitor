package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.common.Event;
import org.apache.spark.api.java.Optional;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.stereotype.Service;
import scala.Tuple2;

@Service
public class EventScoreChangeProcessor {
    public JavaDStream<EventScoreChange> run(JavaStreamingContext sc, JavaDStream<Event> eventDS) {
        return eventDS
                .mapToPair(e -> new Tuple2<>(e.getEventId(), e))
                .mapWithState(StateSpec.function(EventScoreChangeProcessor::stateMapping).timeout(Durations.minutes(100)))
                .filter(r -> r.isPresent())
                .map(r -> r.get());
    }

    private static Optional<EventScoreChange> stateMapping(Long id, Optional<Event> item, State<Tuple2<Integer, Integer>> state) {
        if (state.isTimingOut() || !item.isPresent()) {
            return Optional.empty();
        }
        else {
            Event event = item.get();
            int homeScore, awayScore;
            try {
                homeScore = Integer.parseInt(event.getHomeScore());
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
            try {
                awayScore = Integer.parseInt(event.getAwayScore());
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
            if (state.exists()) {
                int homeDiff = homeScore - state.get()._1;
                int awayDiff = awayScore - state.get()._2;
                if (homeDiff != 0 || awayDiff != 0) {
                    state.update(new Tuple2<>(homeScore, awayScore));
                    EventScoreChange change = new EventScoreChange();
                    change.setEventid(event.getEventId());
                    change.setTimestamp(event.getTimestamp());
                    change.setClocktime(event.getClockTime());
                    change.setHome(homeScore);
                    change.setAway(awayScore);
                    change.setHomediff(homeDiff);
                    change.setAwaydiff(awayDiff);
                    return Optional.of(change);
                }
                else {
                    return Optional.empty();
                }
            }
            else {
                state.update(new Tuple2<>(homeScore, awayScore));
                return Optional.empty();
            }
        }
    }
}
