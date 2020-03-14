package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class TeamEntity extends BaseEntity {
    private Long uid;
    private String name;
    private String abbr;
    private String nickname;
    private String mediumName;
    private Boolean isCountry;
    private Long homeRealCategoryId;
    private EntityId countryId;

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
            case "homerealcategoryid": this.homeRealCategoryId = node.asLong(); break;

            case "haslogo":
            case "virtual":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("countrycode")) {
            this.countryId = childEntity.getId();
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
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
        sb.append(", countryId=").append(countryId);
        sb.append(", homeRealCategoryId=").append(homeRealCategoryId);
        sb.append('}');
        return sb.toString();
    }

    public long getUid() {
        return uid;
    }
}
