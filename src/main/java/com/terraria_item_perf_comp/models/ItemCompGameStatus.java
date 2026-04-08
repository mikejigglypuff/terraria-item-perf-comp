package com.terraria_item_perf_comp.models;

public enum ItemCompGameStatus {
    READY("ready"),
    ACTIVE("active"),
    COMPLETED("completed"),
    ABANDONED("abandoned");

    private final String dbValue;

    ItemCompGameStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static ItemCompGameStatus fromDbValue(String value) {
        if (value == null) {
            return null;
        }
        for (ItemCompGameStatus s : values()) {
            if (s.dbValue.equals(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown comp game status: " + value);
    }
}

