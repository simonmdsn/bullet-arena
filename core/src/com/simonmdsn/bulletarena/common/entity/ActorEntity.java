package com.simonmdsn.bulletarena.common.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.simonmdsn.bulletarena.common.entity.component.RenderComponent;


public class ActorEntity extends Actor {
    private final Entity entity = new Entity();
    private final Sprite sprite;


    public ActorEntity(Sprite sprite) {
        this.sprite = sprite;
        this.setOrigin(Align.center);
        entity.add(new RenderComponent(this));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);

    }

    public Entity entity() {
        return entity;
    }

    public Sprite sprite() {
        return sprite;
    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(),getY());
    }

    @Override
    public void setColor(Color color) {
        sprite.setColor(color);
        super.setColor(color);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        sprite.setColor(r,g,b,a);
        super.setColor(r, g, b, a);
    }

    @Override
    public Color getColor() {
        return sprite.getColor();
    }

}
