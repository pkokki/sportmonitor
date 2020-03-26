package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public abstract class OverUnderEntryEntity extends BaseEntity {
    private Integer matches;
    private Double goalsscoredFtAverage;
    private Integer goalsscoredFtTotal;
    private Integer goalsscoredFtMatches;
    private Double goalsscoredP1Average;
    private Integer goalsscoredP1Total;
    private Integer goalsscoredP1Matches;
    private Double goalsscoredP2Average;
    private Integer goalsscoredP2Total;
    private Integer goalsscoredP2Matches;
    private Double concededFtAverage;
    private Integer concededFtTotal;
    private Integer concededFtMatches;
    private Double concededP1Average;
    private Integer concededP1Total;
    private Integer concededP1Matches;
    private Double concededP2Average;
    private Integer concededP2Total;
    private Integer concededP2Matches;
    private Integer ft05Totalover;
    private Integer ft05Over;
    private Integer ft05Under;
    private Integer ft15Totalover;
    private Integer ft15Over;
    private Integer ft15Under;
    private Integer ft25Totalover;
    private Integer ft25Over;
    private Integer ft25Under;
    private Integer ft35Totalover;
    private Integer ft35Over;
    private Integer ft35Under;
    private Integer ft45Totalover;
    private Integer ft45Over;
    private Integer ft45Under;
    private Integer ft55Totalover;
    private Integer ft55Over;
    private Integer ft55Under;
    private Integer p105Totalover;
    private Integer p105Over;
    private Integer p105Under;
    private Integer p115Totalover;
    private Integer p115Over;
    private Integer p115Under;
    private Integer p125Totalover;
    private Integer p125Over;
    private Integer p125Under;
    private Integer p135Totalover;
    private Integer p135Over;
    private Integer p135Under;
    private Integer p145Totalover;
    private Integer p145Over;
    private Integer p145Under;
    private Integer p155Totalover;
    private Integer p155Over;
    private Integer p155Under;
    private Integer p205Totalover;
    private Integer p205Over;
    private Integer p205Under;
    private Integer p215Totalover;
    private Integer p215Over;
    private Integer p215Under;
    private Integer p225Totalover;
    private Integer p225Over;
    private Integer p225Under;
    private Integer p235Totalover;
    private Integer p235Over;
    private Integer p235Under;
    private Integer p245Totalover;
    private Integer p245Over;
    private Integer p245Under;
    private Integer p255Totalover;
    private Integer p255Over;
    private Integer p255Under;

    public OverUnderEntryEntity(BaseEntity parent, EntityId id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch(nodeName) {
            case "matches": this.matches = node.asInt(); break;
            case "goalsscored.ft.average": this.goalsscoredFtAverage = node.asDouble(); break;
            case "goalsscored.ft.total": this.goalsscoredFtTotal = node.asInt(); break;
            case "goalsscored.ft.matches": this.goalsscoredFtMatches = node.asInt(); break;
            case "goalsscored.p1.average": this.goalsscoredP1Average = node.asDouble(); break;
            case "goalsscored.p1.total": this.goalsscoredP1Total = node.asInt(); break;
            case "goalsscored.p1.matches": this.goalsscoredP1Matches = node.asInt(); break;
            case "goalsscored.p2.average": this.goalsscoredP2Average = node.asDouble(); break;
            case "goalsscored.p2.total": this.goalsscoredP2Total = node.asInt(); break;
            case "goalsscored.p2.matches": this.goalsscoredP2Matches = node.asInt(); break;
            case "conceded.ft.average": this.concededFtAverage = node.asDouble(); break;
            case "conceded.ft.total": this.concededFtTotal = node.asInt(); break;
            case "conceded.ft.matches": this.concededFtMatches = node.asInt(); break;
            case "conceded.p1.average": this.concededP1Average = node.asDouble(); break;
            case "conceded.p1.total": this.concededP1Total = node.asInt(); break;
            case "conceded.p1.matches": this.concededP1Matches = node.asInt(); break;
            case "conceded.p2.average": this.concededP2Average = node.asDouble(); break;
            case "conceded.p2.total": this.concededP2Total = node.asInt(); break;
            case "conceded.p2.matches": this.concededP2Matches = node.asInt(); break;
            case "ft.0.5.totalover": this.ft05Totalover = node.asInt(); break;
            case "ft.0.5.over": this.ft05Over = node.asInt(); break;
            case "ft.0.5.under": this.ft05Under = node.asInt(); break;
            case "ft.1.5.totalover": this.ft15Totalover = node.asInt(); break;
            case "ft.1.5.over": this.ft15Over = node.asInt(); break;
            case "ft.1.5.under": this.ft15Under = node.asInt(); break;
            case "ft.2.5.totalover": this.ft25Totalover = node.asInt(); break;
            case "ft.2.5.over": this.ft25Over = node.asInt(); break;
            case "ft.2.5.under": this.ft25Under = node.asInt(); break;
            case "ft.3.5.totalover": this.ft35Totalover = node.asInt(); break;
            case "ft.3.5.over": this.ft35Over = node.asInt(); break;
            case "ft.3.5.under": this.ft35Under = node.asInt(); break;
            case "ft.4.5.totalover": this.ft45Totalover = node.asInt(); break;
            case "ft.4.5.over": this.ft45Over = node.asInt(); break;
            case "ft.4.5.under": this.ft45Under = node.asInt(); break;
            case "ft.5.5.totalover": this.ft55Totalover = node.asInt(); break;
            case "ft.5.5.over": this.ft55Over = node.asInt(); break;
            case "ft.5.5.under": this.ft55Under = node.asInt(); break;
            case "p1.0.5.totalover": this.p105Totalover = node.asInt(); break;
            case "p1.0.5.over": this.p105Over = node.asInt(); break;
            case "p1.0.5.under": this.p105Under = node.asInt(); break;
            case "p1.1.5.totalover": this.p115Totalover = node.asInt(); break;
            case "p1.1.5.over": this.p115Over = node.asInt(); break;
            case "p1.1.5.under": this.p115Under = node.asInt(); break;
            case "p1.2.5.totalover": this.p125Totalover = node.asInt(); break;
            case "p1.2.5.over": this.p125Over = node.asInt(); break;
            case "p1.2.5.under": this.p125Under = node.asInt(); break;
            case "p1.3.5.totalover": this.p135Totalover = node.asInt(); break;
            case "p1.3.5.over": this.p135Over = node.asInt(); break;
            case "p1.3.5.under": this.p135Under = node.asInt(); break;
            case "p1.4.5.totalover": this.p145Totalover = node.asInt(); break;
            case "p1.4.5.over": this.p145Over = node.asInt(); break;
            case "p1.4.5.under": this.p145Under = node.asInt(); break;
            case "p1.5.5.totalover": this.p155Totalover = node.asInt(); break;
            case "p1.5.5.over": this.p155Over = node.asInt(); break;
            case "p1.5.5.under": this.p155Under = node.asInt(); break;
            case "p2.0.5.totalover": this.p205Totalover = node.asInt(); break;
            case "p2.0.5.over": this.p205Over = node.asInt(); break;
            case "p2.0.5.under": this.p205Under = node.asInt(); break;
            case "p2.1.5.totalover": this.p215Totalover = node.asInt(); break;
            case "p2.1.5.over": this.p215Over = node.asInt(); break;
            case "p2.1.5.under": this.p215Under = node.asInt(); break;
            case "p2.2.5.totalover": this.p225Totalover = node.asInt(); break;
            case "p2.2.5.over": this.p225Over = node.asInt(); break;
            case "p2.2.5.under": this.p225Under = node.asInt(); break;
            case "p2.3.5.totalover": this.p235Totalover = node.asInt(); break;
            case "p2.3.5.over": this.p235Over = node.asInt(); break;
            case "p2.3.5.under": this.p235Under = node.asInt(); break;
            case "p2.4.5.totalover": this.p245Totalover = node.asInt(); break;
            case "p2.4.5.over": this.p245Over = node.asInt(); break;
            case "p2.4.5.under": this.p245Under = node.asInt(); break;
            case "p2.5.5.totalover": this.p255Totalover = node.asInt(); break;
            case "p2.5.5.over": this.p255Over = node.asInt(); break;
            case "p2.5.5.under": this.p255Under = node.asInt(); break;
            default: super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "id=" + getId() +
                ", matches=" + matches +
                ", goalsscoredFtAverage=" + goalsscoredFtAverage +
                ", goalsscoredFtTotal=" + goalsscoredFtTotal +
                ", goalsscoredFtMatches=" + goalsscoredFtMatches +
                ", goalsscoredP1Average=" + goalsscoredP1Average +
                ", goalsscoredP1Total=" + goalsscoredP1Total +
                ", goalsscoredP1Matches=" + goalsscoredP1Matches +
                ", goalsscoredP2Average=" + goalsscoredP2Average +
                ", goalsscoredP2Total=" + goalsscoredP2Total +
                ", goalsscoredP2Matches=" + goalsscoredP2Matches +
                ", concededFtAverage=" + concededFtAverage +
                ", concededFtTotal=" + concededFtTotal +
                ", concededFtMatches=" + concededFtMatches +
                ", concededP1Average=" + concededP1Average +
                ", concededP1Total=" + concededP1Total +
                ", concededP1Matches=" + concededP1Matches +
                ", concededP2Average=" + concededP2Average +
                ", concededP2Total=" + concededP2Total +
                ", concededP2Matches=" + concededP2Matches +
                ", ft05Totalover=" + ft05Totalover +
                ", ft05Over=" + ft05Over +
                ", ft05Under=" + ft05Under +
                ", ft15Totalover=" + ft15Totalover +
                ", ft15Over=" + ft15Over +
                ", ft15Under=" + ft15Under +
                ", ft25Totalover=" + ft25Totalover +
                ", ft25Over=" + ft25Over +
                ", ft25Under=" + ft25Under +
                ", ft35Totalover=" + ft35Totalover +
                ", ft35Over=" + ft35Over +
                ", ft35Under=" + ft35Under +
                ", ft45Totalover=" + ft45Totalover +
                ", ft45Over=" + ft45Over +
                ", ft45Under=" + ft45Under +
                ", ft55Totalover=" + ft55Totalover +
                ", ft55Over=" + ft55Over +
                ", ft55Under=" + ft55Under +
                ", p105Totalover=" + p105Totalover +
                ", p105Over=" + p105Over +
                ", p105Under=" + p105Under +
                ", p115Totalover=" + p115Totalover +
                ", p115Over=" + p115Over +
                ", p115Under=" + p115Under +
                ", p125Totalover=" + p125Totalover +
                ", p125Over=" + p125Over +
                ", p125Under=" + p125Under +
                ", p135Totalover=" + p135Totalover +
                ", p135Over=" + p135Over +
                ", p135Under=" + p135Under +
                ", p145Totalover=" + p145Totalover +
                ", p145Over=" + p145Over +
                ", p145Under=" + p145Under +
                ", p155Totalover=" + p155Totalover +
                ", p155Over=" + p155Over +
                ", p155Under=" + p155Under +
                ", p205Totalover=" + p205Totalover +
                ", p205Over=" + p205Over +
                ", p205Under=" + p205Under +
                ", p215Totalover=" + p215Totalover +
                ", p215Over=" + p215Over +
                ", p215Under=" + p215Under +
                ", p225Totalover=" + p225Totalover +
                ", p225Over=" + p225Over +
                ", p225Under=" + p225Under +
                ", p235Totalover=" + p235Totalover +
                ", p235Over=" + p235Over +
                ", p235Under=" + p235Under +
                ", p245Totalover=" + p245Totalover +
                ", p245Over=" + p245Over +
                ", p245Under=" + p245Under +
                ", p255Totalover=" + p255Totalover +
                ", p255Over=" + p255Over +
                ", p255Under=" + p255Under;
    }
}
