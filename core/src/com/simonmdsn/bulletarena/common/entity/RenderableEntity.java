package com.simonmdsn.bulletarena.common.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.common.entity.component.RenderComponent;

public class RenderableEntity extends Entity {

    private final RenderComponent renderComponent;
    public RenderableEntity(Sprite sprite) {
        this.renderComponent = new RenderComponent(null);
        add(renderComponent);
    }

    public static RenderableEntity center(Sprite sprite) {
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        return new RenderableEntity(sprite);
    }

    public void render(World<Entity> world, SpriteBatch spriteBatch) {
//        renderComponent.getSprite().draw(spriteBatch);
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }
}
