package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityId;

public class MatchInfo extends BaseRootEntity {
    private EntityId sportId, matchId, realcategoryId, seasonId, tournamentId, stadiumId;

    public MatchInfo(long timeStamp) {
        super(BaseRootEntityType.MatchInfo, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.startsWith("jerseys.") || nodeName.startsWith("cities.")) return true;
        return super.handleProperty(nodeName, nodeType, node);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "sport": this.sportId = childEntity.getId(); break;
            case "match": this.matchId = childEntity.getId(); break;
            case "realcategory": this.realcategoryId = childEntity.getId(); break;
            case "season": this.seasonId = childEntity.getId(); break;
            case "tournament": this.tournamentId = childEntity.getId(); break;
            case "stadium": this.stadiumId = childEntity.getId(); break;
            default: return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }
}
