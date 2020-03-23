package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityId;

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
        else if (entityName.startsWith("roles."))
            return true;
        else
            return super.handleChildEntity(entityName, childEntity);
        return true;
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
//        if (nodeName.equals("membersince.uts"))
//            return true; //players.setProperty(childEntity, nodeName, nodeType, node);
//        else
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }
}
