package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.*;
import com.panos.sportmonitor.stats.entities.ref.SeasonMetaEntity;

public class StatsSeasonMeta extends BaseRootEntity {
    private final SeasonMetaEntity __seasonMeta;
    private EntityId seasonId;

    public StatsSeasonMeta(long seasonId, long timeStamp) {
        super(BaseRootEntityType.StatsSeasonMeta, timeStamp);
        __seasonMeta = new SeasonMetaEntity(this, seasonId, timeStamp);
        this.addChildEntity(__seasonMeta);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("season")) this.seasonId = childEntity.getId();
        return __seasonMeta.setEntity(entityName, childEntity);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        return __seasonMeta.setProperty(nodeName, nodeType, node);
    }

    @Override
    public String toString() {
        return "StatsSeasonMeta{" + "name='" + getName() + '\'' +
                '}';
    }
}
