package com.simonmdsn.bulletarena.common;

public class StatDefinition {

    private float baseValue;
    private float capValue;

    public StatDefinition(float baseValue, float capValue) {
        this.baseValue = baseValue;
        this.capValue = capValue;
    }

    public float baseValue() {
        return baseValue;
    }

    public float capValue() {
        return capValue;
    }

    public void baseValue(float baseValue) {
        this.baseValue = baseValue;
    }

    public void capValue(float capValue) {
        this.capValue = capValue;
    }
}
