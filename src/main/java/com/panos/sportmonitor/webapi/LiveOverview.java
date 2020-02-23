package com.panos.sportmonitor.webapi;

import java.io.Serializable;
import java.util.List;

public class LiveOverview implements Serializable {

    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
