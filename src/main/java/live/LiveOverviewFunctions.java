package live;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.streaming.State;

import java.util.Iterator;

public class LiveOverviewFunctions {

    static Function3<String, Optional<LiveOverview.Event>, State<LiveOverview.EventInfo>, LiveOverview.EventUpdate> MappingFunc =
            new Function3<String, Optional<LiveOverview.Event>, State<LiveOverview.EventInfo>, LiveOverview.EventUpdate>() {
                @Override
                public LiveOverview.EventUpdate call(String eventId, Optional<LiveOverview.Event> events, State<LiveOverview.EventInfo> state) throws Exception {
                    // If timed out, then remove event and send final update
                    if (state.isTimingOut()) {
                        LiveOverview.EventUpdate finalUpdate = new LiveOverview.EventUpdate(
                                eventId, 0, 0, true);
                        return finalUpdate;
                    }
                    else {
                        // Find max and min timestamps in events
                        long maxTimestampMs = Long.MIN_VALUE;
                        long minTimestampMs = Long.MAX_VALUE;
                        int numNewEvents = 0;
                        long timestampMs = events.get().getTimestamp();
                        maxTimestampMs = Math.max(timestampMs, maxTimestampMs);
                        minTimestampMs = Math.min(timestampMs, minTimestampMs);
                        numNewEvents += 1;
                        LiveOverview.EventInfo updatedSession = new LiveOverview.EventInfo();

                        // Update start and end timestamps in session
                        if (state.exists()) {
                            LiveOverview.EventInfo oldSession = state.get();
                            updatedSession.setNumEvents(oldSession.getNumEvents() + numNewEvents);
                            updatedSession.setStartTimestampMs(oldSession.getStartTimestampMs());
                            updatedSession.setEndTimestampMs(Math.max(oldSession.getEndTimestampMs(), maxTimestampMs));
                        } else {
                            updatedSession.setNumEvents(numNewEvents);
                            updatedSession.setStartTimestampMs(minTimestampMs);
                            updatedSession.setEndTimestampMs(maxTimestampMs);
                        }
                        state.update(updatedSession);
                        return new LiveOverview.EventUpdate(
                                eventId, state.get().calculateDuration(), state.get().getNumEvents(), false);
                    }
                }
            };
}
