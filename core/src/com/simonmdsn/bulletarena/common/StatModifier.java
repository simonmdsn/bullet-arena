package com.simonmdsn.bulletarena.common;

public class StatModifier {

    private float value;
    private final Object source;
    private StatType type;

    public StatModifier(float value, Object source, StatType type) {
        this.value = value;
        this.source = source;
        this.type = type;
    }

    public float value() {
        return value;
    }

    public StatType type() {
        return type;
    }

    public Object source() {
        return source;
    }
}
