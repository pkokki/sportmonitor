package live;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

class LiveOverviewKafkaReceiver {
    private static final String CHECKPOINT_DIR = "/panos/docker/storage/spark/checkpoints/live-overview";
    private static final Duration BATCH_DURATION = Durations.seconds(5);
    private final static Logger logger = LoggerFactory.getLogger(LiveOverviewKafkaReceiver.class);

    static void start() throws InterruptedException {
        // winutils.exe workaround
        File workaround = new File(".");
        System.getProperties().put("hadoop.home.dir", workaround.getAbsolutePath());

        // Create a SparkSession and a JavaSparkContext
        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("LiveOverview")
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        jsc.setLogLevel("WARN");

        // Configure and initialize the SparkStreamingContext
        JavaStreamingContext streamingContext = new JavaStreamingContext(jsc, BATCH_DURATION);
        streamingContext.sparkContext().setLogLevel("WARN");
        streamingContext.checkpoint(CHECKPOINT_DIR);

        PipelineOverview.run(spark, streamingContext);

        // Execute the Spark workflow defined above
        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
