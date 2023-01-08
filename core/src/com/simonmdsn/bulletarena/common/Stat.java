package com.simonmdsn.bulletarena.common;

import com.badlogic.gdx.utils.Null;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class Stat {

    private final String name;
    private final String abbreviation;
    private float value;
    private final StatDefinition definition;
    private final List<StatModifier> modifiers = new ArrayList<>();
    private boolean isDirty = true;

    public Stat(String name, String abbreviation, StatDefinition definition) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.definition = definition;
    }

    public Stat(String name, StatDefinition definition) {
        this.name = name;
        this.abbreviation = null;
        this.definition = definition;
    }

    public float value() {
        if (!isDirty) return value;

        value = definition.baseValue();
        float sumPercentAdd = 0;
        for (int i = 0; i < modifiers.size(); i++) {
            StatModifier modifier = modifiers.get(i);
            if (modifier.type() == StatType.FLAT) {
                value += modifier.value();
            } else if (modifier.type() == StatType.PERCENT_FLAT) {
                sumPercentAdd += modifier.value();
                if (i + 1 >= modifiers.size() || modifiers.get(i + 1).type() != StatType.PERCENT_FLAT) {
                    value *= 1 + sumPercentAdd;
                    sumPercentAdd = 0;
                }
            } else if (modifier.type() == StatType.PERCENT_MULTIPLICATIVE) {
                value *= 1 + modifier.value();
            }

        }
        if (value > definition.capValue()) {
            value = definition.capValue();
        }
        isDirty = false;
        return value;
    }

    public void raiseBase(float raise) {
        definition.baseValue(definition.baseValue()+ raise);
        isDirty=true;
    }

    public void addStatModifier(StatModifier statModifier) {
        isDirty = true;
        modifiers.add(statModifier);
        System.out.printf("Added %f %s modifier %s%n", statModifier.value(), name, statModifier.type());
        sortStatModifiers();
    }

    public boolean removeStatModifier(StatModifier statModifier) {
        isDirty = true;
        return modifiers.remove(statModifier);
    }

    public boolean removeAllModifiersFromSource(Object source) {
        boolean removedAny = modifiers.stream().filter(statModifier -> statModifier.source() == source).map(modifiers::remove).toList().size() > 0;
        if (removedAny) isDirty = true;
        return removedAny;
    }

    private void sortStatModifiers() {
        modifiers.sort(Comparator.comparingInt(o -> o.type().order()));
    }

    public String name() {
        return name;
    }

    public String abbreviation() {
        return abbreviation;
    }

    public StatDefinition definition() {
        return definition;
    }
}
