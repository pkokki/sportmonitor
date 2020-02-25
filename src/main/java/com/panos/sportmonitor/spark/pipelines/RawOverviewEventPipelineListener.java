package com.panos.sportmonitor.spark.pipelines;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.scheduler.*;

import java.io.Serializable;

public class RawOverviewEventPipelineListener implements StreamingListener, Serializable {
    private transient final JavaStreamingContext streamingContext;
    private Broadcast<Long> sessionStamp;

    public RawOverviewEventPipelineListener(JavaStreamingContext streamingContext) {
        this.streamingContext = streamingContext;
    }

    public long getSessionStamp() {
        return sessionStamp == null ? 0 : sessionStamp.value();
    }

    @Override
    public void onStreamingStarted(StreamingListenerStreamingStarted args) {
        Long newValue = args.time() / 1000;
        sessionStamp = streamingContext.sparkContext().broadcast(newValue);
    }

    @Override
    public void onReceiverStarted(StreamingListenerReceiverStarted args) {
    }

    @Override
    public void onReceiverError(StreamingListenerReceiverError args) {
    }

    @Override
    public void onReceiverStopped(StreamingListenerReceiverStopped args) {
    }

    @Override
    public void onBatchSubmitted(StreamingListenerBatchSubmitted args) {
    }

    @Override
    public void onBatchStarted(StreamingListenerBatchStarted args) {
    }

    @Override
    public void onBatchCompleted(StreamingListenerBatchCompleted args) {
    }

    @Override
    public void onOutputOperationStarted(StreamingListenerOutputOperationStarted args) {
    }

    @Override
    public void onOutputOperationCompleted(StreamingListenerOutputOperationCompleted args) {
    }


}
