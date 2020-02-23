package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.dto.EventData;
import com.panos.sportmonitor.spark.dto.EventScoreChange;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import scala.Tuple2;

public class EventDataStream extends AbstractJavaStream<EventData>  {
    public EventDataStream(JavaDStream<EventData> stream) {
        super(stream);
    }

    public void appendToEventDataTable(JavaRDD<EventData> rdd, Time time) {
        if (!rdd.isEmpty()) {
            Dataset<Row> ds = SparkSession.active().createDataFrame(rdd, EventData.class);
            PostgresHelper.appendDataset(ds, "event_data");
        }
    }

    public EventScoreChangeStream createEventScoreChangeStream() {
        JavaDStream<EventScoreChange> stream = this
                .mapToPair(e -> new Tuple2<>(e.getEventId(), e))
                .mapWithState(StateSpec.function(EventDataStream::eventScoreChangeStateMapping).timeout(Durations.minutes(120)))
                .filter(r -> r.isPresent())
                .map(r -> r.get());
        return new EventScoreChangeStream(stream);
    }

    private static Optional<EventScoreChange> eventScoreChangeStateMapping(Long id, Optional<EventData> item, State<Tuple2<Integer, Integer>> state) {
        if (state.isTimingOut() || !item.isPresent()) {
            return Optional.empty();
        }
        else {
            EventData event = item.get();
            int homeScore = event.getHomeScore();
            int awayScore = event.getAwayScore();
            if (state.exists()) {
                int homeDiff = homeScore - state.get()._1;
                int awayDiff = awayScore - state.get()._2;
                if (homeDiff != 0 || awayDiff != 0) {
                    state.update(new Tuple2<>(homeScore, awayScore));
                    EventScoreChange change = new EventScoreChange(
                            event.getEventId(),
                            event.getTimestamp(),
                            event.getClockTime(),
                            homeScore, awayScore,
                            homeDiff, awayDiff
                    );
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
