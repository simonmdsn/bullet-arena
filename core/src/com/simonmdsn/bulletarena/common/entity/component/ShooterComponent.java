package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ShooterComponent implements Component {
    private int delay;
    private Texture texture;
    private long lastShot = Long.MIN_VALUE;

    public ShooterComponent(int delay, Texture texture) {
        this.delay = delay;
        this.texture = texture;
    }

    public int delay() {
        return delay;
    }

    public void delay(int delay) {
        this.delay = delay;
    }

    public void texture(Texture texture) {
        this.texture = texture;
    }

    public long lastShot() {
        return lastShot;
    }

    public void lastShot(long lastShot) {
        this.lastShot = lastShot;
    }

    public Texture texture() {
        return texture;
    }
}
