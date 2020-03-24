package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.ref.RealCategoryEntity;

public class TeamEntity extends BaseEntity {
    private EntityId uid;
    private String name;
    private String abbr;
    private String nickname;
    private String mediumName;
    private Boolean isCountry;
    private EntityId homeRealCategoryId;
    private EntityId countryId;

    public TeamEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(TeamEntity.class, id));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "uid": if (node.asLong() > 0) this.uid = new EntityId(UniqueTeamEntity.class, node.asLong()); break;
            case "name": this.name = node.asText(); break;
            case "abbr": this.abbr = node.asText(); break;
            case "nickname": this.nickname = node.asText(); break;
            case "mediumname": this.mediumName = node.asText(); break;
            case "iscountry": this.isCountry = node.asBoolean(); break;
            case "homerealcategoryid": this.homeRealCategoryId = new EntityId(RealCategoryEntity.class, node.asLong()); break;

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
            this.countryId = new EntityId(childEntity);
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        return "TeamEntity{" + "id=" + getId() +
                ", uid=" + getUid() +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mediumName='" + mediumName + '\'' +
                ", isCountry=" + isCountry +
                ", countryId=" + countryId +
                ", homeRealCategoryId=" + homeRealCategoryId +
                '}';
    }

    public EntityId getUid() {
        return uid;
    }
}
