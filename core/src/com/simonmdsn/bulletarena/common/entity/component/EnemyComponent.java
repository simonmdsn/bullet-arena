package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;
import com.simonmdsn.bulletarena.common.enemy.Enemy;

public class EnemyComponent implements Component {

    private final Enemy enemy;

    public EnemyComponent(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy enemy() {
        return enemy;
    }
}
