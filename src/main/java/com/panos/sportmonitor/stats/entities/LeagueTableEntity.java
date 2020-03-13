package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class LeagueTableEntity extends BaseEntity {
    private Long seasonId;
    private Integer maxRounds;
    private Integer currentRound;
    private Integer presentationId;
    private String name;
    private String abbr;
    private Integer totalRows;
    private Long tournamentId;
    private Long realCategoryId;
    private Long rulesId;
    private List<Long> tableTypes = new ArrayList<>();
    private List<Long> matchTypes = new ArrayList<>();
    private List<Long> tableRows = new ArrayList<>();

    public LeagueTableEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "tabletype[]": this.tableTypes.add(childEntity.getId()); return true;
            case "matchtype[]": this.matchTypes.add(childEntity.getId()); return true;
            case "tablerows[]": this.tableRows.add(childEntity.getId()); return true;
            case "tournament": this.tournamentId = childEntity.getId(); return true;
            case "realcategory": this.realCategoryId = childEntity.getId(); return true;
            case "rules": this.rulesId = childEntity.getId(); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "seasonid": this.seasonId = Long.parseLong(node.asText()); break;
            case "maxrounds": this.maxRounds = node.asInt(); break;
            case "currentround": this.currentRound = node.asInt(); break;
            case "presentationid": this.presentationId = node.asInt(); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "totalrows": this.totalRows = node.asInt(); break;
            default:
                if (nodeName.startsWith("header[]") || nodeName.startsWith("set[]"))
                    return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LeagueTableEntity{");
        sb.append("id=").append(getId());
        sb.append(", seasonId=").append(seasonId);
        sb.append(", maxRounds=").append(maxRounds);
        sb.append(", currentRound=").append(currentRound);
        sb.append(", presentationId=").append(presentationId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append(", totalRows=").append(totalRows);
        sb.append(", tournamentId=").append(tournamentId);
        sb.append(", realCategoryId=").append(realCategoryId);
        sb.append(", rulesId=").append(rulesId);
        sb.append(", tableTypes=").append(tableTypes);
        sb.append(", matchTypes=").append(matchTypes);
        sb.append(", tableRows=").append(tableRows);
        sb.append('}');
        return sb.toString();
    }
}
