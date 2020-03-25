package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.MatchEntity;

import java.util.ArrayList;
import java.util.List;

public class MatchFunFacts extends BaseRootEntity {
    private final EntityId matchId;

    public MatchFunFacts(long timeStamp, long matchId) {

        super(BaseRootEntityType.MatchFunFacts, timeStamp);
        this.matchId = MatchEntity.createId(matchId);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("funfacts[]")) return true;
        return super.handleProperty(nodeName, nodeType, node);
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("facts")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "match_funfact");
            objNode.put("matchId", matchId.getId());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("facts[]"))
            return true;
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        return "MatchFunFacts{" + "name=" + getName() +
                ", matchId=" + matchId +
                '}';
    }
}
