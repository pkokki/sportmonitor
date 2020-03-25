package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.MatchEntity;
import com.panos.sportmonitor.stats.entities.ref.MatchSituationEntryEntity;

public class StatsMatchSituation extends BaseRootEntity {
    private EntityId matchId;

    public StatsMatchSituation(long timeStamp) {
        super(BaseRootEntityType.StatsMatchSituation, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if ("matchid".equals(nodeName)) {
            this.matchId = new EntityId(MatchEntity.class, node.asLong());
            return true;
        } else {
            return super.handleProperty(nodeName, nodeType, node);
        }
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.equals("data[]")) {
            return new MatchSituationEntryEntity(this, this.matchId, node.get("time").asInt(), node.get("injurytime").asInt());
        }
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if ("data[]".equals(entityName))
            return true;
        else
            return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        return "StatsMatchSituation{" + "name='" + getName() + '\'' +
                ", matchId=" + matchId +
                '}';
    }
}
