package com.simonmdsn.bulletarena.common;

public enum StatType {
    FLAT(100),
    PERCENT_FLAT(200),
    PERCENT_MULTIPLICATIVE(300);

    private final int order;

    StatType(int order) {
        this.order = order;
    }

    public int order() {
        return order;
    }
}
