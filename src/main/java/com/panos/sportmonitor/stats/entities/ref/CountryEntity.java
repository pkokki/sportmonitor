package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class CountryEntity extends BaseEntity {
    private String name;
    private String code;
    private Integer continentId;
    private String continent;
    private Long population;

    public CountryEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(CountryEntity.class, id));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "name": this.name = node.asText(); break;
            case "ioc": this.code = node.asText(); break;
            case "continentid": this.continentId = node.asInt(); break;
            case "continent": this.continent = node.asText(); break;
            case "population": this.population = node.asLong(); break;
            case "a2":
            case "a3":
                return true;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "CountryEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", continentId=" + continentId +
                ", continent='" + continent + '\'' +
                ", population=" + population +
                '}';
    }
}
