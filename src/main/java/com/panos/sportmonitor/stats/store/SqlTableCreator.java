package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.BaseEntity;
import com.panos.sportmonitor.stats.EntityId;
import com.panos.sportmonitor.stats.StatsConsole;
import org.apache.commons.lang3.builder.Diff;

import java.util.*;
import java.util.stream.Collectors;

public class SqlTableCreator extends StatsStoreListener {
    private final Map<String, List<FieldInfo>> sqlData;

    public SqlTableCreator() {
        this.sqlData = new TreeMap<>();
    }

    @Override
    public void onCreate(BaseEntity entity) {
        List<FieldInfo> sqlFields = getTableFields(getTableName(entity));
        if (sqlFields.isEmpty()) {
            sqlFields.add(new FieldInfo(SqlUtils.FIELD_ID, "Long", 0, true));
            if (entity.getId().isComposite())
                sqlFields.add(new FieldInfo(SqlUtils.FIELD_TIMESTAMP, "Long", 0, true));
        }
    }

    @Override
    public void onUpdate(BaseEntity existing, BaseEntity submitted, List<Diff<?>> changes) {
    }

    @Override
    public void onPropertyChange(BaseEntity entity, String entityFieldName, Object oldValue, Object newValue) {
        createOrUpdateFieldInfo(entity, entityFieldName, newValue);
    }

    @Override
    public void onRelationChanged(BaseEntity entity, String entityFieldName, EntityId oldValue, EntityId newValue) {
        createOrUpdateFieldInfo(entity, entityFieldName, newValue.getId());
        if (newValue.isComposite()) {
            String tsPrefix = entityFieldName;
            if (entityFieldName.endsWith("Id"))
                tsPrefix = entityFieldName.substring(0, entityFieldName.length() - 2);
            createOrUpdateFieldInfo(entity, tsPrefix + "_" + SqlUtils.FIELD_TIMESTAMP, newValue.getTimeStamp());
        }
    }

    @Override
    public void onRelationAdded(BaseEntity entity, String entityFieldName, EntityId id) {
        String tableName = getTableName(entity);
        String relTableName = String.format("%s%s%s", tableName, SqlUtils.RELATION_SEPARATOR, SqlUtils.transformTableName(entityFieldName));
        List<FieldInfo> sqlFields = getTableFields(relTableName);
        if (sqlFields.isEmpty()) {
            sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_SOURCE_PREFIX + SqlUtils.FIELD_ID, "Long", 0, true));
            if (entity.getId().isComposite())
                sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_SOURCE_PREFIX + SqlUtils.FIELD_TIMESTAMP, "Long", 0, true));
            sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_TARGET_PREFIX + SqlUtils.FIELD_ID, "Long", 0, true));
            if (id.isComposite())
                sqlFields.add(new FieldInfo(SqlUtils.FIELD_REL_TARGET_PREFIX + SqlUtils.FIELD_TIMESTAMP, "Long", 0, true));
        }
    }

    @Override
    public void submitChanges() {
        StringBuilder sb = new StringBuilder();
        sqlData.forEach((tableName, fields) -> {
            sb.append("DROP TABLE IF EXISTS ").append(tableName).append(";").append("\n");
            sb.append("CREATE TABLE ").append(tableName).append(" (").append("\n");
            fields.forEach(fieldInfo -> sb.append("\t").append(fieldInfo.name).append(" ").append(resolveType(fieldInfo)).append(",\n"));
            sb.append("\tPRIMARY KEY (").append(fields.stream().filter(e -> e.isPK).map(e -> e.name).collect(Collectors.joining(", "))).append(")");
            sb.append(");\n");
        });
        StatsConsole.printlnState(sb.toString());
    }

    private String getTableName(BaseEntity entity) {
        return SqlUtils.transformTableName(entity.getClass().getSimpleName());
    }

    private List<FieldInfo> getTableFields(String tableName) {
        return sqlData.computeIfAbsent(tableName, k -> new LinkedList<>());
    }

    private void createOrUpdateFieldInfo(BaseEntity entity, String entityFieldName, Object value) {
        String tableName = getTableName(entity);
        List<FieldInfo> sqlFields = getTableFields(tableName);
        String sqlFieldName = SqlUtils.transform(entityFieldName);
        Optional<FieldInfo> sqlField = sqlFields.stream().filter(e -> e.name.equals(sqlFieldName)).findFirst();
        FieldInfo fieldInfo;
        if (sqlField.isPresent()) {
            fieldInfo = sqlField.get();
            fieldInfo.size = Math.max(fieldInfo.size, calcSize(value));
        }
        else {
            if (sqlFieldName.startsWith(SqlUtils.FIELD_REL_SOURCE_PREFIX) || sqlFieldName.startsWith(SqlUtils.FIELD_REL_TARGET_PREFIX))
                throw new IllegalArgumentException("Invalid field name: " + sqlFieldName);
            fieldInfo = new FieldInfo(sqlFieldName, value.getClass().getSimpleName(), calcSize(value), false);
            sqlFields.add(fieldInfo);
        }
    }

    private static class FieldInfo {
        public FieldInfo(String name, String type, int size, boolean isPK) {
            this.name = name;
            this.type = type;
            this.size = size;
            this.isPK = isPK;
        }
        public final String name;
        public final String type;
        public final boolean isPK;
        public int size;
    }

    private int calcSize(Object value) {
        if (value instanceof String) {
            return value.toString().length();
        }
        return 0;
    }

    private String resolveType(FieldInfo fieldInfo) {
        StringBuilder sb = new StringBuilder();
        switch (fieldInfo.type) {
            case "Integer": sb.append("int"); break;
            case "Long":
            case "long":
                sb.append("bigint"); break;
            case "Double": sb.append("real"); break;
            case "Boolean": sb.append("boolean"); break;
            case "String": sb.append("varchar(").append((int)Math.pow(2, Math.ceil(Math.log(fieldInfo.size)/Math.log(2)))).append(")"); break;
            default: throw new IllegalArgumentException(String.format("SqlBuilderListener.resolveType: unknown field type %s", fieldInfo.type));
        }
        if (fieldInfo.isPK)
            sb.append(" NOT NULL");
        return sb.toString();
    }
}
