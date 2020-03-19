package com.panos.sportmonitor.stats.store;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class SqlUtils {
    static final String FIELD_ID = "id";
    static final String FIELD_TIMESTAMP = "time_stamp";
    static final String FIELD_REL_SOURCE_PREFIX = "src_";
    static final String FIELD_REL_TARGET_PREFIX = "dst_";
    static final String RELATION_SEPARATOR = "__";

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
