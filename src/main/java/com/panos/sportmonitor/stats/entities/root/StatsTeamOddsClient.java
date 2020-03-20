package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.*;

import java.util.ArrayList;
import java.util.List;

public class StatsTeamOddsClient extends BaseRootEntity {
    private EntityId uniqueTeamId;
    private EntityIdList odds = new EntityIdList();

    public StatsTeamOddsClient(long timeStamp) {
        super(BaseRootEntityType.StatsTeamOddsClient, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.uniqueTeamId = childEntity.getId();
        }
        else if (entityName.startsWith("odds.")) {
            this.odds.add(childEntity.getId());
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.startsWith("odds.") && !currentNodeName.endsWith("[]")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_id", this.getRoot().getNext());
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamOddsClient{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append(", odds=").append(odds);
        sb.append('}');
        return sb.toString();
    }
}
