package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

public class CountryEntity extends BaseEntity {
    private String name;
    private String code;
    private Integer continentId;
    private String continent;
    private Long population;

    public CountryEntity(BaseEntity parent, long id) {
        super(parent, id);
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
        final StringBuilder sb = new StringBuilder("CountryEntity{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", continentId=").append(continentId);
        sb.append(", continent='").append(continent).append('\'');
        sb.append(", population=").append(population);
        sb.append('}');
        return sb.toString();
    }
}
