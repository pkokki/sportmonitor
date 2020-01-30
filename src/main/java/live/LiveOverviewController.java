package live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LiveOverviewController {
    LiveOverviewController() {
        System.out.println("LiveOverviewController constructor.");
    }

    // curl localhost:8080/live
    @GetMapping(value = "/live")
    String index(@RequestParam(defaultValue = "world", required = false) String name) {
        System.out.println(String.format("LiveOverviewController index %s", name));
        return String.format("Hello %s!", name);
    }

    // curl -H "Content-Type: text/plain" -d "{ Message: 2 }" localhost:8080/live
    @PostMapping(value = "/live", consumes = "text/plain")
    String newOverview(@RequestBody String overview) throws JsonProcessingException {
        //System.out.println("LiveOverviewController newOverview.");
        //LiveOverviewStreamer.put(overview);
        final ObjectMapper mapper = new ObjectMapper();
        LiveOverview overviewObj = mapper.readValue(overview, LiveOverview.class);

        //LiveOverviewKafkaSender ks = new LiveOverviewKafkaSender("localhost:9092");
        LiveOverviewKafkaSender.send(overviewObj.getEvents());
        return "OK";
    }

    @PostMapping(value = "/sportradar", consumes = "text/plain")
    String sportradar(@RequestBody String data) {
        LiveOverviewKafkaSender.send("RADAR", data);
        return "OK";
    }
}
