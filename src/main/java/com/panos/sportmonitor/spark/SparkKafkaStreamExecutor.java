package com.panos.sportmonitor.spark;

import com.panos.sportmonitor.spark.pipelines.RawOverviewEventPipeline;
import com.panos.sportmonitor.spark.pipelines.RawRadarEventPipeline;
import com.panos.sportmonitor.spark.sources.KafkaRadarSource;
import com.panos.sportmonitor.spark.sources.KafkaOverviewSource;
import com.panos.sportmonitor.spark.streams.RawOverviewEventStream;
import com.panos.sportmonitor.spark.streams.RawRadarEventStream;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.spark.util.SparkStreamingListener;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.scheduler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;

@Component
public class SparkKafkaStreamExecutor implements Serializable, Runnable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SparkKafkaStreamExecutor.class);

    @Value("${spark.batch-duration-millis}")
    private long batchDurationMillis;
    @Value("${spark.checkpoint-directory}")
    private String checkpointDir;
    @Value("${spark.log-level}")
    private String logLevel;
    @Value("${spark.app-name}")
    private String appName;
    @Value("${spark.master-url}")
    private String masterUrl;


    @Autowired
    private KafkaOverviewSource kafkaOverviewSource;
    @Autowired
    private KafkaRadarSource kafkaRadarSource;

    @Autowired
    private RawRadarEventPipeline rawRadarEventPipeline;
    @Autowired
    private RawOverviewEventPipeline rawOverviewEventPipeline;

    @Override
    public void run() {
        System.out.println("Initializing Spark");

        // winutils.exe workaround
        System.setProperty("hadoop.home.dir", new File(".").getAbsolutePath());

        // Create a SparkSession and a JavaSparkContext
        SparkSession spark = SparkSession.builder()
                .master(masterUrl)
                .appName(appName)
                .config("spark.executor.extraJavaOptions", "-Dlog4j.configuration=spark-log4j.properties")
                .config("spark.driver.extraJavaOptions",   "-Dlog4j.configuration=spark-log4j.properties")
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Configure and initialize the SparkStreamingContext
        JavaStreamingContext streamingContext = new JavaStreamingContext(jsc, new Duration(batchDurationMillis));
        streamingContext.sparkContext().setLogLevel(logLevel);
        streamingContext.checkpoint(checkpointDir);
        // Listener setup
        StreamingListener listener = new SparkStreamingListener(batchDurationMillis);
        streamingContext.addStreamingListener(listener);
        // Postgres
        PostgresHelper.init();

        // Source streams
        RawOverviewEventStream rawOverviewEventStream = kafkaOverviewSource.createRawOverviewEventStream(streamingContext);
        RawRadarEventStream rawRadarEventStream = kafkaRadarSource.run(streamingContext);

        // Processing pipelines
        rawOverviewEventPipeline.run(rawOverviewEventStream);
        rawRadarEventPipeline.run(rawOverviewEventStream, rawRadarEventStream);

        // Execute the Spark workflow defined above
        streamingContext.start();
    }
}
