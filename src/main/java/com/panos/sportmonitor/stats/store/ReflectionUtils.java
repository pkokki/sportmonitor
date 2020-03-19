package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.StatsConsole;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {
    public static List<Field> getAllFields(final List<Field> fields, final Class<?> type) {
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        return fields;
    }

    public static Object getValue(Field entityField, BaseEntity entity) {
        try {
            final boolean accessible = entityField.isAccessible();
            entityField.setAccessible(true);
            try {
                return entityField.get(entity);
            } finally {
                entityField.setAccessible(accessible);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            StatsConsole.printlnError(String.format("ReflectionUtils.getValue: error getting value %s of entity %s -> %s", entityField.getName(), entity.getClass().getSimpleName(), e));
            return null;
        }
    }

    public static void setValue(BaseEntity entity, Field entityField, Object value) {
        try {
            final boolean accessible = entityField.isAccessible();
            entityField.setAccessible(true);
            try {
                entityField.set(entity, value);
            } finally {
                entityField.setAccessible(accessible);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            StatsConsole.printlnError(String.format("ReflectionUtils.getValue: error setting value %s of entity %s -> %s", entityField.getName(), entity.getClass().getSimpleName(), e));
        }
    }
}
