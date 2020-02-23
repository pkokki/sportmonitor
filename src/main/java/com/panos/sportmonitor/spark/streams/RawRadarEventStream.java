package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.dto.RawRadarEvent;
import org.apache.spark.streaming.api.java.JavaDStream;

public class RawRadarEventStream extends AbstractJavaStream<RawRadarEvent> {
    public RawRadarEventStream(JavaDStream<RawRadarEvent> stream) {
        super(stream);
    }
}
