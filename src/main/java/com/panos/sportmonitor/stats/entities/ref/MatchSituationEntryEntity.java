package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class MatchSituationEntryEntity extends BaseEntity {
    private Integer time, injuryTime, safe, safeCount;
    private Integer homeAttack, homeDangerous, homeSafe,homeAttackCount, homeDangerousCount, homeSafeCount;
    private Integer awayAttack, awayDangerous, awaySafe,awayAttackCount, awayDangerousCount, awaySafeCount;

    public MatchSituationEntryEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, MatchSituationEntryEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "time": this.time = node.asInt(); break;
            case "injurytime": this.injuryTime = node.asInt(); break;
            case "safe": this.safe = node.asInt(); break;
            case "safecount": this.safeCount = node.asInt(); break;
            case "home.attack": this.homeAttack = node.asInt(); break;
            case "home.dangerous": this.homeDangerous = node.asInt(); break;
            case "home.safe": this.homeSafe = node.asInt(); break;
            case "home.attackcount": this.homeAttackCount = node.asInt(); break;
            case "home.dangerouscount": this.homeDangerousCount = node.asInt(); break;
            case "home.safecount": this.homeSafeCount = node.asInt(); break;
            case "away.attack": this.awayAttack = node.asInt(); break;
            case "away.dangerous": this.awayDangerous = node.asInt(); break;
            case "away.safe": this.awaySafe = node.asInt(); break;
            case "away.attackcount": this.awayAttackCount = node.asInt(); break;
            case "away.dangerouscount": this.awayDangerousCount = node.asInt(); break;
            case "away.safecount": this.awaySafeCount = node.asInt(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "MatchSituationEntryEntity{" + "id=" + getId() +
                ", time=" + time +
                ", injuryTime=" + injuryTime +
                ", safe=" + safe +
                ", safeCount=" + safeCount +
                ", homeAttack=" + homeAttack +
                ", homeDangerous=" + homeDangerous +
                ", homeSafe=" + homeSafe +
                ", homeAttackCount=" + homeAttackCount +
                ", homeDangerousCount=" + homeDangerousCount +
                ", homeSafeCount=" + homeSafeCount +
                ", awayAttack=" + awayAttack +
                ", awayDangerous=" + awayDangerous +
                ", awaySafe=" + awaySafe +
                ", awayAttackCount=" + awayAttackCount +
                ", awayDangerousCount=" + awayDangerousCount +
                ", awaySafeCount=" + awaySafeCount +
                '}';
    }
}
