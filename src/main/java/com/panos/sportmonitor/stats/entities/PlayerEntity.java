package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class PlayerEntity extends BaseEntity {
    private String name, fullName;
    private Long birthDate, marketValue;
    private Integer height, weight;
    private String foot, birthPlace, twitter, facebook;
    private EntityId nationalityId, secondNationalityId, birthCountryId;
    private EntityId positionId;

    public PlayerEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, PlayerEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "fullname": this.fullName = node.asText(); break;
            case "birthdate.uts": this.birthDate = node.asLong(); break;
            case "marketvalue": this.marketValue = node.asLong(); break;
            case "height": this.height = node.asInt(); break;
            case "weight": this.weight = node.asInt(); break;
            case "_foot": this.foot = node.asText(); break;
            case "foot": break;
            case "birthplace": this.birthPlace = node.asText(); break;
            case "twitter": this.twitter = node.asText(); break;
            case "facebook": this.facebook = node.asText(); break;
            case "primarypositiontype": if (!node.isNull()) return false; break;
            case "membersince.uts":
                getParent().setChildProperty(this, nodeName, nodeType, node);
                break;
            case "jerseynumber":
            case "shirtnumber":
            case "birthdate._doc":
            case "birthdate.time":
            case "birthdate.date":
            case "birthdate.tz":
            case "birthdate.tzoffset":
            case "membersince._doc":
            case "membersince.time":
            case "membersince.date":
            case "membersince.tz":
            case "membersince.tzoffset":
            case "haslogo":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public boolean handleAuxId(long auxEntityId) {
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("position")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "player_position_type");
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "nationality":
                this.nationalityId = new EntityId(childEntity);
                break;
            case "secondarynationality":
                this.secondNationalityId = new EntityId(childEntity);
                break;
            case "position":
                this.positionId = new EntityId(childEntity);
                break;
            case "birthcountry":
                this.birthCountryId = new EntityId(childEntity);
                break;
            default:
                return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlayerEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", fullname='" + fullName + '\'' +
                ", birthdate=" + birthDate +
                ", nationalityId=" + nationalityId +
                ", secondNationalityId=" + secondNationalityId +
                ", positionId=" + positionId +
                '}';
    }
}
