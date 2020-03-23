package com.panos.sportmonitor.stats.entities.associations;

import com.panos.sportmonitor.stats.BaseEntity;

public class SimpleEntityAssociation extends BaseEntityAssociation {
    public SimpleEntityAssociation(BaseEntity master, Class<? extends BaseEntity> childEntityClass) {
        super(master, childEntityClass);
    }
}
