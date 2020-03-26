package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.ref.UniqueTournamentEntity;
import com.panos.sportmonitor.stats.entities.ref.OddsEntity;

import java.util.Iterator;
import java.util.Map;

public class SeasonEntity extends BaseEntity {
    private EntityId realCategoryId;
    private EntityId uniqueTournamentId;

    private String name;
    private String abbr;
    private Long startDate;
    private Long endDate;
    private Boolean neutralGround;
    private Boolean friendly;
    private String year;
    private Boolean coverageLineups;

    public SeasonEntity(BaseEntity parent, long id) {
        super(parent, createId(id));
    }

    public static EntityId createId(long id) {
        return new EntityId(SeasonEntity.class, id);
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "uniquetournament": this.uniqueTournamentId = new EntityId(childEntity); break;
            case "realcategory": this.realCategoryId = new EntityId(childEntity); break;
            case "tables[]":
            case "matches[]":break;
            default:
                if (entityName.startsWith("iseodds."))
                    break;
                else if (entityName.startsWith("odds."))
                    break;
                else if (entityName.startsWith("tournaments."))
                    break;
                else if (entityName.startsWith("tables."))
                    break;
                else return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.equals("iseodds")) {
            for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
                ObjectNode child = (ObjectNode)it.next().getValue();
                child.remove("_doc");
            }
        }
        else if (nodeName.startsWith("odds.") && nodeName.endsWith("[]")) {
            long matchId = Long.parseLong(nodeName.substring(nodeName.lastIndexOf('.') + 1).replace("[]", ""));
            return new OddsEntity(this, matchId, timeStamp, 1);
        }
        else if (nodeName.startsWith("iseodds.")) {
            long matchId = Long.parseLong(nodeName.substring(nodeName.lastIndexOf('.') + 1));
            return new OddsEntity(this, matchId, timeStamp, 0);
        }
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_utid": this.uniqueTournamentId = new EntityId(UniqueTournamentEntity.class, node.asLong()); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "start.uts": this.startDate = node.asLong(); break;
            case "end.uts": this.endDate = node.asLong(); break;
            case "neutralground": this.neutralGround = node.asBoolean(); break;
            case "friendly": this.friendly = node.asBoolean(); break;
            case "year": this.year = node.asText(); break;
            case "coverage.lineups": this.coverageLineups = node.asBoolean(); break;
            case "start._doc":
            case "start.time":
            case "start.date":
            case "start.tz":
            case "start.tzoffset":
            case "end._doc":
            case "end.time":
            case "end.date":
            case "end.tz":
            case "end.tzoffset":
            case "currentseasonid":
            case "h2hdefault.matchid":
            case "h2hdefault.teamidhome":
            case "h2hdefault.teamidaway":
                return true;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "SeasonEntity{" + "id=" + getId() +
                ", uniqueTournamentId=" + uniqueTournamentId +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", neutralGround=" + neutralGround +
                ", friendly=" + friendly +
                ", year='" + year + '\'' +
                ", coverageLineups=" + coverageLineups +
                ", realCategoryId=" + realCategoryId +
                '}';
    }
}
