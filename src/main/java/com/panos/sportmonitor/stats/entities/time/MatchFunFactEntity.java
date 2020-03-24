package com.panos.sportmonitor.stats.entities.time;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;

public class MatchFunFactEntity extends BaseTimeEntity {
    private Long typeId;
    private String sentence;

    public MatchFunFactEntity(BaseEntity parent, long id, long timeStamp) {
        super(parent, new EntityId(MatchFunFactEntity.class, id, timeStamp));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
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
