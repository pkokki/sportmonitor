package com.panos.sportmonitor.spark.pipelines;

import com.panos.sportmonitor.spark.streams.DataPointStream;
import com.panos.sportmonitor.spark.streams.RawOverviewEventStream;
import com.panos.sportmonitor.spark.streams.RawRadarEventStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class RawCompositePipeline implements Serializable {

    public void init(JavaStreamingContext streamingContext) {

    }

    public void run(RawOverviewEventStream rawOverviewEventStream, RawRadarEventStream rawRadarEventStream) {

        DataPointStream dps1 = rawOverviewEventStream.createDataPointStream();
        dps1.output(dps1::appendToDataPointTable);
        //dps1.print();
        DataPointStream dps2 = rawRadarEventStream.createDataPointStream();
        dps2.output(dps2::appendToDataPointTable);
        dps2.print();

        //DataPointStream dataPointStream = new DataPointStream(dps1.union(dps2));
    }

}
