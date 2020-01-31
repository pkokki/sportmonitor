package live;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class RadarJsonEvent implements Serializable {
    public String queryUrl;
    public String event;
    public long dob;
    public int maxage;
    public JsonNode data;

    public RadarJsonEvent(String queryUrl, String event, long dob, int maxage, JsonNode data) {
        this.queryUrl = queryUrl;
        this.event = event;
        this.dob = dob;
        this.maxage = maxage;
        this.data = data;
    }
}
