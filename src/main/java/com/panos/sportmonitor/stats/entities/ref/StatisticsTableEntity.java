package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityIdList;
import com.panos.sportmonitor.stats.EntityId;

public class StatisticsTableEntity extends BaseEntity {
    private String name;
    private String abbr;
    private String maxRounds;
    private Long tournamentId;
    private String seasonId;
    private String seasonType;
    private String seasonTypeName;
    private String seasonTypeUnique;
    private Long seasonStart;
    private Long seasonEnd;
    private EntityIdList matches = new EntityIdList();

    public StatisticsTableEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, StatisticsTableEntity.class));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "maxrounds": this.maxRounds = node.asText(); break;
            case "tournamentid": this.tournamentId = node.asLong(); break;
            case "seasonid": this.seasonId = node.asText(); break;
            case "seasontype": this.seasonType = node.asText(); break;
            case "seasontypename": this.seasonTypeName = node.asText(); break;
            case "seasontypeunique": this.seasonTypeUnique = node.asText(); break;
            case "start.uts": this.seasonStart = node.asLong(); break;
            case "end.uts": this.seasonEnd = node.asLong(); break;
            case "matches[]": matches.add(new EntityId(node.asLong(), MatchSituationEntryEntity.class)); break;
            case "uniqueteams[].id":
            case "uniqueteams[].name":
                break;

            case "id":
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
            case "end.tzoffset":
                return true;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "StatisticsTableEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", maxRounds='" + maxRounds + '\'' +
                ", tournamentId=" + tournamentId +
                ", seasonId='" + seasonId + '\'' +
                ", seasonType='" + seasonType + '\'' +
                ", seasonTypeName='" + seasonTypeName + '\'' +
                ", seasonTypeUnique='" + seasonTypeUnique + '\'' +
                ", seasonStart=" + seasonStart +
                ", seasonEnd=" + seasonEnd +
                ", matches=" + matches +
                '}';
    }
}
