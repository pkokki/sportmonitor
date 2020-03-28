package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class SeasonGoalStatsEntity extends BaseEntity {
    private Integer matches, scoredSum, scored0015, scored1630, scored3145, scored4660, scored6175, scored7690;

    public SeasonGoalStatsEntity(BaseEntity parent, EntityId seasonId, long timeStamp) {
        super(parent, createId(seasonId, timeStamp));
    }

    private static EntityId createId(EntityId seasonId, long timeStamp) {
        return new EntityId(SeasonGoalStatsEntity.class,
                new EntityId[] { seasonId },
                new EntityKey[] { EntityKey.Timestamp(timeStamp) }
        );
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "matches": this.matches = node.asInt(); break;
            case "scoredsum": this.scoredSum = node.asInt(); break;
            case "scored.0-15": this.scored0015 = node.asInt(); break;
            case "scored.16-30": this.scored1630 = node.asInt(); break;
            case "scored.31-45": this.scored3145 = node.asInt(); break;
            case "scored.46-60": this.scored4660 = node.asInt(); break;
            case "scored.61-75": this.scored6175 = node.asInt(); break;
            case "scored.76-90": this.scored7690 = node.asInt(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "StatsSeasonGoals{" + "id=" + getId() +
                ", matches=" + matches +
                ", scoredSum=" + scoredSum +
                ", scored0015=" + scored0015 +
                ", scored1630=" + scored1630 +
                ", scored3145=" + scored3145 +
                ", scored4660=" + scored4660 +
                ", scored6175=" + scored6175 +
                ", scored7690=" + scored7690 +
                '}';
    }
}
