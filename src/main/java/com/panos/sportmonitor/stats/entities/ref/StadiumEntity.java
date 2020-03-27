package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class StadiumEntity extends BaseEntity {
    private EntityId countryId;
    private String name;
    private String description;
    private String city;
    private String country;
    private String state;
    private String capacity;
    private String constrYear;
    private String address;
    private String googlecoords;
    private Integer pitchsizeX;
    private Integer pitchsizeY;
    private String url;
    private String phone;

    public StadiumEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(StadiumEntity.class, id));
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("cc"))
            this.countryId = new EntityId(childEntity);
        else if (entityName.equals("hometeams[]"))
            this.getRoot().addChildEntity(new StadiumUniqueTeamEntity(this, this.getId(), childEntity.getId()));
        else
            return super.handleChildEntity(entityName, childEntity);
        return true;
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "description": this.description = node.asText(); break;
            case "city": this.city = node.asText(); break;
            case "country": this.country = node.asText(); break;
            case "state": this.state = node.asText(); break;
            case "capacity": this.capacity = node.asText(); break;
            case "constryear": this.constrYear = node.asText(); break;
            case "address": this.address = node.asText(); break;
            case "url": this.url = node.asText(); break;
            case "phone": this.phone = node.asText(); break;
            case "googlecoords": this.googlecoords = node.asText(); break;
            case "pitchsize.x": this.pitchsizeX = node.isNull() ? null : node.asInt(); break;
            case "pitchsize.y": this.pitchsizeY = node.isNull() ? null : node.asInt(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "StadiumEntity{" + "id=" + getId() +
                ", countryId=" + countryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city=" + city +
                ", country=" + country +
                ", state='" + state + '\'' +
                ", capacity='" + capacity + '\'' +
                ", constrYear='" + constrYear + '\'' +
                ", address='" + address + '\'' +
                ", googlecoords='" + googlecoords + '\'' +
                ", pitchsizeX=" + pitchsizeX +
                ", pitchsizeY=" + pitchsizeY +
                ", url=" + url +
                ", phone=" + phone +
                '}';
    }
}
