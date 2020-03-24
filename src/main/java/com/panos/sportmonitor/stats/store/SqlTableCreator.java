package com.panos.sportmonitor.stats.store;

import com.panos.sportmonitor.stats.*;

import java.util.*;

public class SqlTableCreator extends SqlStructureListener {
    private final boolean suppressFKs;

    public SqlTableCreator(boolean suppressFKs) {
        this.suppressFKs = suppressFKs;
    }

    @Override
    protected void onComplete(List<TableInfo> tables) {
        StringBuilder sb = new StringBuilder();
        for (TableInfo table : tables)
            appendTable(sb, table, suppressFKs);
        StatsConsole.printlnState(sb.toString());
    }
}
