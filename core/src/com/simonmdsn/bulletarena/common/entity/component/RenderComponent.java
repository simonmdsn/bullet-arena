package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public record RenderComponent(Actor actor) implements Component {

    public Actor actor() {
        return actor;
    }
}
