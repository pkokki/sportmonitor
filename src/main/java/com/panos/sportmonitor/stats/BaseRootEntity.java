package com.panos.sportmonitor.stats;

import org.apache.ivy.util.StringUtils;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseRootEntity extends BaseTimeEntity {
    private final String __name;
    private final List<Tuple2<Integer, BaseEntity>> __childEntities = new ArrayList<>();

    public BaseRootEntity(BaseRootEntityType type, long timeStamp) {
        super(null, new EntityId((timeStamp << 2) + type.getId(), timeStamp, BaseRootEntity.class));
        this.__name = type.getName();
        this.addChildEntity(1, this);
    }

    public BaseRootEntity(BaseRootEntityType type, EntityId id) {
        super(null, id);
        this.__name = type.getName();
        this.addChildEntity(1, this);
    }

    public final String getName() {
        return __name;
    }

    public void addChildEntity(int level, BaseEntity entity) {
        __childEntities.add(new Tuple2<>(level, entity));
    }

    public void print() {
        for (Tuple2<Integer, BaseEntity> entry : __childEntities) {
            System.out.println(String.format("%s %s",
                    StringUtils.repeat("  ", entry._1),
                    entry._2
            ));
        }
    }

    public List<BaseEntity> getChildEntities() {
        return __childEntities.stream().map(e -> e._2).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + getName() + '\'' +
                ", ......}" +
                '}';
    }

}
