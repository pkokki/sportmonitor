package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;

public class StatsTeamPlayerFacts extends BaseRootEntity {
    public StatsTeamPlayerFacts(long timeStamp) {
        super(BaseRootEntityType.StatsTeamPlayerFacts, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.endsWith(".player") || entityName.endsWith(".stats") || entityName.contains(".teams.") || entityName.contains(".seasons."))
            return true;
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts"))
            return true;
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }
}
