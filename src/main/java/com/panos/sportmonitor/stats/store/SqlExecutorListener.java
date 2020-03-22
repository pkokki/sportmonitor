package com.panos.sportmonitor.stats.store;

public interface SqlExecutorListener {
    void onSqlExecutorCompleted(int total, int succeeded, int failed);
}
