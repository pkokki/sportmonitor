package com.panos.sportmonitor.stats.entities.root;

import com.panos.sportmonitor.stats.entities.BaseEntity;
import org.apache.ivy.util.StringUtils;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class RootEntity extends BaseEntity {
    private final String name;
    private final List<Tuple2<Integer, BaseEntity>> registry = new ArrayList<>();

    public RootEntity(String name) {
        super(null, 0);
        this.name = name;
        this.register(1, this);
    }

    public String getName() {
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
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
