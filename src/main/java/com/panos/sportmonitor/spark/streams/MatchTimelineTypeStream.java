package com.panos.sportmonitor.spark.streams;


import com.panos.sportmonitor.spark.dto.MatchTimelineType;
import org.apache.spark.streaming.api.java.JavaDStream;

public class MatchTimelineTypeStream extends AbstractJavaStream<MatchTimelineType> {
    public MatchTimelineTypeStream(JavaDStream<MatchTimelineType> stream) {
        super(stream);
    }
}
