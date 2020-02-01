package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.dto.Event;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.streaming.State;

class StateFunctions {

    static Function3<Long, Optional<Event>, State<EventState>, EventMaster> MappingFunc =
            (Function3<Long, Optional<Event>, State<EventState>, EventMaster>) (eventId, event, state) -> {
                // If timed out, then remove event and send final update
                if (state.isTimingOut()) {
                    return new EventMaster(eventId, 0, 0, 0, true);
                }
                else {
                    // Find max and min timestamps in events
                    long maxTimestampMs = Long.MIN_VALUE;
                    long minTimestampMs = Long.MAX_VALUE;
                    int numNewEvents = 0;
                    long timestampMs = event.get().getTimestamp();
                    maxTimestampMs = Math.max(timestampMs, maxTimestampMs);
                    minTimestampMs = Math.min(timestampMs, minTimestampMs);
                    numNewEvents += 1;
                    EventState newState = new EventState();

                    // Update start and end timestamps in session
                    if (state.exists()) {
                        EventState prevState = state.get();
                        newState.setNumEvents(prevState.getNumEvents() + numNewEvents);
                        newState.setStartTimestampMs(prevState.getStartTimestampMs());
                        newState.setEndTimestampMs(Math.max(prevState.getEndTimestampMs(), maxTimestampMs));
                    } else {
                        newState.setNumEvents(numNewEvents);
                        newState.setStartTimestampMs(minTimestampMs);
                        newState.setEndTimestampMs(maxTimestampMs);
                    }
                    state.update(newState);
                    return new EventMaster(
                            eventId, maxTimestampMs, state.get().calculateDuration(), state.get().getNumEvents(), false);
                }
            };
}
