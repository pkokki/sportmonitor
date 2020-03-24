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
    private String seasonType;
    private String seasonTypeName;
    private String seasonTypeUnique;
    private Long seasonStart;
    private Long seasonEnd;
    private EntityIdList matches = new EntityIdList();
    private EntityIdList tableTypes = new EntityIdList();
    private EntityIdList matchTypes = new EntityIdList();
    private EntityIdList tableRows = new EntityIdList();


    public LeagueTableEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(LeagueTableEntity.class, id));
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
            case "seasonid": this.seasonId = new EntityId(SeasonEntity.class, node.asLong()); break;
            case "maxrounds": this.maxRounds = node.asInt(); break;
            case "currentround": this.currentRound = node.asInt(); break;
            case "presentationid": this.presentationId = node.asInt(); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "groupname": this.groupName = node.asText(); break;
            case "totalrows": this.totalRows = node.asInt(); break;
            case "seasontype": this.seasonType = node.asText(); break;
            case "seasontypename": this.seasonTypeName = node.asText(); break;
            case "seasontypeunique": this.seasonTypeUnique = node.asText(); break;
            case "start.uts": this.seasonStart = node.asLong(); break;
            case "end.uts": this.seasonEnd = node.asLong(); break;
            case "matches[]": matches.add(new EntityId(MatchSituationEntryEntity.class, node.asLong())); break;
            case "tournamentid": new EntityId(TournamentEntity.class, node.asLong()); break;
            case "roundbyround":
            case "start._doc":
            case "start.time":
            case "start.date":
            case "start.tz":
            case "start.tzoffset":
            case "end._doc":
            case "end.date":
            case "end.time":
            case "end.tz":
            case "id":
            case "end.tzoffset":
            case "uniqueteams[].id":
            case "uniqueteams[].name":
                return true;
            default:
                if (nodeName.startsWith("header[]") || nodeName.startsWith("set[]"))
                    return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "LeagueTableEntity{" + "seasonId=" + seasonId +
                ", tournamentId=" + tournamentId +
                ", realCategoryId=" + realCategoryId +
                ", rulesId=" + rulesId +
                ", maxRounds=" + maxRounds +
                ", currentRound=" + currentRound +
                ", presentationId=" + presentationId +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", groupName='" + groupName + '\'' +
                ", totalRows=" + totalRows +
                ", seasonType='" + seasonType + '\'' +
                ", seasonTypeName='" + seasonTypeName + '\'' +
                ", seasonTypeUnique='" + seasonTypeUnique + '\'' +
                ", seasonStart=" + seasonStart +
                ", seasonEnd=" + seasonEnd +
                ", matches=" + matches +
                ", tableTypes=" + tableTypes +
                ", matchTypes=" + matchTypes +
                ", tableRows=" + tableRows +
                '}';
    }
}
