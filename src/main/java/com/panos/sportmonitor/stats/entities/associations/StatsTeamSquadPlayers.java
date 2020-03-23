package com.panos.sportmonitor.stats.entities.associations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.entities.PlayerEntity;

public class StatsTeamSquadPlayers extends BaseEntityAssociation {
    public StatsTeamSquadPlayers(BaseEntity master) {
        super(master, PlayerEntity.class);
    }

    @Override
    protected boolean handleProperty(BaseEntity childEntity, String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.equals("membersince.uts")) {
            this.setChildProperty(childEntity, "memberSince", node.asLong());
        } else
            return super.handleProperty(childEntity, nodeName, nodeType, node);
        return true;
    }
}
