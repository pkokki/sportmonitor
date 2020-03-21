package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class BookmakerEntity extends BaseEntity {
    private String name;
    private String url;
    private Boolean exchange;

    public BookmakerEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, BookmakerEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "name": this.name = node.asText(); return true;
            case "url": this.url = node.asText(); return true;
            case "exchange": this.exchange = node.asBoolean(); return true;
        }
        return super.handleProperty(nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        return "BookmakerEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", exchange=" + exchange +
                '}';
    }
}
