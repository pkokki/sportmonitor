package com.panos.sportmonitor.stats.entities.associations;

import com.panos.sportmonitor.stats.entities.SeasonEntity;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;

public class UniqueTeamSeasonAssociation extends BaseEntityAssociation  {
    public UniqueTeamSeasonAssociation(UniqueTeamEntity master) {
        super(master, SeasonEntity.class);
    }
}
