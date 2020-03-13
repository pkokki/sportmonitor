package com.panos.sportmonitor.stats;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.BaseTimeEntity;
import org.apache.ivy.util.StringUtils;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRootEntity extends BaseTimeEntity {
    private final String name;
    private final List<Tuple2<Integer, BaseEntity>> registry = new ArrayList<>();

    public BaseRootEntity(String name, long timeStamp) {
        super(null, 0, timeStamp);
        this.name = name;
        this.register(1, this);
    }

    @Override
    public final long getId() {
        System.err.println("getId in root: " + this.getClass().getSimpleName());
        return this.getId();
    }
    public final String getName() {
        return name;
    }

    public void register(int level, BaseEntity entity) {
        registry.add(new Tuple2<>(level, entity));
    }

    public void print() {
        for (Tuple2<Integer, BaseEntity> entry : registry) {
            System.out.println(String.format("%s %s",
                    StringUtils.repeat("  ", entry._1),
                    entry._2
            ));
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append("{");
        sb.append("name='").append(getName()).append('\'');
        sb.append(", timeStamp=").append(getTimeStamp());
        sb.append(", ......}");
        sb.append('}');
        return sb.toString();
    }
}
