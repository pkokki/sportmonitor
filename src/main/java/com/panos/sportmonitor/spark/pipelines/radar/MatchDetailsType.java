package com.panos.sportmonitor.spark.pipelines.radar;

import java.io.Serializable;

public class MatchDetailsType implements Serializable {
    private String key;
    private String name;

    public MatchDetailsType(String key, String name) {
        this.setKey(key);
        this.setName(name);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
