package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    private final int maxHealth;
    private int health;
    private boolean hit = false;

    public HealthComponent(int health) {
        this.maxHealth = health;
        this.health = health;
    }

    public int health() {
        return health;
    }

    public void health(int health) {
        this.health = health;
    }

    public boolean hit() {
        return hit;
    }

    public void hit(boolean hit) {
        this.hit = hit;
    }

    public int maxHealth() {
        return maxHealth;
    }
}
