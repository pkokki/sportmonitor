package com.panos.sportmonitor.stats;

import org.apache.ivy.util.StringUtils;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseRootEntity extends BaseTimeEntity {
    private final String name;
    private final List<Tuple2<Integer, BaseEntity>> childEntities = new ArrayList<>();

    public BaseRootEntity(BaseRootEntityType type, long timeStamp) {
        super(null, combine(type, timeStamp), timeStamp);
        this.name = type.getName();
        this.addChildEntity(1, this);
    }

    private static EntityId combine(BaseRootEntityType type, long timeStamp) {
        try {
            return new EntityId(String.format("1%013d%018d", timeStamp, type.getId()));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(String.format("Unable to combine %d and %d -- %s", type.getId(), timeStamp, ex));
        }
    }

    public final String getName() {
        return name;
    }

    public void addChildEntity(int level, BaseEntity entity) {
        childEntities.add(new Tuple2<>(level, entity));
    }

    public void print() {
        for (Tuple2<Integer, BaseEntity> entry : childEntities) {
            System.out.println(String.format("%s %s",
                    StringUtils.repeat("  ", entry._1),
                    entry._2
            ));
        }
    }

    public List<BaseEntity> getChildEntities() {
        return childEntities.stream().map(e -> e._2).collect(Collectors.toList());
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
