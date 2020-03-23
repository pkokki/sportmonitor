package com.panos.sportmonitor.stats.entities.associations;

import com.panos.sportmonitor.stats.BaseEntity;

import javax.validation.constraints.NotNull;

public class SimpleEntityAssociation extends BaseEntityAssociation {
    public SimpleEntityAssociation(@NotNull BaseEntity master, @NotNull Class<? extends BaseEntity> childEntityClass) {
        super(master, childEntityClass);
    }
}
