package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class BookmakerEntity extends BaseEntity {
    private String name;
    private String url;
    private Boolean exchange;

    public BookmakerEntity(BaseEntity parent, long id) {
        super(parent, id);
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
        final StringBuilder sb = new StringBuilder("BookmakerEntity{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", exchange=").append(exchange);
        sb.append('}');
        return sb.toString();
    }
}
