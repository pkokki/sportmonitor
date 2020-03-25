package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityIdList;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.ref.UniqueTournamentEntity;

public class SeasonEntity extends BaseEntity {
    private EntityId uniqueTournamentId;
    private String name;
    private String abbr;
    private Long startDate;
    private Long endDate;
    private Boolean neutralGround;
    private Boolean friendly;
    private String year;
    private Boolean coverageLineups;
    private EntityIdList tables = new EntityIdList();
    private EntityId realCategoryId;
    private EntityIdList iseOdds = new EntityIdList();
    private EntityIdList odds = new EntityIdList();
    private EntityIdList matches = new EntityIdList();
    private EntityIdList tournaments = new EntityIdList();

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
            case "tables[]": this.tables.add(childEntity.getId()); break;
            case "matches[]": this.matches.add(childEntity.getId()); break;
            default:
                if (entityName.startsWith("iseodds."))
                    this.iseOdds.add(childEntity.getId());
                else if (entityName.startsWith("odds."))
                    this.odds.add(childEntity.getId());
                else if (entityName.startsWith("tournaments."))
                    this.tournaments.add(childEntity.getId());
                else if (entityName.startsWith("tables."))
                    this.tables.add(childEntity.getId());
                else return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.startsWith("odds.") && !currentNodeName.endsWith("[]")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_id", this.getRoot().getNext());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
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
                ", tables=" + tables +
                ", realCategoryId=" + realCategoryId +
                ", iseOdds=" + iseOdds +
                ", odds=" + odds +
                ", matches=" + matches +
                ", tournaments=" + tournaments +
                '}';
    }
}
