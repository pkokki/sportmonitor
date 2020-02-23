package com.panos.sportmonitor.spark.pipelines;

import com.panos.sportmonitor.spark.streams.RawOverviewEventStream;
import com.panos.sportmonitor.spark.streams.RawRadarEventStream;
import org.springframework.stereotype.Service;

@Service
public class RawRadarEventPipeline {
    public void run(RawOverviewEventStream rawOverviewEventStream, RawRadarEventStream rawRadarEventStream) {

    }
}
