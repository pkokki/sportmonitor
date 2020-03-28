package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;

public class SeasonLeagueSummaryEntity extends BaseEntity {
    private Integer matchesPlayed, goalsTotal;
    private Double matchesHomeWins, matchesDraws, matchesAwayWins, goalsPerMatch, goalsPerMatchHome, goalsPerMatchAway;
    private Double overUnder05, overUnder15, overUnder25, overUnder35, overUnder45, overUnder55;

    public SeasonLeagueSummaryEntity(BaseEntity parent, long seasonId, long timeStamp) {
        super(parent, new EntityId(SeasonLeagueSummaryEntity.class, new EntityKey("seasonId", seasonId), EntityKey.Timestamp(timeStamp)));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "matches.played": this.matchesPlayed = node.asInt(); break;
            case "matches.home_wins": this.matchesHomeWins = node.asDouble(); break;
            case "matches.draws": this.matchesDraws = node.asDouble(); break;
            case "matches.away_wins": this.matchesAwayWins = node.asDouble(); break;
            case "goals.total": this.goalsTotal = node.asInt(); break;
            case "goals.pr_match": this.goalsPerMatch = node.asDouble(); break;
            case "goals.pr_match_home": this.goalsPerMatchHome = node.asDouble(); break;
            case "goals.pr_match_away": this.goalsPerMatchAway = node.asDouble(); break;
            case "overunder.0.5": this.overUnder05 = node.asDouble(); break;
            case "overunder.1.5": this.overUnder15 = node.asDouble(); break;
            case "overunder.2.5": this.overUnder25 = node.asDouble(); break;
            case "overunder.3.5": this.overUnder35 = node.asDouble(); break;
            case "overunder.4.5": this.overUnder45 = node.asDouble(); break;
            case "overunder.5.5": this.overUnder55 = node.asDouble(); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "SeasonLeagueSummary{" + "id=" + getId() +
                ", matchesPlayed=" + matchesPlayed +
                ", goalsTotal=" + goalsTotal +
                ", matchesHomeWins=" + matchesHomeWins +
                ", matchesDraws=" + matchesDraws +
                ", matchesAwayWins=" + matchesAwayWins +
                ", goalsPerMatch=" + goalsPerMatch +
                ", goalsPerMatchHome=" + goalsPerMatchHome +
                ", goalsPerMatchAway=" + goalsPerMatchAway +
                ", overUnder05=" + overUnder05 +
                ", overUnder15=" + overUnder15 +
                ", overUnder25=" + overUnder25 +
                ", overUnder35=" + overUnder35 +
                ", overUnder45=" + overUnder45 +
                ", overUnder55=" + overUnder55 +
                '}';
    }
}
