package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class TournamentEntity extends BaseEntity {
    private Long realCategoryId;
    private Long isk;
    private Long seasonId, currentSeason;
    private String seasonType;
    private String seasonTypeName;
    private String seasonTypeUnique;
    private String year;
    private String name;
    private String abbr;
    private Boolean friendly;
    private Boolean roundByRound;
    private Boolean outdated;
    private Long liveTable;
    private Long tournamentLevelOrder;
    private String tournamentLevelName;
    private String cuprRosterId;
    private Integer currentRound;
    private String groupName;
    private List<Long> matches = new ArrayList<>();

    public TournamentEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_rcid": this.realCategoryId = node.asLong(); break;
            case "_isk": this.isk = node.asLong(); break;
            case "seasonid": this.seasonId = node.asLong(); break;
            case "currentseason": this.currentSeason = node.asLong(); break;
            case "seasontype": this.seasonType = node.asText(); break;
            case "seasontypename": this.seasonTypeName = node.asText(); break;
            case "seasontypeunique": this.seasonTypeUnique = node.asText(); break;
            case "year": this.year = node.asText(); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "cuprosterid": this.cuprRosterId = node.asText(); break;
            case "ground":
                if (!node.isNull()) return false; break;
            case "friendly": this.friendly = node.asBoolean(); break;
            case "roundbyround": this.roundByRound = node.asBoolean(); break;
            case "outdated": this.outdated = node.asBoolean(); break;
            case "livetable": this.liveTable = node.asLong(); break;
            case "tournamentlevelorder": this.tournamentLevelOrder = node.asLong(); break;
            case "tournamentlevelname": this.tournamentLevelName = node.asText(); break;
            case "groupname": this.groupName = node.asText(); break;
            case "currentround": this.currentRound = node.asInt(); break;
            case "matches[]": this.matches.add(node.asLong()); break;

            case "_tid":
            case "_utid":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TournamentEntity{");
        sb.append("id=").append(getId());
        sb.append("realCategoryId=").append(realCategoryId);
        sb.append(", isk=").append(isk);
        sb.append(", seasonId=").append(seasonId);
        sb.append(", currentSeason=").append(currentSeason);
        sb.append(", seasonType='").append(seasonType).append('\'');
        sb.append(", seasonTypeName='").append(seasonTypeName).append('\'');
        sb.append(", seasonTypeUnique='").append(seasonTypeUnique).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append(", friendly=").append(friendly);
        sb.append(", roundByRound=").append(roundByRound);
        sb.append(", outdated=").append(outdated);
        sb.append(", liveTable=").append(liveTable);
        sb.append(", tournamentLevelOrder=").append(tournamentLevelOrder);
        sb.append(", tournamentLevelName='").append(tournamentLevelName).append('\'');
        sb.append(", cuprRosterId='").append(cuprRosterId).append('\'');
        sb.append(", currentRound=").append(currentRound);
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", matches=").append(matches);
        sb.append('}');
        return sb.toString();
    }
}
