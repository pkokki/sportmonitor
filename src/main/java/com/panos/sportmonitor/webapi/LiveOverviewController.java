package com.panos.sportmonitor.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panos.sportmonitor.spark.util.PostgresHelper;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.store.SqlExecutor;
import com.panos.sportmonitor.stats.store.StoreCounterListener;
import com.panos.sportmonitor.webapi.kafka.OverviewProducer;
import com.panos.sportmonitor.webapi.kafka.RadarProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        final ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = mapper.readTree(message);
        } catch (JsonProcessingException e) {
            StatsConsole.printlnWarn("sportradar: invalid json");
            return "ERROR";
        }
        StatsFileWriter statsFileWriter = new StatsFileWriter("C:\\panos\\betting\\radar\\logs\\", json, true);
        StatsStore store = new StatsStore();
        store.addListener(new StoreCounterListener());
        SqlExecutor sqlExecutor = new SqlExecutor(true,false);
        sqlExecutor.addSqlExecutorListener(statsFileWriter);
        store.addListener(sqlExecutor);
        StatsParser parser = new StatsParser(store);
        parser.addListener(statsFileWriter);
        parser.parse(json);
        store.submitChanges();
        return "OK";
    }

    @PostMapping(value = "/coupon", consumes = "text/plain")
    String coupon(@RequestBody String payload) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(payload);
        long stamp = json.get("stamp").asLong();

        List<String> statements = new ArrayList<>();
        for (JsonNode eventNode : json.get("events")) {
            long eventId = eventNode.get("id").asLong();
            long betRadarId = eventNode.get("id").asLong();
            String title = eventNode.get("title").asText().replace("'", "''");
            String href = eventNode.get("href").asText().replace("'", "''");
            String country = eventNode.get("country").asText().replace("'", "''");
            String league = eventNode.get("league").asText().replace("'", "''");
            long eventTime = eventNode.get("eventTime").asLong();
            String stats = eventNode.get("stats").asText().replace("'", "''");
            boolean live = eventNode.get("live").asBoolean();
            statements.add(String.format("INSERT INTO coupon_events (eventid, stamp, betradarid, title, href, country, league, eventtime, stats, live) VALUES (%d, %d, %d, '%s', '%s', '%s', '%s', %d, '%s', %b)",
                    eventId, stamp, betRadarId, title, href, country, league, eventTime, stats, live));
            for (JsonNode marketNode : eventNode.get("markets")) {
                long marketId = marketNode.get("id").asLong();
                String marketType = marketNode.get("type").asText().replace("'", "''");
                String handicap = marketNode.has("handicap") ? marketNode.get("handicap").asText() : null;
                statements.add(String.format("INSERT INTO coupon_markets (marketid, eventid, stamp, type, handicap) VALUES (%d, %d, %d, '%s', %s)",
                        marketId, eventId, stamp, marketType, handicap != null ? handicap : "null"));
                for (JsonNode selNode : marketNode.get("selections")) {
                    long selId = selNode.get("id").asLong();
                    String selType = selNode.get("type").asText().replace("'", "''");
                    double selPrice = selNode.get("price").asDouble();
                    statements.add(String.format(Locale.ROOT, "INSERT INTO coupon_selections (id, marketid, stamp, type, price) VALUES (%d, %d, %d, '%s', %.2f)",
                            selId, marketId, stamp, selType, selPrice));
                }
            }
        }
        if (!statements.isEmpty()) PostgresHelper.execute(statements);
        return "OK";
    }
}
