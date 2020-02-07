package com.panos.sportmonitor.spark.pipelines.radar;

import java.io.Serializable;

public class MatchDetailsType implements Serializable {
    private String key;
    private String name;

    public MatchDetailsType() {
    }

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

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MatchDetailsType)) {
            return false;
        }
        MatchDetailsType other = (MatchDetailsType) obj;
        if (this.key == null)
            return other.key == null;
        return this.key.equals(other.key);
    }

    @Override
    public int hashCode() {
        if (this.key == null)
            return 0;
        return this.key.hashCode();
    }
}
