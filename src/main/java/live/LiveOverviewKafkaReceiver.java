package live;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;
import scala.Tuple3;

import java.io.File;
import java.util.*;

class LiveOverviewKafkaReceiver {
    private static final String CHECKPOINT_DIR = "/panos/docker/storage/spark/checkpoints/live-overview";
    private static final Duration BATCH_DURATION = Durations.seconds(5);
    private final static Logger logger = LoggerFactory.getLogger(LiveOverviewKafkaReceiver.class);

    static void start() throws InterruptedException {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", LiveOverviewEventDeserializer.class);
        kafkaParams.put("group.id", "overviews_stream");
        kafkaParams.put("auto.offset.reset", "latest"); // earliest, latest, none
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singletonList("OVERVIEWS");

        // winutils.exe workaround
        File workaround = new File(".");
        System.getProperties().put("hadoop.home.dir", workaround.getAbsolutePath());

        // Create a SparkSession and a JavaSparkContext
        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("LiveOverview")
                //.config("spark.some.config.option", "some-value")
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        jsc.setLogLevel("WARN");

        // Configure and initialize the SparkStreamingContext
        JavaStreamingContext streamingContext = new JavaStreamingContext(jsc, BATCH_DURATION);
        streamingContext.sparkContext().setLogLevel("WARN");
        streamingContext.checkpoint(CHECKPOINT_DIR);

        // Create live events stream from kafka
        JavaInputDStream<ConsumerRecord<String, LiveOverview.Event>> eventRecordDS =
                KafkaUtils.createDirectStream(
                        streamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );

        JavaDStream<LiveOverview.Event> eventsDS = eventRecordDS.map(r -> r.value());
        eventsDS.print();

        // Append to db event_data
        eventsDS
                .map(e -> new LiveOverview.EventRecord(e))
                .foreachRDD(rdd -> {
                    if (!rdd.isEmpty()) {
                        Dataset<Row> ds = spark.createDataFrame(rdd, LiveOverview.EventRecord.class);
                        LiveOverviewSql.appendDataset(ds, "event_data");
                    }
                });

        // Append to db market_data
        JavaDStream<LiveOverview.MarketRecord> marketRecords = eventsDS
                .flatMap(e -> {
                    List<LiveOverview.MarketRecord> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> list.add(new LiveOverview.MarketRecord(e.getId(), e.getTimestamp(), m)));
                    return list.iterator();
                });
        marketRecords.print();

        marketRecords.foreachRDD(rdd -> {
                    if (!rdd.isEmpty()) {
                        Dataset<Row> ds = spark.createDataFrame(rdd, LiveOverview.MarketRecord.class);
                        LiveOverviewSql.appendDataset(ds, "market_data");
                    }
                });

        // Append to db selection_data
        JavaDStream<LiveOverview.SelectionRecord> selectionRecords = eventsDS
                .flatMap(e -> {
                    List<LiveOverview.SelectionRecord> list = new ArrayList<>();
                    e.getMarkets().forEach(m -> {
                        m.getSelections().forEach(s -> {
                            list.add(new LiveOverview.SelectionRecord(e.getId(), e.getTimestamp(), m.getId(), s));
                        });
                    });
                    return list.iterator();
                });
        selectionRecords.print();
        selectionRecords.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, LiveOverview.SelectionRecord.class);
                LiveOverviewSql.appendDataset(ds, "selection_data");
            }
        });

        // Apply the state update function to the events streaming Dataset grouped by eventId
        JavaMapWithStateDStream<Long, LiveOverview.Event, LiveOverview.EventState, LiveOverview.EventMaster> eventUpdates = eventsDS
                .mapToPair(e -> new Tuple2<>(Long.parseLong(e.getId()), e))
                .mapWithState(StateSpec.function(LiveOverviewFunctions.MappingFunc).timeout(Durations.minutes(1)));
        eventUpdates.print();

        // Overwrite db table event_master_data
        eventUpdates.foreachRDD(rdd -> {
            if (!rdd.isEmpty()) {
                Dataset<Row> ds = spark.createDataFrame(rdd, LiveOverview.EventMaster.class);
                LiveOverviewSql.overwriteDataset(ds, "event_master_data");
            }
        });

        // Execute the Spark workflow defined above
        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
