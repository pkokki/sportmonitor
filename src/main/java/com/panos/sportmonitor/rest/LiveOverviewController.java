package com.panos.sportmonitor.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panos.sportmonitor.dto.Event;
import com.panos.sportmonitor.dto.LiveOverview;
import com.panos.sportmonitor.kafka.OverviewProducer;
import com.panos.sportmonitor.kafka.RadarProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LiveOverviewController {
    private final OverviewProducer overviewProducer;
    private final RadarProducer radarProducer;

    @Autowired
    LiveOverviewController(OverviewProducer overviewProducer, RadarProducer radarProducer) {
        this.overviewProducer = overviewProducer;
        this.radarProducer = radarProducer;
    }

    @GetMapping(value = "/live")
    String index(@RequestParam(defaultValue = "world", required = false) String name) {
        System.out.println(String.format("LiveOverviewController index %s", name));
        return String.format("Hello %s!", name);
    }

    @PostMapping(value = "/live", consumes = "text/plain")
    String newOverview(@RequestBody String payload) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        LiveOverview overview = mapper.readValue(payload, LiveOverview.class);
        for (Event event : overview.getEvents()) {
            this.overviewProducer.sendMessage(event);
        }
        return "OK";
    }

    @PostMapping(value = "/sportradar", consumes = "text/plain")
    String sportradar(@RequestBody String message) {
        this.radarProducer.sendMessage(message);
        return "OK";
    }
}
