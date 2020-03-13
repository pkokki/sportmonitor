package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;

import java.util.ArrayList;
import java.util.List;

public class StatsMatchSituation extends BaseRootEntity {
    private long matchId;
    private List<Long> entries = new ArrayList<>();

    public StatsMatchSituation(String name, long timeStamp) {
        super(name, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "matchid": this.matchId = node.asLong(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("data[]")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "match_situation_entry");
            objNode.put("_id", Long.parseLong(String.format("%08d%04d%02d", matchId, childNode.get("time").asInt(), childNode.get("injurytime").asInt())));
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "data[]": this.entries.add(childEntity.getId()); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsMatchSituation{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", matchId=").append(matchId);
        sb.append(", entries=").append(entries);
        sb.append('}');
        return sb.toString();
    }
}
