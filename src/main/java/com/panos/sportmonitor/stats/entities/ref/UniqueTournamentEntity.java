package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class UniqueTournamentEntity extends BaseEntity {
    private String name;
    private EntityId realCategoryId;
    private Boolean friendly;
    private Integer levelOrder;

    public UniqueTournamentEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(UniqueTournamentEntity.class, id));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "_rcid": this.realCategoryId = new EntityId(RealCategoryEntity.class, node.asLong()); break;
            case "friendly": this.friendly = node.asBoolean(); break;
            case "tournamentlevelorder": this.levelOrder = node.asInt(); break;
            case "_utid":
            case "_sk":
            case "currentseason":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "UniqueTournamentEntity{" + "id=" + getId() +
                ", name='" + name + '\'' +
                ", realCategory=" + realCategoryId +
                ", friendly=" + friendly +
                ", levelOrder=" + levelOrder +
                '}';
    }
}
