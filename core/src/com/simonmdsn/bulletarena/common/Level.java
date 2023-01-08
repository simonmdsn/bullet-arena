package com.simonmdsn.bulletarena.common;

import com.simonmdsn.bulletarena.common.player.LevelUpEvent;
import com.simonmdsn.bulletarena.common.player.PlayerStatsComponent;

public class Level extends Stat {

    private int experience = 0;
    private final int experienceToLevel = 100;
    private final PlayerStatsComponent playerStatsComponent;

    public Level(PlayerStatsComponent playerStatsComponent) {
        super("Level", "LVL", new StatDefinition(1, 99));
        this.playerStatsComponent = playerStatsComponent;
    }

    public int experience() {
        return experience;
    }

    public void gainExperience(int exp) {
        if (experience + exp >= experienceToLevel) {
            playerStatsComponent.speed().raiseBase(1);
            playerStatsComponent.damage().raiseBase(1);
            raiseBase(1);
            playerStatsComponent.player().fire(new LevelUpEvent((int) playerStatsComponent.level().definition().baseValue()));
            experience = 0;
            return;
        }
        experience += exp;
    }

    public int experienceToLevel() {
        return experienceToLevel;
    }
}
