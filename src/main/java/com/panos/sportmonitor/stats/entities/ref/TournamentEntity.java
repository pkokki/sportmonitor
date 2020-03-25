package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.SeasonEntity;

public class TournamentEntity extends BaseEntity {
    private EntityId realCategoryId;
    private EntityId seasonId;
    private EntityId currentSeasonId;

    private Long isk;
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
    private String cupRosterId;
    private Integer currentRound;
    private String groupName;

    public TournamentEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(TournamentEntity.class, id));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_rcid": this.realCategoryId = new EntityId(RealCategoryEntity.class, node.asLong()); break;
            case "_isk": this.isk = node.asLong(); break;
            case "seasonid": this.seasonId = new EntityId(SeasonEntity.class, node.asLong()); break;
            case "currentseason": this.currentSeasonId = new EntityId(SeasonEntity.class, node.asLong()); break;
            case "seasontype": this.seasonType = node.asText(); break;
            case "seasontypename": this.seasonTypeName = node.asText(); break;
            case "seasontypeunique": this.seasonTypeUnique = node.asText(); break;
            case "year": this.year = node.asText(); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "cuprosterid": this.cupRosterId = node.asText(); break;
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
            case "matches[]": /*this.matches.add(new EntityId(MatchEntity.class, node.asLong()));*/ break;

            case "_tid":
            case "_utid":
            case "_sk":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "TournamentEntity{" + "id=" + getId() +
                "realCategoryId=" + realCategoryId +
                ", isk=" + isk +
                ", seasonId=" + seasonId +
                ", currentSeason=" + currentSeasonId +
                ", seasonType='" + seasonType + '\'' +
                ", seasonTypeName='" + seasonTypeName + '\'' +
                ", seasonTypeUnique='" + seasonTypeUnique + '\'' +
                ", year='" + year + '\'' +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", friendly=" + friendly +
                ", roundByRound=" + roundByRound +
                ", outdated=" + outdated +
                ", liveTable=" + liveTable +
                ", tournamentLevelOrder=" + tournamentLevelOrder +
                ", tournamentLevelName='" + tournamentLevelName + '\'' +
                ", cuprRosterId='" + cupRosterId + '\'' +
                ", currentRound=" + currentRound +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
