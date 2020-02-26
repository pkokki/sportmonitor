package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class MatchDetailsType implements Serializable {
    private String id;
    private String description;

    public MatchDetailsType(String id, String description) {
        this.setId(id);
        this.setDescription(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(obj instanceof MatchDetailsType)) {
            return false;
        }
        MatchDetailsType other = (MatchDetailsType) obj;
        if (this.id == null)
            return other.id == null;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        if (this.id == null)
            return 0;
        return this.id.hashCode();
    }
}
