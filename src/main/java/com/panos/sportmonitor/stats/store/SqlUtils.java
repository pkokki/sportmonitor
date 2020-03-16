package com.panos.sportmonitor.stats.store;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class SqlUtils {
    public static List<Field> getAllFields(final List<Field> fields, final Class<?> type) {
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        return fields;
    }
    public static String transformTableName(String str) {
        return transform(str.replace("Entity", ""));
    }
    public static String transform(String str) {
        return str.replaceAll("(\\w)([A-Z])", "$1_$2").toLowerCase();
    }
    public static String prepareSql(Object src) {
        if (src == null)
            return "null";
        if (src instanceof  String)
            return "'" + src.toString().replace("'", "''") + "'";
        return src.toString();
    }
}
