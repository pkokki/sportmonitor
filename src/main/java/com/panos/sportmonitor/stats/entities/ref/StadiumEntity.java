package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class StadiumEntity extends BaseEntity {
    private List<Long> teamHomes = new ArrayList<>();
    private Long countryId;
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
        super(parent, id);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("cc")) {
            this.countryId = childEntity.getId();
        }
        else if (entityName.equals("hometeams[]")) {
            this.teamHomes.add(childEntity.getId());
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
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
        final StringBuilder sb = new StringBuilder("StadiumEntity{");
        sb.append("id=").append(getId());
        sb.append(", teamHomes=").append(teamHomes);
        sb.append(", countryId=").append(countryId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", city=").append(city);
        sb.append(", country=").append(country);
        sb.append(", state='").append(state).append('\'');
        sb.append(", capacity='").append(capacity).append('\'');
        sb.append(", constrYear='").append(constrYear).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", googlecoords='").append(googlecoords).append('\'');
        sb.append(", pitchsizeX=").append(pitchsizeX);
        sb.append(", pitchsizeY=").append(pitchsizeY);
        sb.append('}');
        return sb.toString();
    }
}
