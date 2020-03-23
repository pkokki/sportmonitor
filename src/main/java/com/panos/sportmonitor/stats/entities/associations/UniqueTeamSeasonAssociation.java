package com.panos.sportmonitor.stats.entities.associations;

import com.panos.sportmonitor.stats.entities.SeasonEntity;
import com.panos.sportmonitor.stats.entities.UniqueTeamEntity;

import javax.validation.constraints.NotNull;

public class UniqueTeamSeasonAssociation extends BaseEntityAssociation  {
    public UniqueTeamSeasonAssociation(@NotNull UniqueTeamEntity master) {
        super(master, SeasonEntity.class);
    }
}
