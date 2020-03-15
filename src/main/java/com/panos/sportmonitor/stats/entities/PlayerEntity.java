package com.panos.sportmonitor.stats.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;

public class PlayerEntity extends BaseEntity {
    private String name, fullName;
    private Long birthDate;
    private EntityId nationalityId, secondNationalityId;
    private EntityId positionId;

    public PlayerEntity(BaseEntity parent, long id) {
        super(parent, id);
    }

    @Override
    protected boolean handleProperty(String nodeName, JsonNodeType nodeType, JsonNode node) {
        switch (nodeName) {
            case "name": this.name = node.asText(); break;
            case "fullname": this.fullName = node.asText(); break;
            case "birthdate.uts": this.birthDate = node.asLong(); break;
            case "primarypositiontype": if (!node.isNull()) return false; break;
            case "membersince.uts": return getParent().setChildProperty(this, nodeName, nodeType, node);
            case "jerseynumber":
            case "shirtnumber":
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
    public boolean handleAuxId(long auxEntityId) {
        return true;
    }

    @Override
    public JsonNode transformChildNode(String currentNodeName, int index, JsonNode childNode) {
        if (currentNodeName.equals("position")) {
            ObjectNode objNode = (ObjectNode)childNode;
            objNode.put("_doc", "player_position_type");
        }
        return super.transformChildNode(currentNodeName, index, childNode);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        if (entityName.equals("nationality")) {
            this.nationalityId = childEntity.getId();
        }
        else  if (entityName.equals("secondarynationality")) {
            this.secondNationalityId = childEntity.getId();
        }
        else if (entityName.equals("position")) {
            this.positionId = childEntity.getId();
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
        sb.append(", fullname='").append(fullName).append('\'');
        sb.append(", birthdate=").append(birthDate);
        sb.append(", nationalityId=").append(nationalityId);
        sb.append(", secondNationalityId=").append(secondNationalityId);
        sb.append(", positionId=").append(positionId);
        sb.append('}');
        return sb.toString();
    }
}
