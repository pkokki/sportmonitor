package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class SeasonEntity extends BaseEntity {
    private Long uniqueTournamentId;
    private String name;
    private String abbr;
    private Long startDate;
    private Long endDate;
    private Boolean neutralGround;
    private Boolean friendly;
    private String year;
    private Boolean coverageLineups;
    private List<Long> tables = new ArrayList<>();
    private Long realCategoryId;
    private List<Long> iseOdds = new ArrayList<>();
    private List<Long> odds = new ArrayList<>();
    private List<Long> matches = new ArrayList<>();
    private List<Long> tournaments = new ArrayList<>();

    public SeasonEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "uniquetournament": this.uniqueTournamentId = childEntity.getId(); break;
            case "realcategory": this.realCategoryId = childEntity.getId(); break;
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
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_utid": this.uniqueTournamentId = node.asLong(); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "start.uts": this.startDate = node.asLong() * 1000; break;
            case "end.uts": this.endDate = node.asLong() * 1000; break;
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
        final StringBuilder sb = new StringBuilder("SeasonEntity{");
        sb.append("id=").append(getId());
        sb.append(", uniqueTournamentId=").append(uniqueTournamentId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", neutralGround=").append(neutralGround);
        sb.append(", friendly=").append(friendly);
        sb.append(", year='").append(year).append('\'');
        sb.append(", coverageLineups=").append(coverageLineups);
        sb.append(", tables=").append(tables);
        sb.append('}');
        return sb.toString();
    }
}
