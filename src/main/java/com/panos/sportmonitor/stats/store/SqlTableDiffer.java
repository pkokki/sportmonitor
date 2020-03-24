package com.panos.sportmonitor.stats.store;

import com.google.common.collect.Lists;
import com.panos.sportmonitor.stats.StatsConsole;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class SqlTableDiffer extends SqlStructureListener {
    public SqlTableDiffer(boolean suppressFKs) {
        super(suppressFKs);
    }

    @Override
    protected void onComplete(List<TableInfo> tables) {
        for (TableInfo tableInfo : tables) {
            try (
                    Connection connection = SqlUtils.DATA_SOURCE.getConnection();
                    Statement statement = connection.createStatement()
            ) {
                String tableName = tableInfo.name;
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet metaTable = metaData.getTables(null, null, tableName, null);
                String sqlStatement;
                if (metaTable.next()) {
                    TableMetadata tableMetadata = new TableMetadata();
                    String catalog = metaTable.getString("TABLE_CAT");
                    String schema = metaTable.getString("TABLE_SCHEM");
                    ResultSet metaPrimaryKeys = metaData.getPrimaryKeys(catalog, schema, tableName);
                    while (metaPrimaryKeys.next()) {
                        String primaryKey = metaPrimaryKeys.getString("COLUMN_NAME");
                        tableMetadata.addPrimaryKey(primaryKey);
                    }
                    ResultSet metaColumns = metaData.getColumns(catalog, schema, tableName, null);
                    while (metaColumns.next()) {
                        String columnName = metaColumns.getString("COLUMN_NAME");
                        String columnType = metaColumns.getString("TYPE_NAME");
                        tableMetadata.addColumn(columnName, columnType);
                    }
                    sqlStatement = diffTable(tableInfo, tableMetadata);
                }
                else {
                    sqlStatement = createTable(tableInfo);
                }
                if (sqlStatement.length() > 0) {
                    statement.execute(sqlStatement);
                    StatsConsole.printlnState(sqlStatement);
                }
            }
            catch (SQLException e) {
                StatsConsole.printlnError("Diff " + tableInfo + ": ERROR");
                while (e != null) {
                    StatsConsole.printlnError("   " + e.getMessage());
                    e = e.getNextException();
                }
            }
        }
    }

    private String createTable(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        appendTable(sb, tableInfo);
        return sb.toString();
    }

    private String diffTable(TableInfo tableInfo, TableMetadata tableMetadata) {
        StringBuilder sb = new StringBuilder();
        for (FieldInfo field : tableInfo.getFields()) {
            String fieldName = field.name;
            String fieldType = field.getSqlType();
            if (tableMetadata.columns.containsKey(fieldName)) {
                if (!sameType(fieldType, tableMetadata.columns.get(fieldName))) {
                    sb.append("ALTER TABLE ")
                            .append(tableInfo.name)
                            .append(" ALTER COLUMN ")
                            .append(fieldName)
                            .append(" TYPE ")
                            .append(fieldType)
                            .append(";");
                }
            }
            else {
                sb.append("ALTER TABLE ")
                        .append(tableInfo.name)
                        .append(" ADD COLUMN ")
                        .append(fieldName)
                        .append(" ")
                        .append(fieldType)
                        .append(";");
            }
            if (field.isPK && !tableMetadata.primaryKeys.contains(field.name)) {
                // add PK
                throw new IllegalStateException(tableInfo.name + ": Missing primary key: " + field.name);
            }
        }
        return sb.toString();
    }

    private static List<String> SYNONYMS = Lists.newArrayList("int#int4", "bigint#int8", "boolean#bool", "real#float4");
    private boolean sameType(String type, String sqlType) {
        if (type.equals(sqlType)) return true;
        return SYNONYMS.contains(type + "#" + sqlType);
    }

    private static class TableMetadata {
        private final List<String> primaryKeys;
        private final HashMap<String, String> columns;

        public TableMetadata() {
            this.primaryKeys = new LinkedList<>();
            this.columns = new LinkedHashMap<>();
        }

        public void addPrimaryKey(String primaryKey) {
            this.primaryKeys.add(primaryKey);
        }

        public void addColumn(String columnName, String columnType) {
            this.columns.put(columnName, columnType);
        }
    }
}
