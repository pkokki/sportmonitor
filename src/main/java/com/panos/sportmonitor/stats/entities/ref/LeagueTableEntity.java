package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityIdList;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.SeasonEntity;

public class LeagueTableEntity extends BaseEntity {
    private EntityId seasonId;
    private EntityId tournamentId;
    private EntityId realCategoryId;
    private EntityId rulesId;
    private Integer maxRounds;
    private Integer currentRound;
    private Integer presentationId;
    private String name;
    private String abbr, groupName;
    private Integer totalRows;
    private EntityIdList tableTypes = new EntityIdList();
    private EntityIdList matchTypes = new EntityIdList();
    private EntityIdList tableRows = new EntityIdList();

    public LeagueTableEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, LeagueTableEntity.class));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "tabletype[]": this.tableTypes.add(childEntity.getId()); return true;
            case "matchtype[]": this.matchTypes.add(childEntity.getId()); return true;
            case "tablerows[]": this.tableRows.add(childEntity.getId()); return true;
            case "tournament": this.tournamentId = new EntityId(childEntity); return true;
            case "realcategory": this.realCategoryId = new EntityId(childEntity); return true;
            case "rules": this.rulesId = new EntityId(childEntity); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "seasonid": this.seasonId = new EntityId(node.asLong(), SeasonEntity.class); break;
            case "maxrounds": this.maxRounds = node.asInt(); break;
            case "currentround": this.currentRound = node.asInt(); break;
            case "presentationid": this.presentationId = node.asInt(); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "groupname": this.groupName = node.asText(); break;
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
        return "LeagueTableEntity{" + "id=" + getId() +
                ", seasonId=" + seasonId +
                ", maxRounds=" + maxRounds +
                ", currentRound=" + currentRound +
                ", presentationId=" + presentationId +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", groupName='" + groupName + '\'' +
                ", totalRows=" + totalRows +
                ", tournamentId=" + tournamentId +
                ", realCategoryId=" + realCategoryId +
                ", rulesId=" + rulesId +
                ", tableTypes=" + tableTypes +
                ", matchTypes=" + matchTypes +
                ", tableRows=" + tableRows +
                '}';
    }
}
