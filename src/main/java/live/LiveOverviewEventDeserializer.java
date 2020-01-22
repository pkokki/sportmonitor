package live;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class LiveOverviewEventDeserializer implements Deserializer<LiveOverview.Event> {
    private final static Logger logger = LoggerFactory.getLogger(LiveOverviewEventDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public LiveOverview.Event deserialize(String topic, byte[] data) {
        LiveOverview.Event event = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            event = mapper.readValue(data, LiveOverview.Event.class);
        } catch (IOException e) {
            logger.error("Failed to deserialize object: " + data.toString(), e);
        }
        return event;
    }

    @Override
    public LiveOverview.Event deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

    @Override
    public void close() {
    }
}
