package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;

public class MatchSituationEntryEntity extends BaseEntity {
    private Integer time, injuryTime, safe, safeCount;
    private Integer homeAttack, homeDangerous, homeSafe,homeAttackCount, homeDangerousCount, homeSafeCount;
    private Integer awayAttack, awayDangerous, awaySafe,awayAttackCount, awayDangerousCount, awaySafeCount;

    public MatchSituationEntryEntity(BaseEntity parent, long id) {
        super(parent, id);
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
        final StringBuilder sb = new StringBuilder("MatchSituationEntryEntity{");
        sb.append("id=").append(getId());
        sb.append(", time=").append(time);
        sb.append(", injuryTime=").append(injuryTime);
        sb.append(", safe=").append(safe);
        sb.append(", safeCount=").append(safeCount);
        sb.append(", homeAttack=").append(homeAttack);
        sb.append(", homeDangerous=").append(homeDangerous);
        sb.append(", homeSafe=").append(homeSafe);
        sb.append(", homeAttackCount=").append(homeAttackCount);
        sb.append(", homeDangerousCount=").append(homeDangerousCount);
        sb.append(", homeSafeCount=").append(homeSafeCount);
        sb.append(", awayAttack=").append(awayAttack);
        sb.append(", awayDangerous=").append(awayDangerous);
        sb.append(", awaySafe=").append(awaySafe);
        sb.append(", awayAttackCount=").append(awayAttackCount);
        sb.append(", awayDangerousCount=").append(awayDangerousCount);
        sb.append(", awaySafeCount=").append(awaySafeCount);
        sb.append('}');
        return sb.toString();
    }
}
