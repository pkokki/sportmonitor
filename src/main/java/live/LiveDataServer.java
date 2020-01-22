package live;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiveDataServer {
    public static void main(String[] args) throws InterruptedException {
        Logger.getRootLogger().setLevel(Level.WARN);
        Logger.getLogger("org").setLevel(Level.WARN);
        Logger.getLogger("akka").setLevel(Level.WARN);
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.apache.spark.streaming").setLevel(Level.WARN);

        SpringApplication.run(LiveDataServer.class, args);
        LiveOverviewKafkaReceiver.start();
    }
}
