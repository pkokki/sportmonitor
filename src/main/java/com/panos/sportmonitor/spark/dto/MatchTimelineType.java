package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class MatchTimelineType  implements Serializable {
    private long id;
    private String description;

    public MatchTimelineType(long id, String description) {
        this.setId(id);
        this.setDescription(description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MatchTimelineType)) {
            return false;
        }
        MatchTimelineType other = (MatchTimelineType) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return (int)this.id;
    }
}
