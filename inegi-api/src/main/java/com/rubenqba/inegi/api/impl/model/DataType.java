package com.rubenqba.inegi.api.impl.model;

/**
 * ResultType summary here...
 *
 * @author rbresler
 **/
public enum DataType {
    RECENT(true),
    HISTORICAL(false);

    private final boolean recent;

    DataType(boolean recent) {
        this.recent = recent;
    }

    public boolean isRecent() {
        return recent;
    }
}
