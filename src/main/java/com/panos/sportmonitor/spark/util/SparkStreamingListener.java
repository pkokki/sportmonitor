package com.panos.sportmonitor.spark.util;

import org.apache.spark.streaming.scheduler.*;
import org.apache.spark.util.Utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SparkStreamingListener implements StreamingListener {
    private final long batchDurationMillis;
    private long firstBatchMillis;

    public SparkStreamingListener(long batchDurationMillis) {
        this.batchDurationMillis = batchDurationMillis;
        this.firstBatchMillis = 0;
    }

    private long getBatchNum(long ms) {
        if (firstBatchMillis == 0)
            firstBatchMillis = ms;
        return ((ms - firstBatchMillis) / batchDurationMillis) + 1;
    }

    @Override
    public void onStreamingStarted(StreamingListenerStreamingStarted args) {
        System.out.println(String.format("[****] %d %s",
                args.time(),
                args.productPrefix()
        ));
    }

    @Override
    public void onReceiverStarted(StreamingListenerReceiverStarted args) {
        System.out.println(String.format("%s", args.productPrefix()));
    }

    @Override
    public void onReceiverError(StreamingListenerReceiverError args) {
        System.out.println(String.format("%s", args.productPrefix()));
    }

    @Override
    public void onReceiverStopped(StreamingListenerReceiverStopped args) {
        System.out.println(String.format("%s", args.productPrefix()));
    }

    @Override
    public void onBatchSubmitted(StreamingListenerBatchSubmitted args) {
        BatchInfo info = args.batchInfo();
        System.out.println(String.format("[%4d] %d %s: records=%d -----------------------------------------------------",
                getBatchNum(info.batchTime().milliseconds()),
                info.submissionTime(),
                args.productPrefix(),
                info.numRecords()
        ));
    }

    @Override
    public void onBatchStarted(StreamingListenerBatchStarted args) {
        BatchInfo info = args.batchInfo();
        System.out.println(String.format("[%4d] %d %s, delay = %s",
                getBatchNum(info.batchTime().milliseconds()),
                info.processingStartTime().get(),
                args.productPrefix(),
                info.schedulingDelay().get()
        ));
    }

    @Override
    public void onBatchCompleted(StreamingListenerBatchCompleted args) {
        BatchInfo info = args.batchInfo();
        System.out.println(String.format("[%4d] %d %s, delay = %s + %s = %s",
                getBatchNum(info.batchTime().milliseconds()),
                info.processingEndTime().get(),
                args.productPrefix(),
                info.schedulingDelay().get(),
                info.processingDelay().get(),
                info.totalDelay().get()
        ));
    }

    @Override
    public void onOutputOperationStarted(StreamingListenerOutputOperationStarted args) {
        /*
        OutputOperationInfo info = args.outputOperationInfo();
        System.out.println(String.format("[%4d] %s %s: id=%d, name=%s",
                getBatchNum(info.batchTime().milliseconds()),
                info.startTime().get(),
                args.productPrefix(),
                info.id(),
                info.name()
        ));
         */
    }

    @Override
    public void onOutputOperationCompleted(StreamingListenerOutputOperationCompleted args) {
        OutputOperationInfo info = args.outputOperationInfo();
        System.out.println(String.format("[%4d] %s %s: [%d] %5s %s%s",
                getBatchNum(info.batchTime().milliseconds()),
                info.endTime().get(),
                args.productPrefix(),
                info.id(),
                info.duration().get(),
                Arrays.stream(info.description().split("\n")).filter(s -> s.contains("com.panos.sportmonitor.spark.pipelines")).findFirst().get().substring(39),
                info.failureReason().isEmpty() ? "" : String.format("\n%s", getFailureReason(info.failureReason().get()))
        ));
    }

    private String getFailureReason(String input) {
        input = input.substring(0, input.indexOf("Driver stacktrace:"));
        return Arrays.stream(input.split("\n"))
                .filter(s -> s.length() > 0 && !s.startsWith("\tat ") && !s.startsWith("\t..."))
                .map(s -> "       " + s)
                .collect(Collectors.joining("\n"));
    }
}
