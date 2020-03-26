package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.ref.SeasonOverUnderEntity;
import com.panos.sportmonitor.stats.entities.ref.TeamOverUnderEntity;

public class StatsSeasonOverUnder extends BaseRootEntity {
    private EntityId seasonId;

    public StatsSeasonOverUnder(long timeStamp) {
        super(BaseRootEntityType.StatsSeasonOverUnder, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "season": this.seasonId = new EntityId(childEntity); return true;
            case "league.totals": return true;
            default:
                if (entityName.startsWith("stats.")) {
                    return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.equals("league.totals")) {
            return new SeasonOverUnderEntity(this, seasonId, timeStamp);
        } else if (nodeName.startsWith("stats.")) {
            long teamId = Long.parseLong(nodeName.substring(nodeName.lastIndexOf('.') + 1).replace("[]", ""));
            return new TeamOverUnderEntity(this, teamId, timeStamp);
        }
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        if (nodeName.startsWith("values.")) return true;
        return super.handleProperty(nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatsSeasonOverUnder{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", seasonId=").append(seasonId);
        sb.append('}');
        return sb.toString();
    }
}
