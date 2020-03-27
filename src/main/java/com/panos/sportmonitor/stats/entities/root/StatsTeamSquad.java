package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.ref.UniqueTeamManagerEntity;
import com.panos.sportmonitor.stats.entities.ref.UniqueTeamPlayerEntity;
import com.panos.sportmonitor.stats.entities.ref.UniqueTeamSeasonEntity;

public class StatsTeamSquad extends BaseRootEntity {
    private EntityId uniqueTeamId;

    public StatsTeamSquad(long timeStamp) {
        super(BaseRootEntityType.StatsTeamSquad, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("squadinfo.numberofplayers"))
            return true;
        else if (nodeName.equals("squadinfo.averagesquadage"))
            return true;
        else if (nodeName.startsWith("squadinfo.averagestartingxiage.")) {
            long seasonId = Long.parseLong(nodeName.substring(nodeName.lastIndexOf('.') + 1));
            double age = Double.parseDouble(node.asText());
            setAsyncProperty(nodeName, UniqueTeamSeasonEntity.createId(this.uniqueTeamId.getId(), seasonId), e -> ((UniqueTeamSeasonEntity)e).setAverageStartingXiAge(age));
        }
        else
            return super.handleProperty(nodeName, nodeType, node);
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("team")) {
            this.uniqueTeamId = childEntity.getId();
        }
        else if (entityName.equals("players[]"))
            return true;
        else if (entityName.equals("managers[]")) {
//            this.__lastManager = new UniqueTeamManagerEntity(this, uniqueTeamId.getId(), childEntity.getId().getId());
//            this.getRoot().addChildEntity(__lastManager);
            return true;
        }
        else if (entityName.startsWith("seasons.")) {
            UniqueTeamSeasonEntity uts = new UniqueTeamSeasonEntity(this, uniqueTeamId.getId(), childEntity.getId().getId());
            this.getRoot().addChildEntity(uts);
            return true;
        }
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
//            long startTime = node.asLong();
//            if (__lastManager != null && __lastManager.getManagerId() == childEntity.getId().getId())
//                __lastManager.setStartTime(startTime);
//            else
//                this.setAsyncProperty(nodeName, UniqueTeamPlayerEntity.createId(this.uniqueTeamId.getId(), childEntity.getId().getId()), e -> ((UniqueTeamPlayerEntity)e).setStartTime(startTime));
            return true;
        }
        return super.handleChildProperty(childEntity, nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        return "StatsTeamSquad{" + "id=" + getId() +
                ", uniqueTeamId=" + uniqueTeamId +
                '}';
    }
}
