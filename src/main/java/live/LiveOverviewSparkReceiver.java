package live;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.io.File;

public class LiveOverviewSparkReceiver {
    private static final String HOST = "localhost";
    private static final int PORT = 9999;
    private static final String CHECKPOINT_DIR = "/tmp/spark-live-overview";
    private static final Duration BATCH_DURATION = Durations.seconds(5);

    static void start() throws InterruptedException {
        System.out.println("LiveOverviewSparkReceiver start.");
        File workaround = new File(".");
        System.getProperties().put("hadoop.home.dir", workaround.getAbsolutePath());

        // Configure and initialize the SparkStreamingContext
        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("LiveOverview");
        JavaStreamingContext streamingContext = new JavaStreamingContext(conf, BATCH_DURATION);
        streamingContext.sparkContext().setLogLevel("WARN");
        streamingContext.checkpoint(CHECKPOINT_DIR);

        // Receive streaming data from the source
        JavaReceiverInputDStream<String> inputOverviews = streamingContext.socketTextStream(HOST, PORT);
        //inputOverviews.print();

        // Transform string input messages to LiveOverview messages
        final ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaDStream<LiveOverview> objOverviews = inputOverviews.map(x -> mapper.readValue(x, LiveOverview.class));
        //objOverviews.print();

        // Flatten and Group by event id
        JavaPairDStream<String, Iterable<LiveOverview.Event>> eventsDS = objOverviews
                .flatMap(e -> e.getEvents().iterator())
                .mapToPair(e -> new Tuple2<>(e.getId(), e))
                .groupByKey();
        eventsDS.print(100);



        // Execute the Spark workflow defined above
        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
