package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;

public class TeamFormEntryEntity extends BaseTimeEntity {
    private Integer index;
    private String group;
    private String typeId;
    private String value;
    private Boolean homeMatch;
    private Boolean neutralGround;
    private EntityId matchId;

    public TeamFormEntryEntity(BaseEntity parent, long timeStamp) {
        super(parent, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_index": this.index = node.asInt(); break;
            case "group": this.group = node.asText(); break;
            case "typeid": this.typeId = node.asText(); break;
            case "value": this.value = node.asText(); break;
            case "homematch": this.homeMatch = node.asBoolean(); break;
            case "neutralground": this.neutralGround = node.asBoolean(); break;
            case "matchid": this.matchId = new EntityId(node.asLong()); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamFormEntryEntity{");
        sb.append("id=").append(getId());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", group='").append(group).append('\'');
        sb.append(", index=").append(index);
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", homeMatch=").append(homeMatch);
        sb.append(", neutralGround=").append(neutralGround);
        sb.append(", matchId=").append(matchId);
        sb.append('}');
        return sb.toString();
    }
}
