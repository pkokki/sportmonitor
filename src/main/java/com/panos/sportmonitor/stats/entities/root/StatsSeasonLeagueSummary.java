package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.SeasonEntity;
import com.panos.sportmonitor.stats.entities.ref.SeasonLeagueSummaryEntity;

public class StatsSeasonLeagueSummary extends BaseRootEntity {
    private final SeasonLeagueSummaryEntity __summary;
    private final EntityId seasonId;

    public StatsSeasonLeagueSummary(long seasonId, long timeStamp) {
        super(BaseRootEntityType.StatsSeasonLeagueSummary, timeStamp);
        __summary = new SeasonLeagueSummaryEntity(this, seasonId, timeStamp);
        this.addChildEntity(__summary);
        this.seasonId = SeasonEntity.createId(seasonId);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        return __summary.setProperty(nodeName, nodeType, node);
    }
}
