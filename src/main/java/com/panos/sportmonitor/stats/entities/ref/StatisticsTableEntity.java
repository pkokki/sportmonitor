package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsTableEntity extends BaseEntity {
    private transient long lastId = -1;

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
    private HashMap<Long, String> uniqueTeams = new HashMap<>();
    private List<Long> matches = new ArrayList<>();

    public StatisticsTableEntity(BaseEntity parent, long id) {
        super(parent, id);
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
            case "matches[]": matches.add(node.asLong()); break;
            case "uniqueteams[].id":
                this.lastId = node.asLong();
                this.uniqueTeams.put(lastId, "<N/A>");
                break;
            case "uniqueteams[].name":
                this.uniqueTeams.put(lastId, node.asText());
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
        final StringBuilder sb = new StringBuilder("StatisticsTableEntity{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append(", maxRounds='").append(maxRounds).append('\'');
        sb.append(", tournamentId=").append(tournamentId);
        sb.append(", seasonId='").append(seasonId).append('\'');
        sb.append(", seasonType='").append(seasonType).append('\'');
        sb.append(", seasonTypeName='").append(seasonTypeName).append('\'');
        sb.append(", seasonTypeUnique='").append(seasonTypeUnique).append('\'');
        sb.append(", seasonStart=").append(seasonStart);
        sb.append(", seasonEnd=").append(seasonEnd);
        sb.append(", uniqueTeams=").append(uniqueTeams);
        sb.append('}');
        return sb.toString();
    }
}
