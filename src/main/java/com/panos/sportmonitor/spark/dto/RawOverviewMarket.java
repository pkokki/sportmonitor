package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;
import java.util.List;

public class RawOverviewMarket implements Serializable {
    private String id;
    private String description;
    private String type;
    private Float handicap;
    private Boolean isSuspended;
    private List<RawOverviewSelection> selections;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getHandicap() {
        return handicap;
    }

    public void setHandicap(Float handicap) {
        this.handicap = handicap;
    }

    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean suspended) {
        isSuspended = suspended;
    }

    public List<RawOverviewSelection> getSelections() {
        return selections;
    }

    public void setSelections(List<RawOverviewSelection> selections) {
        this.selections = selections;
    }
}
