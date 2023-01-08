package com.simonmdsn.bulletarena.common.player;

import com.badlogic.ashley.core.Component;
import com.simonmdsn.bulletarena.Speed;
import com.simonmdsn.bulletarena.common.Level;
import com.simonmdsn.bulletarena.common.StatDefinition;
import com.simonmdsn.bulletarena.common.Damage;

public class PlayerStatsComponent implements Component {
    private final Damage damage = new Damage(new StatDefinition(25, 100));
    private final Speed speed = new Speed(new StatDefinition(5, 10));
    private final Level level = new Level(this);
    private final Player player;

    public PlayerStatsComponent(Player player) {
        this.player = player;
    }

    public Damage damage() {
        return damage;
    }

    public Speed speed() {
        return speed;
    }

    public Level level() {
        return level;
    }

    public Player player() {
        return player;
    }
}
