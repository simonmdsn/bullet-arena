package com.simonmdsn.bulletarena.common.player;

import com.badlogic.gdx.scenes.scene2d.Event;

public class LevelUpEvent extends Event {
    public final int level;

    public LevelUpEvent(int level) {
        this.level = level;
    }
}
