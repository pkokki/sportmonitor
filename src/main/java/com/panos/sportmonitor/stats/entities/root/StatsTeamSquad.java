package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

public class StatsTeamSquad extends BaseRootEntity {
    private EntityId teamId;
    private EntityIdList players;

    public StatsTeamSquad(long timeStamp) {
        super(BaseRootEntityType.StatsTeamSquad, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.teamId = new EntityId(childEntity);
        } else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            //players.setProperty(childEntity, nodeName, nodeType, node);
            return false;
        } else {
            return super.handleChildProperty(childEntity, nodeName, nodeType, node);
        }
        //return true;
    }
}
