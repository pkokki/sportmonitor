package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityIdList;

import java.util.ArrayList;
import java.util.List;

public class MatchFunFacts extends BaseRootEntity {
    private EntityIdList facts = new EntityIdList();

    public MatchFunFacts(long timeStamp) {
        super(BaseRootEntityType.MatchFunFacts, timeStamp);
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
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("facts[]"))
            this.facts.add(childEntity.getId());
        else
            return super.handleChildEntity(entityName, childEntity);
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchFunFacts{");
        sb.append("name=").append(getName());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", facts=").append(facts);
        sb.append('}');
        return sb.toString();
    }
}
