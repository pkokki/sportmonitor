package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.entities.MatchEntity;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;

public class UniqueTeamFormEntity extends BaseEntity {
    private EntityId uniqueTeamId;
    private EntityId matchId;
    private Double home3, home5, home7, home9;
    private Double away3, away5, away7, away9;
    private Double total3, total5, total7, total9;

    public UniqueTeamFormEntity(BaseEntity parent, long id) {
        super(parent, new EntityId(id, UniqueTeamFormEntity.class));
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "uniqueteamid": this.uniqueTeamId = new EntityId(node.asLong(), UniqueTeamEntity.class); break;
            case "matchid": this.matchId = new EntityId(node.asLong(), MatchEntity.class); break;
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
        return "UniqueTeamFormEntity{" + "id=" + getId() +
                ", uniqueTeamId=" + uniqueTeamId +
                ", matchId=" + matchId +
                ", home3=" + home3 +
                ", home5=" + home5 +
                ", home7=" + home7 +
                ", home9=" + home9 +
                ", away3=" + away3 +
                ", away5=" + away5 +
                ", away7=" + away7 +
                ", away9=" + away9 +
                ", total3=" + total3 +
                ", total5=" + total5 +
                ", total7=" + total7 +
                ", total9=" + total9 +
                '}';
    }
}
