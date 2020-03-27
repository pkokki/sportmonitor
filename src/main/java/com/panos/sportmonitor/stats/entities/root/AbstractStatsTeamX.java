package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;
import com.panos.sportmonitor.stats.EntityId;

public class AbstractStatsTeamX extends BaseRootEntity {
    private EntityId uniqueTeamId;

    public AbstractStatsTeamX(BaseRootEntityType type, long timeStamp) {
        super(type, timeStamp);
    }

    @Override
    protected boolean handleChildEntity(String entityName, BaseEntity childEntity) {
        switch (entityName) {
            case "team": this.uniqueTeamId = new EntityId(childEntity); return true;
            case "matches[]": return true;
            default:
                if (entityName.startsWith("tournaments.")) {
                    return true;
                } else if (entityName.startsWith("uniquetournaments.")) {
                    return true;
                } else if (entityName.startsWith("realcategories.")) {
                    return true;
                }
                return super.handleChildEntity(entityName, childEntity);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append("{");
        sb.append("name=").append(getName());
        sb.append(", uniqueTeamId=").append(uniqueTeamId);
        sb.append('}');
        return sb.toString();
    }
}
