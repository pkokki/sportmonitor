package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

import java.util.HashMap;

public class StatsTeamVersus extends BaseRootEntity {
    private EntityIdList matches = new EntityIdList();
    private EntityIdList tournaments = new EntityIdList();
    private EntityIdList uniqueTeams = new EntityIdList();
    private EntityIdList realCategories = new EntityIdList();
    private EntityId nextMatchId;
    private EntityId liveMatchId;

    public StatsTeamVersus(long timeStamp) {
        super(BaseRootEntityType.StatsTeamVersus, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "matches[]":
                this.matches.add(childEntity.getId());
                return true;
            case "next":
                this.nextMatchId = childEntity.getId();
                return true;
            default:
                if (entityName.startsWith("tournaments.")) {
                    this.tournaments.add(childEntity.getId());
                    return true;
                } else if (entityName.startsWith("realcategories.")) {
                    this.realCategories.add(childEntity.getId());
                    return true;
                } else if (entityName.startsWith("teams.")) {
                    this.uniqueTeams.add(childEntity.getId());
                    return true;
                }
                else if (entityName.startsWith("currentmanagers.")) {
                    //long teamId = Long.parseLong(entityName.split("\\.|\\[")[1]);
                   //this.currentManagers.put(teamId, childEntity.getId());
                    return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "livematchid": this.liveMatchId = new EntityId(node.asLong()); break;
            default:
                if (nodeName.startsWith("jersey.")) return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            //this.currentManagerSince.put(childEntity.getId(), node.asLong());
            return true;
        }
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamVersus{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", matches=").append(matches);
        sb.append(", tournamentIds=").append(tournaments);
        sb.append(", uniqueTeamIds=").append(uniqueTeams);
        sb.append(", realCategoryIds=").append(realCategories);
        sb.append(", nextMatchId=").append(nextMatchId);
        sb.append(", liveMatchId=").append(liveMatchId);
        sb.append('}');
        return sb.toString();
    }
}
