package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class UniqueTeamEntity extends BaseEntity {
    private long uid;
    private String name;
    private String suffix;
    private String abbr;
    private String nickname;
    private String mediumName;
    private boolean isCountry;
    private String founded;
    private String website;
    private String sex;
    private long realCategoryId;
    private long teamTypeId;
    private long countryCodeId;
    private long stadiumId;
    private long homeRealCategoryId;

    public UniqueTeamEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "countrycode": this.countryCodeId = childEntity.getId(); return true;
            case "stadium": this.stadiumId = childEntity.getId(); return true;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_rcid": this.realCategoryId = node.asLong(); break;
            case "homerealcategoryid": this.homeRealCategoryId = node.asLong(); break;
            case "teamtypeid": this.teamTypeId = node.asLong(); break;
            case "name": this.name = node.asText(); break;
            case "suffix": this.suffix = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "nickname": this.nickname = node.asText(); break;
            case "mediumname": this.mediumName = node.asText(); break;
            case "iscountry": this.isCountry = node.asBoolean(); break;
            case "sex": this.sex = node.asText(); break;
            case "website": this.website = node.asText(); break;
            case "founded": this.founded = node.asText(); break;

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
        final StringBuilder sb = new StringBuilder("UniqueTeamEntity{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", suffix='").append(suffix).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", mediumName='").append(mediumName).append('\'');
        sb.append(", isCountry=").append(isCountry);
        sb.append(", founded='").append(founded).append('\'');
        sb.append(", website='").append(website).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", realCategoryId=").append(realCategoryId);
        sb.append(", teamTypeId=").append(teamTypeId);
        sb.append('}');
        return sb.toString();
    }
}
