package com.simonmdsn.bulletarena.common.player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dongbat.jbump.Item;
import com.simonmdsn.bulletarena.common.entity.ActorEntity;
import com.simonmdsn.bulletarena.common.entity.component.*;

import java.util.HashSet;
import java.util.Set;

public class Player extends ActorEntity {
    private final Camera camera;

    private final PlayerComponent playerComponent;
    private final ItemEntityComponent entityItemComponent;
    private final CollisionComponent collisionComponent;
    private final HealthComponent healthComponent;
    private final PlayerStatsComponent statsComponent;
    private static final Set<CollisionTag> collidables = new HashSet<>() {
        {
            add(CollisionTag.PLAYER);
        }
    };


    public Player(Camera camera, Sprite sprite) {
        super(sprite);
        this.camera = camera;
        this.collisionComponent = new CollisionComponent(collidables);
        this.healthComponent = new HealthComponent(100);
        this.statsComponent = new PlayerStatsComponent(this);
        setPosition(6000, 6000);
        entity().add(collisionComponent);
        entity().add(healthComponent);
        entity().add(statsComponent);
        System.out.println("player width=" + sprite.getWidth() + " height=" + sprite.getHeight());
        sprite.setScale(0.5f);
        sprite.setOriginCenter();
        System.out.println(sprite.getWidth() * sprite.getScaleX());
        System.out.println(sprite.getHeight());
        this.playerComponent = new PlayerComponent();
        entity().add(playerComponent());
        this.entityItemComponent = new ItemEntityComponent(new Item<>(this));
        entity().add(entityItemComponent);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Pixmap healthShadow = new Pixmap(50, 10, Pixmap.Format.RGBA8888);
        healthShadow.setColor(Color.BLACK);
        healthShadow.drawRectangle(0, 0, 50, 10);
        Texture texture1 = new Texture(healthShadow);
        Pixmap health = new Pixmap(50, 10, Pixmap.Format.RGBA8888);
        float healthFraction = ((float) healthComponent.health()) / (float) healthComponent.maxHealth();
        if (healthFraction < 0.25) {
            health.setColor(Color.RED);
        } else {
            health.setColor(Color.GREEN);
        }
        health.fillRectangle(0, 0, (int) (healthFraction * 50), 10);
        Texture texture = new Texture(health);
        batch.draw(texture, sprite().getX() + 20, getY());
        batch.draw(texture1, sprite().getX() + 20, getY());
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        camera.position.set(getX(), getY(), 0);
        camera.update();
    }


    public PlayerComponent playerComponent() {
        return playerComponent;
    }

    public ItemEntityComponent entityItemComponent() {
        return entityItemComponent;
    }

    public CollisionComponent collisionComponent() {
        return collisionComponent;
    }

    public HealthComponent healthComponent() {
        return healthComponent;
    }

    public PlayerStatsComponent statsComponent() {
        return statsComponent;
    }
}
