package com.panos.sportmonitor.stats;

import java.math.BigInteger;

public class EntityId extends BigInteger {
    public EntityId(String val) {
        super(val);
    }
    public EntityId(long id) {
        super(String.format("%d", id));
    }
}
