package com.panos.sportmonitor.stats;

public interface StatsParserListener {
    String INVALID_ROOT_NODE = "INVALID_ROOT_NODE";
    String UNHANDLED_PROPERTY = "UNHANDLED_PROPERTY";
    String UNHANDLED_CHILD_ENTITY = "UNHANDLED_CHILD_ENTITY";
    String UNHANDLED_AUX_ID = "UNHANDLED_AUX_ID";
    String UNKNOWN_ROOT_TYPE = "UNKNOWN_ROOT_TYPE";
    String UNKNOWN_ENTITY_TYPE = "UNKNOWN_ENTITY_TYPE";

    void onParserError(String code, String message);
}
