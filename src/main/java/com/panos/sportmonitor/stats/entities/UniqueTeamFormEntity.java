package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class UniqueTeamFormEntity extends BaseEntity {
    private long uniqueTeamId;
    private long matchId;
    private double home3, home5, home7, home9;
    private double away3, away5, away7, away9;
    private double total3, total5, total7, total9;

    public UniqueTeamFormEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "uniqueteamid": this.uniqueTeamId = Long.parseLong(node.asText()); break;
            case "matchid": this.matchId = node.asLong(); break;
            case "form.home.3": this.home3 = node.asDouble(); break;
            case "form.home.5": this.home5 = node.asDouble(); break;
            case "form.home.7": this.home7 = node.asDouble(); break;
            case "form.home.9": this.home9 = node.asDouble(); break;
            case "form.away.3": this.away3 = node.asDouble(); break;
            case "form.away.5": this.away5 = node.asDouble(); break;
            case "form.away.7": this.away7 = node.asDouble(); break;
            case "form.away.9": this.away9 = node.asDouble(); break;
            case "form.total.3": this.total3 = node.asDouble(); break;
            case "form.total.5": this.total5 = node.asDouble(); break;
            case "form.total.7": this.total7 = node.asDouble(); break;
            case "form.total.9": this.total9 = node.asDouble(); break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UniqueTeamFormEntity{");
        sb.append("id=").append(getId());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append(", matchId=").append(matchId);
        sb.append(", home3=").append(home3);
        sb.append(", home5=").append(home5);
        sb.append(", home7=").append(home7);
        sb.append(", home9=").append(home9);
        sb.append(", away3=").append(away3);
        sb.append(", away5=").append(away5);
        sb.append(", away7=").append(away7);
        sb.append(", away9=").append(away9);
        sb.append(", total3=").append(total3);
        sb.append(", total5=").append(total5);
        sb.append(", total7=").append(total7);
        sb.append(", total9=").append(total9);
        sb.append('}');
        return sb.toString();
    }
}
