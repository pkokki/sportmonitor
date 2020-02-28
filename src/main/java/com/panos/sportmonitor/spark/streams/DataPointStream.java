package com.panos.sportmonitor.spark.streams;

import com.panos.sportmonitor.spark.dto.DataPoint;
import org.apache.spark.streaming.api.java.JavaDStream;

public class DataPointStream extends AbstractJavaStream<DataPoint>  {
    public DataPointStream(JavaDStream<DataPoint> stream) {
        super(stream);
    }
}
