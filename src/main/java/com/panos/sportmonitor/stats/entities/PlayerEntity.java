package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class PlayerEntity extends BaseEntity {
    private String name;
    private long birthdate;
    private long nationalityId;
    private long memberSince;

    public PlayerEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "birthdate.uts": this.birthdate = node.asLong(); break;
            case "primarypositiontype": if (!node.isNull()) return false; break;
            case "membersince.uts": this.memberSince = node.asLong(); break;

            case "birthdate._doc":
            case "birthdate.time":
            case "birthdate.date":
            case "birthdate.tz":
            case "birthdate.tzoffset":
            case "membersince._doc":
            case "membersince.time":
            case "membersince.date":
            case "membersince.tz":
            case "membersince.tzoffset":
            case "haslogo":
                break;
            default:
                return super.handleProperty(nodeName, nodeType, node);
        }
        return true;
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("nationality")) {
            this.nationalityId = childEntity.getId();
        }
        else {
            return super.handleChildEntity(entityName, childEntity);
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlayerEntity{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", birthdate=").append(birthdate);
        sb.append(", nationalityId=").append(nationalityId);
        sb.append('}');
        return sb.toString();
    }
}
