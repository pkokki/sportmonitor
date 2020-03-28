package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class OddsEntity extends BaseEntity {
    private EntityId bookmakerId;
    private Double homeOdds;
    private Long homeTbId;
    private Integer homeOddsFieldId;
    private Double homeChange;
    private Double drawOdds;
    private Long drawTbId;
    private Integer drawOddsFieldId;
    private Double drawChange;
    private Double awayOdds;
    private Long awayTbId;
    private Integer awayOddsFieldId;
    private Double awayChange;
    private String type;
    private Integer oddsTypeId;
    private Boolean exchange;
    private String key;
    private String extra, closingTime;

    public OddsEntity(BaseEntity parent, long matchId, long timeStamp, int ise) {
        super(parent, new EntityId(OddsEntity.class,
                new EntityKey("matchId", matchId),
                EntityKey.Timestamp(timeStamp),
                new EntityKey("ise", ise)
                ));
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "_mid": return true;
            case "home.odds":
            case "odds.home":
                this.homeOdds = Double.parseDouble(node.asText()); return true;
            case "home.tbid": this.homeTbId = node.asLong(); return true;
            case "home.oddsfieldid": this.homeOddsFieldId = node.asInt(); return true;
            case "home.change": this.homeChange = Double.parseDouble(node.asText()); return true;
            case "draw.odds":
            case "odds.draw":
                this.drawOdds = Double.parseDouble(node.asText()); return true;
            case "draw.tbid": this.drawTbId = node.asLong(); return true;
            case "draw.oddsfieldid": this.drawOddsFieldId = node.asInt(); return true;
            case "draw.change": this.drawChange = Double.parseDouble(node.asText()); return true;
            case "away.odds":
            case "odds.away":
                this.awayOdds = Double.parseDouble(node.asText()); return true;
            case "away.tbid": this.awayTbId = node.asLong(); return true;
            case "away.oddsfieldid": this.awayOddsFieldId = node.asInt(); return true;
            case "away.change": this.awayChange = Double.parseDouble(node.asText()); return true;
            case "type": this.type = node.asText(); return true;
            case "oddstypeid": this.oddsTypeId = node.asInt(); return true;
            case "exchange": this.exchange = node.asBoolean(); return true;
            case "key": this.key = node.asText(); return true;
            case "extra": this.extra = node.asText(); return true;
            case "closingtime": this.closingTime = node.asText(); return true;
        }
        return super.handleProperty(nodeName, nodeType, node);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("bookmaker")) {
            this.bookmakerId = new EntityId(childEntity);
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        return "OddsEntity{" + "id=" + getId() +
                ", bookmakerId=" + bookmakerId +
                ", homeOdds=" + homeOdds +
                ", drawOdds=" + drawOdds +
                ", awayOdds=" + awayOdds +
                ", homeTbId=" + homeTbId +
                ", homeOddsFieldId=" + homeOddsFieldId +
                ", homeChange=" + homeChange +
                ", drawTbId=" + drawTbId +
                ", drawOddsFieldId=" + drawOddsFieldId +
                ", drawChange=" + drawChange +
                ", awayTbId=" + awayTbId +
                ", awayOddsFieldId=" + awayOddsFieldId +
                ", awayChange=" + awayChange +
                ", type='" + type + '\'' +
                ", oddsTypeId=" + oddsTypeId +
                ", exchange=" + exchange +
                ", key='" + key + '\'' +
                ", extra='" + extra + '\'' +
                ", closingtime='" + closingTime + '\'' +
                '}';
    }
}
