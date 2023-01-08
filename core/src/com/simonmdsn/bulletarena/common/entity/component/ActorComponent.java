package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorComponent implements Component {

    private final Actor actor;

    public ActorComponent(Actor actor) {
        this.actor = actor;
    }

    public Actor actor() {
        return actor;
    }
}
