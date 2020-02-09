package com.panos.sportmonitor.spark;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public abstract class SparkStreamingTest  {
    private static final String APP_NAME = "SportMonitorTest";
    private static final boolean ONE_AT_TIME = true;
    private static final long BATCH_INTERVAL = 1000L;
    private static final SparkConf CONFIGURATION =
            new SparkConf().setAppName(APP_NAME).setMaster("local[2]");
    private static String checkpointDir;
    private JavaSparkContext batchContext;
    protected JavaStreamingContext streamingContext;
    private long timeout;

    @BeforeAll
    public static void setUpClass() throws IOException {
        // winutils.exe workaround
        System.setProperty("hadoop.home.dir", new File(".").getAbsolutePath());
        checkpointDir = Files.createTempDirectory(APP_NAME).toString();
    }

    @AfterAll
    public static void tearDownClass() throws IOException {
        FileUtils.deleteDirectory(new File(checkpointDir));
    }

    @BeforeEach
    public void initContext() {
        System.out.println(String.format("%s init start", java.time.Instant.now()));
        batchContext = new JavaSparkContext(CONFIGURATION);
        streamingContext = new JavaStreamingContext(batchContext, Durations.milliseconds(BATCH_INTERVAL));
        streamingContext.checkpoint(checkpointDir);
        timeout= 0L;
        System.out.println(String.format("%s init end, checkpointDir=%s", java.time.Instant.now(), checkpointDir));
    }

    @AfterEach
    public void stopContext() {
        System.out.println(String.format("%s stop start", java.time.Instant.now()));
        streamingContext.stop(true, true);
        System.out.println(String.format("%s stop end", java.time.Instant.now()));
    }

    protected <TIn> JavaInputDStream<TIn> setupSource(List<List<TIn>> batches) {
        System.out.println(String.format("%s test init", java.time.Instant.now()));
        Queue<JavaRDD<TIn>> testQueue = new LinkedList<>();
        Iterator<List<TIn>> it = batches.iterator();
        while (it.hasNext()) {
            List<TIn> list = it.next();
            JavaRDD<TIn> newRDD = batchContext.parallelize(list);
            testQueue.add(newRDD);
        }
        JavaInputDStream<TIn> source = streamingContext.queueStream(testQueue, ONE_AT_TIME);
        timeout = (batches.size() + 1) * BATCH_INTERVAL;
        return source;
    }

    protected <TOut> List<TOut> collectOutput(JavaDStream<TOut> output) throws InterruptedException {
        List<TOut> outputData = new ArrayList<>();
        output.foreachRDD(rdd -> outputData.addAll(rdd.collect()));
        System.out.println(String.format("%s test start", java.time.Instant.now()));
        streamingContext.start();
        streamingContext.awaitTerminationOrTimeout(timeout);
        System.out.println(String.format("%s test terminate", java.time.Instant.now()));
        return outputData;
    }
}
