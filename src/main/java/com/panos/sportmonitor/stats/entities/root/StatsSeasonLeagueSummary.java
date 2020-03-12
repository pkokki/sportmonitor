package com.panos.sportmonitor.stats.entities.root;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class StatsSeasonLeagueSummary extends RootEntity {
    private Integer matchesPlayed, goalsTotal;
    private Double matchesHomeWins, matchesDraws, matchesAwayWins, goalsPerMatch, goalsPerMatchHome, goalsPerMatchAway;
    private Double overUnder05, overUnder15, overUnder25, overUnder35, overUnder45, overUnder55;

    public StatsSeasonLeagueSummary(String name, long timeStamp) {
        super(name, timeStamp);
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
        final StringBuilder sb = new StringBuilder("StatsSeasonLeagueSummary{");
        sb.append("name=").append(getName());
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", matchesPlayed=").append(matchesPlayed);
        sb.append(", goalsTotal=").append(goalsTotal);
        sb.append(", matchesHomeWins=").append(matchesHomeWins);
        sb.append(", matchesDraws=").append(matchesDraws);
        sb.append(", matchesAwayWins=").append(matchesAwayWins);
        sb.append(", goalsPerMatch=").append(goalsPerMatch);
        sb.append(", goalsPerMatchHome=").append(goalsPerMatchHome);
        sb.append(", goalsPerMatchAway=").append(goalsPerMatchAway);
        sb.append(", overUnder05=").append(overUnder05);
        sb.append(", overUnder15=").append(overUnder15);
        sb.append(", overUnder25=").append(overUnder25);
        sb.append(", overUnder35=").append(overUnder35);
        sb.append(", overUnder45=").append(overUnder45);
        sb.append(", overUnder55=").append(overUnder55);
        sb.append('}');
        return sb.toString();
    }
}
