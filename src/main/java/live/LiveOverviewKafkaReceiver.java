package live;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.io.File;
import java.util.*;

class LiveOverviewKafkaReceiver {
    private static final String CHECKPOINT_DIR = "/panos/docker/storage/spark/checkpoints/live-overview";
    private static final Duration BATCH_DURATION = Durations.seconds(5);

    static void start() throws InterruptedException {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", LiveOverviewEventDeserializer.class);
        kafkaParams.put("group.id", "overviews_stream");
        kafkaParams.put("auto.offset.reset", "earliest"); // earliest, latest, none
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singletonList("OVERVIEWS");

        // winutils.exe workaround
        File workaround = new File(".");
        System.getProperties().put("hadoop.home.dir", workaround.getAbsolutePath());

        // Configure and initialize the SparkStreamingContext
        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("LiveOverview");
        JavaStreamingContext streamingContext = new JavaStreamingContext(conf, BATCH_DURATION);
        streamingContext.sparkContext().setLogLevel("WARN");
        streamingContext.checkpoint(CHECKPOINT_DIR);

        // Create live events stream from kafka
        JavaInputDStream<ConsumerRecord<String, LiveOverview.Event>> eventRecordDS =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );
        JavaPairDStream<String, LiveOverview.Event> eventsDS = eventRecordDS
                .mapToPair(record -> new Tuple2<>(record.key(), record.value()));
        eventsDS.print(100);

        // Execute the Spark workflow defined above
        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
