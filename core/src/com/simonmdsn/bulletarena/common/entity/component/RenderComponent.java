package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Objects;

public final class RenderComponent implements Component {
    private final Actor actor;

    public RenderComponent(Actor actor) {
        this.actor = actor;
    }

    public Actor actor() {
        return actor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RenderComponent) obj;
        return Objects.equals(this.actor, that.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor);
    }

    @Override
    public String toString() {
        return "RenderComponent[" +
                "actor=" + actor + ']';
    }

}
