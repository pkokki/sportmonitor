package com.panos.sportmonitor.stats.entities.ref;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.EntityKey;
import com.panos.sportmonitor.stats.entities.MatchEntity;

import java.util.LinkedList;

public class TeamFormEntryEntity extends BaseEntity {
    private Integer _index;
    private String group_name;
    private String typeId;
    private String value;
    private Boolean homeMatch;
    private Boolean neutralGround;
    private EntityId matchId;

    public TeamFormEntryEntity(BaseEntity parent, String name, int index) {
        super(parent, createId(parent.getId(), name, index));
    }

    public static EntityId createId(EntityId masterId, String name, int index) {
        LinkedList<EntityKey> keys = new LinkedList<>(masterId.getKeys());
        keys.add(new EntityKey("name", name));
        keys.add(new EntityKey("index", index));
        return new EntityId(TeamFormTableEntity.class, keys);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "_index": this._index = node.asInt(); break;
            case "group": this.group_name = node.asText(); break;
            case "typeid": this.typeId = node.asText(); break;
            case "value": this.value = node.asText(); break;
            case "homematch": this.homeMatch = node.asBoolean(); break;
            case "neutralground": this.neutralGround = node.asBoolean(); break;
            case "matchid": this.matchId = new EntityId(MatchEntity.class, node.asLong()); break;
            default: return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    public String toString() {
        return "TeamFormEntryEntity{" + "id=" + getId() +
                ", group_name='" + group_name + '\'' +
                ", index=" + _index +
                ", typeId='" + typeId + '\'' +
                ", value='" + value + '\'' +
                ", homeMatch=" + homeMatch +
                ", neutralGround=" + neutralGround +
                ", matchId=" + matchId +
                '}';
    }
}
