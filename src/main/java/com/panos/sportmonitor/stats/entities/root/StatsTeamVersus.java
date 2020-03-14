package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsTeamVersus extends BaseRootEntity {
    private EntityIdList matches = new EntityIdList();
    private EntityIdList tournamentIds = new EntityIdList();
    private EntityIdList uniqueTeamIds = new EntityIdList();
    private EntityIdList realCategoryIds = new EntityIdList();
    private HashMap<Long, EntityId> currentManagers = new HashMap<>();
    private HashMap<EntityId, Long> currentManagerSince = new HashMap<>();
    private EntityId nextMatchId;
    private Long liveMatchId;

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
                    this.tournamentIds.add(childEntity.getId());
                    return true;
                } else if (entityName.startsWith("realcategories.")) {
                    this.realCategoryIds.add(childEntity.getId());
                    return true;
                } else if (entityName.startsWith("teams.")) {
                    this.uniqueTeamIds.add(childEntity.getId());
                    return true;
                }
                else if (entityName.startsWith("currentmanagers.")) {
                    long teamId = Long.parseLong(entityName.split("\\.|\\[")[1]);
                    this.currentManagers.put(teamId, childEntity.getId());
                    return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "livematchid": this.liveMatchId = node.asLong(); break;
            default:
                if (nodeName.startsWith("jersey.")) return true;
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            this.currentManagerSince.put(childEntity.getId(), node.asLong());
            return true;
        }
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsTeamVersus{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", matches=").append(matches);
        sb.append(", tournamentIds=").append(tournamentIds);
        sb.append(", uniqueTeamIds=").append(uniqueTeamIds);
        sb.append(", realCategoryIds=").append(realCategoryIds);
        sb.append(", currentManagers=").append(currentManagers);
        sb.append(", currentManagerSince=").append(currentManagerSince);
        sb.append(", nextMatchId=").append(nextMatchId);
        sb.append(", liveMatchId=").append(liveMatchId);
        sb.append('}');
        return sb.toString();
    }
}
