package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.MatchEntity;

public class MatchFunFactEntity extends BaseEntity {
    private EntityId matchId;
    private Long typeId;
    private String sentence;

    public MatchFunFactEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(MatchFunFactEntity.class, id));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "matchId": this.matchId = MatchEntity.createId(node.asLong()); break;
            case "_typeid": this.typeId = node.asLong(); break;
            case "sentence": this.sentence = node.asText(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "MatchFunFactEntity{" + "id=" + getId() +
                ", typeId=" + typeId +
                ", sentence='" + sentence + '\'' +
                '}';
    }
}
