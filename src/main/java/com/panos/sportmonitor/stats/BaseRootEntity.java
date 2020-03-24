package com.panos.sportmonitor.stats;

import org.apache.ivy.util.StringUtils;
import scala.Tuple2;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class BaseRootEntity extends BaseTimeEntity {
    private final String __name;
    private final List<Tuple2<Integer, BaseEntity>> __childEntities = new ArrayList<>();
    private final HashMap<EntityId, List<Tuple2<String, Consumer<BaseEntity>>>> __consumers = new HashMap<>();

    public BaseRootEntity(BaseRootEntityType type, long timeStamp) {
        super(null, new EntityId(BaseRootEntity.class, (timeStamp << 2) + type.getId(), timeStamp));
        this.__name = type.getName();
        this.addChildEntity(1, this);
    }

    public final String getName() {
        return __name;
    }

    public void addChildEntity(int level, BaseEntity entity) {
        __childEntities.add(new Tuple2<>(level, entity));
        handleAsyncProperties(entity);
    }

    private void handleAsyncProperties(BaseEntity entity) {
        EntityId id = entity.getId();
        List<Tuple2<String, Consumer<BaseEntity>>> consumers = __consumers.get(id);
        if (consumers != null) {
            consumers.forEach(c -> c._2.accept(entity));
            __consumers.remove(id);
        }
    }

    public void endTraverse() {
        if (!__consumers.isEmpty()) {
            StatsConsole.printlnWarn(String.format("%s [UNHANDLED ASYNC CONSUMERS]: %s", this.getClass().getSimpleName(),
                    __consumers.entrySet().stream()
                            .map(e -> e.getKey() + ":" + e.getValue().stream().map(t -> t._1).collect(Collectors.joining(","))).
                            collect(Collectors.joining(", ", "[", "]"))));
        }
    }

    @Override
    protected final void setAsyncProperty(String name, EntityId id, Consumer<BaseEntity> consumer) {
        Optional<BaseEntity> entity = __childEntities.stream().map(e -> e._2).filter(e -> e.getId().equals(id)).findFirst();
        if (entity.isPresent())
            consumer.accept(entity.get());
        else
             __consumers.computeIfAbsent(id, k -> new LinkedList<>()).add(new Tuple2<>(name, consumer));
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
