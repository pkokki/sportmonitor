package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.ref.OddsEntity;

public class StatsTeamOddsClient extends BaseRootEntity {
    private EntityId uniqueTeamId;

    public StatsTeamOddsClient(long timeStamp) {
        super(BaseRootEntityType.StatsTeamOddsClient, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.uniqueTeamId = new EntityId(childEntity);
        }
        else if (entityName.startsWith("odds.")) {
            return true;
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

//    @Override
//    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
//        if (currentNodeName.startsWith("odds.") && !currentNodeName.endsWith("[]")) {
//            ObjectNode objNode = (ObjectNode)childNode;
//            objNode.put("_id", this.getRoot().getNext());
//        }
//        return super.transformChildNode(currentNodeName, index, childNode);
//    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
//        System.out.println(nodeName + ": " + node);
        if (nodeName.startsWith("odds."))
            return new OddsEntity(this, Long.parseLong(nodeName.substring(nodeName.indexOf('.') + 1).replace("[]", "")), timeStamp, 0);
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamOddsClient{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append('}');
        return sb.toString();
    }
}
