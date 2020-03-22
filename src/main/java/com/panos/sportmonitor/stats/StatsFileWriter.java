package com.panos.sportmonitor.stats;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.panos.sportmonitor.stats.store.SqlExecutorListener;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class StatsFileWriter implements SqlExecutorListener, StatsParserListener {
    private final String path;
    private final JsonNode json;
    private final boolean withFailsOnly;
    private boolean saved = false;

    public StatsFileWriter(String path, JsonNode json, boolean withFailsOnly) {
        this.path = path;
        this.json = json;
        this.withFailsOnly = withFailsOnly;
    }

    @Override
    public void onSqlExecutorCompleted(int total, int succeeded, int failed) {
        if ((withFailsOnly && failed == 0) || total == 0)
            return;
         save();
    }

    private synchronized void save() {
        if (saved) return;
        saved = true;
        String fileName = path
                + json.get("radarUrl").asText().replace("/", "#")
                + "#"
                + json.get("_dob").asLong()
                + ".json";
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File(fileName), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onParserError(String code, String message) {
        save();
    }
}
