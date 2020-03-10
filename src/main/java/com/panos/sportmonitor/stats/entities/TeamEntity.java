package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class TeamEntity extends BaseEntity {
    private long uid;
    private String name;
    private String abbr;
    private String nickname;
    private String mediumName;
    private boolean isCountry;

    public TeamEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "uid": this.uid = node.asLong(); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "nickname": this.nickname = node.asText(); break;
            case "mediumname": this.mediumName = node.asText(); break;
            case "iscountry": this.isCountry = node.asBoolean(); break;

            case "haslogo":
            case "virtual":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamEntity{");
        sb.append("id=").append(getId());
        sb.append(", uid=").append(getUid());
        sb.append(", name='").append(name).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", mediumName='").append(mediumName).append('\'');
        sb.append(", isCountry=").append(isCountry);
        sb.append('}');
        return sb.toString();
    }

    public long getUid() {
        return uid;
    }
}
