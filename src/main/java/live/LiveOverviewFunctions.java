package live;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.streaming.State;

public class LiveOverviewFunctions {

    static Function3<Long, Optional<LiveOverview.Event>, State<LiveOverview.EventState>, LiveOverview.EventMaster> MappingFunc =
            new Function3<Long, Optional<LiveOverview.Event>, State<LiveOverview.EventState>, LiveOverview.EventMaster>() {
                @Override
                public LiveOverview.EventMaster call(Long eventId, Optional<LiveOverview.Event> event, State<LiveOverview.EventState> state) throws Exception {
                    // If timed out, then remove event and send final update
                    if (state.isTimingOut()) {
                        return new LiveOverview.EventMaster(eventId, 0, 0, true);
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
                        LiveOverview.EventState newState = new LiveOverview.EventState();

                        // Update start and end timestamps in session
                        if (state.exists()) {
                            LiveOverview.EventState prevState = state.get();
                            newState.setNumEvents(prevState.getNumEvents() + numNewEvents);
                            newState.setStartTimestampMs(prevState.getStartTimestampMs());
                            newState.setEndTimestampMs(Math.max(prevState.getEndTimestampMs(), maxTimestampMs));
                        } else {
                            newState.setNumEvents(numNewEvents);
                            newState.setStartTimestampMs(minTimestampMs);
                            newState.setEndTimestampMs(maxTimestampMs);
                        }
                        state.update(newState);
                        return new LiveOverview.EventMaster(
                                eventId, state.get().calculateDuration(), state.get().getNumEvents(), false);
                    }
                }
            };
}
