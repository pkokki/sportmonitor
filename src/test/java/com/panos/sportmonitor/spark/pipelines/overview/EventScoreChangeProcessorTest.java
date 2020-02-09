package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.common.Event;
import com.panos.sportmonitor.spark.SparkStreamingTest;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventScoreChangeProcessorTest extends SparkStreamingTest {

    @Test
    void should_return_correct_output() throws InterruptedException {
        // Arrange
        List<List<Event>> batches = Arrays.asList(
                Arrays.asList(
                        new Event("1", 1, "00:01", "0", "0"),
                        new Event("2", 1, "00:01", "0", "0")
                ),
                Arrays.asList(
                        new Event("1", 2, "00:02", "1", "0"),
                        new Event("2", 2, "00:02", "0", "1")
                )
        );
        JavaInputDStream<Event> source = setupSource(batches);

        // Act
        EventScoreChangeProcessor processor = new EventScoreChangeProcessor();
        JavaDStream<EventScoreChange> output = processor.run(streamingContext, source);
        List<EventScoreChange> outputData = collectOutput(output);

        // Assert
        //outputData.stream().forEach(System.out::println);
        assertEquals(2, outputData.size());
        assertTrue(outputData.stream().anyMatch(o -> o.getEventid() == 2
                && o.getTimestamp() == 2
                && o.getClocktime().equals("00:02")
                && o.getHome() == 0
                && o.getAway() == 1
                && o.getHomediff() == 0
                && o.getAwaydiff() == 1
        ));
        assertTrue(outputData.stream().anyMatch(o -> o.getEventid() == 1L
                && o.getTimestamp() == 2L
                && o.getClocktime().equals("00:02")
                && o.getHome() == 1
                && o.getAway() == 0
                && o.getHomediff() == 1
                && o.getAwaydiff() == 0));
    }

    @Test
    void should_handle_negative_home_diff() throws InterruptedException {
        // Arrange
        List<List<Event>> batches = Arrays.asList(
                Arrays.asList(
                        new Event("200", 1000, "10:00", "1", "2")
                ),
                Arrays.asList(
                        new Event("200", 2000, "20:00", "0", "2")
                )
        );
        JavaInputDStream<Event> source = setupSource(batches);

        // Act
        EventScoreChangeProcessor processor = new EventScoreChangeProcessor();
        JavaDStream<EventScoreChange> output = processor.run(streamingContext, source);
        List<EventScoreChange> outputData = collectOutput(output);

        // Assert
        assertEquals(1, outputData.size());
        EventScoreChange change = outputData.iterator().next();
        System.out.println(change);
        assertTrue(change.getEventid() == 200
                && change.getTimestamp() == 2000
                && change.getClocktime().equals("20:00")
                && change.getHome() == 0
                && change.getAway() == 2
                && change.getHomediff() == -1
                && change.getAwaydiff() == 0
        );
    }

    @Test
    void should_handle_negative_away_diff() throws InterruptedException {
        // Arrange
        List<List<Event>> batches = Arrays.asList(
                Arrays.asList(
                        new Event("200", 1000, "10:00", "1", "2")
                ),
                Arrays.asList(
                        new Event("200", 2000, "20:00", "1", "1")
                )
        );
        JavaInputDStream<Event> source = setupSource(batches);

        // Act
        EventScoreChangeProcessor processor = new EventScoreChangeProcessor();
        JavaDStream<EventScoreChange> output = processor.run(streamingContext, source);
        List<EventScoreChange> outputData = collectOutput(output);

        // Assert
        assertEquals(1, outputData.size());
        EventScoreChange change = outputData.iterator().next();
        System.out.println(change);
        assertTrue(change.getEventid() == 200
                && change.getTimestamp() == 2000
                && change.getClocktime().equals("20:00")
                && change.getHome() == 1
                && change.getAway() == 1
                && change.getHomediff() == 0
                && change.getAwaydiff() == -1
        );
    }

    @Test
    void should_ignore_null_initial_home_score() throws InterruptedException {
        // Arrange
        List<List<Event>> batches = Arrays.asList(
                Arrays.asList(
                        new Event("200", 1000, "10:00", null, "2")
                ),
                Arrays.asList(
                        new Event("200", 2000, "20:00", "0", "2")
                )
        );
        JavaInputDStream<Event> source = setupSource(batches);

        // Act
        EventScoreChangeProcessor processor = new EventScoreChangeProcessor();
        JavaDStream<EventScoreChange> output = processor.run(streamingContext, source);
        List<EventScoreChange> outputData = collectOutput(output);

        // Assert
        assertEquals(0, outputData.size());
    }

    @Test
    void should_ignore_null_home_score() throws InterruptedException {
        // Arrange
        List<List<Event>> batches = Arrays.asList(
                Arrays.asList(
                        new Event("200", 1000, "10:00", "2", "2")
                ),
                Arrays.asList(
                        new Event("200", 2000, "20:00", null, "2")
                )
        );
        JavaInputDStream<Event> source = setupSource(batches);

        // Act
        EventScoreChangeProcessor processor = new EventScoreChangeProcessor();
        JavaDStream<EventScoreChange> output = processor.run(streamingContext, source);
        List<EventScoreChange> outputData = collectOutput(output);

        // Assert
        assertEquals(0, outputData.size());
    }
}
