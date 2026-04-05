package com.terraria_item_perf_comp.models;

public enum ItemBalanceGameStatus {
    READY("ready"),
    ACTIVE("active"),
    COMPLETED("completed"),
    ABANDONED("abandoned");

    private final String dbValue;

    ItemBalanceGameStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static ItemBalanceGameStatus fromDbValue(String value) {
        if (value == null) {
            return null;
        }
        for (ItemBalanceGameStatus s : values()) {
            if (s.dbValue.equals(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown balance game status: " + value);
    }
}
