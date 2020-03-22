package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseRootEntity;
import com.panos.sportmonitor.stats.BaseRootEntityType;

public class NullRootEntity extends BaseRootEntity {
    public NullRootEntity(long timeStamp) {
        super(BaseRootEntityType.NullRoot, timeStamp);
    }
}
