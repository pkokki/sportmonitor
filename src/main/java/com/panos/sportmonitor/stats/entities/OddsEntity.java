package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class OddsEntity extends BaseEntity {
    private long bookmakerId;
    private double homeOdds;
    private long homeTbId;
    private int homeOddsFieldId;
    private double homeChange;
    private double drawOdds;
    private long drawTbId;
    private int drawOddsFieldId;
    private double drawChange;
    private double awayOdds;
    private long awayTbId;
    private int awayOddsFieldId;
    private double awayChange;
    private String type;
    private int oddsTypeId;
    private boolean exchange;
    private String key;
    private String extra, closingtime;

    public OddsEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "home.odds": this.homeOdds = Double.parseDouble(node.asText()); return true;
            case "home.tbid": this.homeTbId = node.asLong(); return true;
            case "home.oddsfieldid": this.homeOddsFieldId = node.asInt(); return true;
            case "home.change": this.homeChange = Double.parseDouble(node.asText()); return true;
            case "draw.odds": this.drawOdds = Double.parseDouble(node.asText()); return true;
            case "draw.tbid": this.drawTbId = node.asLong(); return true;
            case "draw.oddsfieldid": this.drawOddsFieldId = node.asInt(); return true;
            case "draw.change": this.drawChange = Double.parseDouble(node.asText()); return true;
            case "away.odds": this.awayOdds = Double.parseDouble(node.asText()); return true;
            case "away.tbid": this.awayTbId = node.asLong(); return true;
            case "away.oddsfieldid": this.awayOddsFieldId = node.asInt(); return true;
            case "away.change": this.awayChange = Double.parseDouble(node.asText()); return true;
            case "type": this.type = node.asText(); return true;
            case "oddstypeid": this.oddsTypeId = node.asInt(); return true;
            case "exchange": this.exchange = node.asBoolean(); return true;
            case "key": this.key = node.asText(); return true;
            case "extra": this.extra = node.asText(); return true;
            case "closingtime": this.closingtime = node.asText(); return true;
        }
        return super.handleProperty(nodeName, nodeType, node);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("bookmaker")) {
            this.bookmakerId = childEntity.getId();
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OddsEntity{");
        sb.append("id=").append(getId());
        sb.append(", bookmakerId=").append(bookmakerId);
        sb.append(", homeOdds=").append(homeOdds);
        sb.append(", homeTbId=").append(homeTbId);
        sb.append(", homeOddsFieldId=").append(homeOddsFieldId);
        sb.append(", homeChange=").append(homeChange);
        sb.append(", drawOdds=").append(drawOdds);
        sb.append(", drawTbId=").append(drawTbId);
        sb.append(", drawOddsFieldId=").append(drawOddsFieldId);
        sb.append(", drawChange=").append(drawChange);
        sb.append(", awayOdds=").append(awayOdds);
        sb.append(", awayTbId=").append(awayTbId);
        sb.append(", awayOddsFieldId=").append(awayOddsFieldId);
        sb.append(", awayChange=").append(awayChange);
        sb.append(", type='").append(type).append('\'');
        sb.append(", oddsTypeId=").append(oddsTypeId);
        sb.append(", exchange=").append(exchange);
        sb.append(", key='").append(key).append('\'');
        sb.append(", extra='").append(extra).append('\'');
        sb.append(", closingtime='").append(closingtime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
