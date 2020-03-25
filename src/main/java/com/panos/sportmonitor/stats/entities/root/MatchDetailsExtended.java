package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.MatchEntity;
import com.panos.sportmonitor.stats.entities.ref.MatchDetailsEntryEntity;

public class MatchDetailsExtended extends BaseRootEntity {
    private EntityId matchId;

    public MatchDetailsExtended(long timeStamp) {
        super(BaseRootEntityType.MatchDetailsExtended, timeStamp);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_matchid": this.matchId = new EntityId(MatchEntity.class, node.asLong()); break;

            case "teams.home":
            case "teams.away":
            case "index[]":
                break;
            default:
                if (nodeName.startsWith("types.")) {
                    return true;
                }
                else
                    return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public BaseEntity tryCreateChildEntity(long timeStamp, String nodeName, JsonNode node) {
        if (nodeName.startsWith("values.")) {
            String typeId = nodeName.substring(7);
            return new MatchDetailsEntryEntity(this, this.matchId, timeStamp, typeId);
        }
        return super.tryCreateChildEntity(timeStamp, nodeName, node);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.startsWith("values.")) {
            return true;
        }
        return super.handleChildEntity(entityName, childEntity);
    }

    @Override
    public String toString() {
        return "MatchDetailsExtended{" + "name='" + getName() + '\'' +
                ", matchId=" + matchId +
                '}';
    }
}
