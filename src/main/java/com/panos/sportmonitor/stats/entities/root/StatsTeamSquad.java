package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.ref.UniqueTeamPlayerEntity;

public class StatsTeamSquad extends BaseRootEntity {
    private EntityId uniqueTeamId;
    private Integer numberOfPlayers;
    private Double averageSquadAge;

    public StatsTeamSquad(long timeStamp) {
        super(BaseRootEntityType.StatsTeamSquad, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("squadinfo.numberofplayers"))
            this.numberOfPlayers = node.asInt();
        else if (nodeName.equals("squadinfo.averagesquadage"))
            this.averageSquadAge = node.asDouble();
        else
            return super.handleProperty(nodeName, nodeType, node);
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team"))
            this.uniqueTeamId = childEntity.getId();
        else if (entityName.equals("players[]"))
            return true;
//        else if (entityName.equals("managers[]"))
//            this.uniqueTeam.setEntity(entityName, childEntity);
//        else if (entityName.startsWith("seasons."))
//            this.uniqueTeam.setEntity(entityName, childEntity);
        else if (entityName.startsWith("roles.")) {
            return true;
        }
        else
            return super.handleChildEntity(entityName, childEntity);
        return true;
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            long startTime = node.asLong();
            this.setAsyncProperty(nodeName, UniqueTeamPlayerEntity.createId(this.uniqueTeamId.getId(), childEntity.getId().getId()), e -> ((UniqueTeamPlayerEntity)e).setStartTime(startTime));
            return true;
        }
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }
}
