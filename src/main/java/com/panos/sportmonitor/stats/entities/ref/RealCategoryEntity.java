package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class RealCategoryEntity extends BaseEntity {
    private String name;
    private EntityId countryId;

    public RealCategoryEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(RealCategoryEntity.class, id));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "_rcid":
            case "_sk":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("cc")) {
            this.countryId = new EntityId(childEntity);
            return true;
        } else if (entityName.equals("tournaments[]") || entityName.startsWith("uniquetournaments.")) {
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        return "RealCategoryEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                '}';
    }
}
