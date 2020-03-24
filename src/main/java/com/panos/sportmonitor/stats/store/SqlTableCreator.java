package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.*;

import java.util.*;

public class SqlTableCreator extends SqlStructureListener {

    public SqlTableCreator(boolean suppressFKs) {
        super(suppressFKs);
    }

    @Override
    protected void onComplete(List<TableInfo> tables) {
        StringBuilder sb = new StringBuilder();
        for (TableInfo table : tables)
            appendTable(sb, table);
        StatsConsole.printlnState(sb.toString());
    }
}
