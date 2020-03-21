package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.MatchEntity;

public class TeamFormEntryEntity extends BaseTimeEntity {
    private Integer _index;
    private String group_name;
    private String typeId;
    private String value;
    private Boolean homeMatch;
    private Boolean neutralGround;
    private EntityId matchId;

    public TeamFormEntryEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, new EntityId(id, timeStamp, TeamFormEntryEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_index": this._index = node.asInt(); break;
            case "group": this.group_name = node.asText(); break;
            case "typeid": this.typeId = node.asText(); break;
            case "value": this.value = node.asText(); break;
            case "homematch": this.homeMatch = node.asBoolean(); break;
            case "neutralground": this.neutralGround = node.asBoolean(); break;
            case "matchid": this.matchId = new EntityId(node.asLong(), MatchEntity.class); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamFormEntryEntity{" + "id=" + getId() +
                ", group_name='" + group_name + '\'' +
                ", index=" + _index +
                ", typeId='" + typeId + '\'' +
                ", value='" + value + '\'' +
                ", homeMatch=" + homeMatch +
                ", neutralGround=" + neutralGround +
                ", matchId=" + matchId +
                '}';
    }
}
