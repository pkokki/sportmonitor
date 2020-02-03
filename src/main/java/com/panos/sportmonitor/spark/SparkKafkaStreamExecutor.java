package com.panos.sportmonitor.spark;

import com.panos.sportmonitor.spark.pipelines.overview.PipelineOverview;
import com.panos.sportmonitor.spark.pipelines.radar.PipelineRadar;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
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
    private Long batchDurationMillis;
    @Value("${spark.checkpoint-directory}")
    private String checkpointDir;
    @Value("${spark.log-level}")
    private String logLevel;
    @Value("${spark.app-name}")
    private String appName;
    @Value("${spark.master-url}")
    private String masterUrl;

    @Autowired
    private PipelineRadar pipelineRadar;
    @Autowired
    private PipelineOverview pipelineOverview;

    @Override
    public void run() {
        // winutils.exe workaround
        System.setProperty("hadoop.home.dir", new File(".").getAbsolutePath());

        // Create a SparkSession and a JavaSparkContext
        SparkSession spark = SparkSession.builder()
                .master(masterUrl)
                .appName(appName)
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Configure and initialize the SparkStreamingContext
        JavaStreamingContext streamingContext = new JavaStreamingContext(jsc, new Duration(batchDurationMillis));
        streamingContext.sparkContext().setLogLevel(logLevel);
        streamingContext.checkpoint(checkpointDir);

        pipelineRadar.run(spark, streamingContext);
        pipelineOverview.run(spark, streamingContext);

        // Execute the Spark workflow defined above
        streamingContext.start();
    }
}
