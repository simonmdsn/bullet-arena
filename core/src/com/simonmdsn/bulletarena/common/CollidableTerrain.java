package com.simonmdsn.bulletarena.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.simonmdsn.bulletarena.common.entity.component.CollisionComponent;
import com.simonmdsn.bulletarena.common.entity.component.CollisionTag;

import java.util.HashSet;

public class CollidableTerrain extends Actor {
    private final Entity entity;
    private final float x,y;

    public CollidableTerrain(float x,float y) {
        super();
        this.x = x;
        this.y = y;
        this.entity = new Entity();
        entity.add(new CollisionComponent(new HashSet<>() {{
            add(CollisionTag.TERRAIN);
        }}));
    }

    public Entity entity() {
        return entity;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }
}
