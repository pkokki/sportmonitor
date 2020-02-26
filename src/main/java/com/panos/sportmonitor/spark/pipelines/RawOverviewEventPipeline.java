package com.panos.sportmonitor.spark.pipelines;

import com.panos.sportmonitor.spark.dto.SelectionData;
import com.panos.sportmonitor.spark.streams.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.stereotype.Service;
import scala.Serializable;

@Service
public class RawOverviewEventPipeline implements Serializable {

    private RawOverviewEventPipelineListener listener;

    public void init(JavaStreamingContext streamingContext) {
        listener = new RawOverviewEventPipelineListener(streamingContext);
        streamingContext.addStreamingListener(listener);
    }

    public void run(RawOverviewEventStream rawOverviewEventStream) {

        EventMasterDataStream eventMasterDataStream = rawOverviewEventStream.createEventMasterDataStream(listener::getSessionStamp);
        eventMasterDataStream.output(eventMasterDataStream::appendToEventMasterTable);

        MarketMasterDataStream marketMasterDataStream = rawOverviewEventStream.createMarketMasterDataStream(listener::getSessionStamp);
        marketMasterDataStream.output(marketMasterDataStream::appendToMarketMasterTable);

        SelectionMasterDataStream selectionMasterDataStream = rawOverviewEventStream.createSelectionMasterDataStream(listener::getSessionStamp);
        selectionMasterDataStream.output(selectionMasterDataStream::appendToSelectionMasterTable);

        SelectionDataStream selectionDataStream = rawOverviewEventStream.createSelectionDataStream();
        selectionDataStream.foreachRDD((rdd, time) -> {
            Dataset<Row> activeSelections = SparkSession.active().createDataFrame(rdd, SelectionData.class);
            selectionDataStream.appendToSelectionDataTable(activeSelections);
            //selectionDataStream.overwriteActiveSelections(activeSelections);
            //selectionDataStream.generateBets(rdd.filter(s -> s.getActive()).collect(), 10);
        });

        EventDataStream eventData = rawOverviewEventStream.createEventDataStream();
        eventData.output(eventData::appendToEventDataTable);

        EventScoreChangeStream eventScoreChangeStream = eventData.createEventScoreChangeStream();
        eventScoreChangeStream.output(eventScoreChangeStream::appendToEventScoreChangesTable);
    }
}

