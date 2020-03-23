package com.panos.sportmonitor.stats.store;

public final class SqlUtils {
    static final String FIELD_REL_SOURCE_PREFIX = "src_";
    static final String FIELD_REL_TARGET_PREFIX = "dst_";
    static final String RELATION_SEPARATOR = "__";

    public static String transformTableName(String str) {
        return transform(str.replace("Entity", ""));
    }
    public static String transform(String str) {
        return str.replaceAll("(\\w)([A-Z])", "$1_$2").toLowerCase();
    }

    public static String resolveSqlFieldName(String entityFieldName, String keyName) {
        if (entityFieldName.equals("id"))
            return SqlUtils.transform(keyName);
        if (entityFieldName.endsWith("Id"))
            entityFieldName = entityFieldName.substring(0, entityFieldName.length() - 2);
        return SqlUtils.transform(entityFieldName + "_" + keyName);
    }
}
