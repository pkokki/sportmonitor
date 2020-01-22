package live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LiveOverviewEventSerializer implements Serializer<LiveOverview.Event> {
    private final static Logger logger = LoggerFactory.getLogger(LiveOverviewEventSerializer.class);

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, LiveOverview.Event data) {
        ObjectWriter writer = new ObjectMapper().writer();
        byte[] jsonBytes = new byte[0];

        try {
            jsonBytes = writer.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize object", e);
        }
        return jsonBytes;
    }

    @Override
    public void close() {
    }
}
