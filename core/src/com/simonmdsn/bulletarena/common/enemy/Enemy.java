package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.CollidableTerrain;
import com.simonmdsn.bulletarena.common.entity.ActorEntity;
import com.simonmdsn.bulletarena.common.entity.BulletEntity;
import com.simonmdsn.bulletarena.common.entity.component.*;
import com.simonmdsn.bulletarena.common.player.Player;

import java.util.HashSet;
import java.util.Set;

public class Enemy extends ActorEntity {
    private final EnemyComponent enemyComponent;
    private final ItemEntityComponent itemEntityComponent;
    private final HealthComponent healthComponent;
    final ShooterComponent shooterComponent;
    private final Assets assets;
    private final float movementSpeed;
    ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);
    ComponentMapper<ItemEntityComponent> iem = ComponentMapper.getFor(ItemEntityComponent.class);
    ComponentMapper<ShooterComponent> sm = ComponentMapper.getFor(ShooterComponent.class);

    private static final Set<CollisionTag> collidables = new HashSet<>()
    {
        {
            add(CollisionTag.ENEMY);
        }
    };

    public Enemy(Sprite sprite, int maxHealth, Assets assets, float movementSpeed) {
        super(sprite);
        this.assets = assets;
        this.movementSpeed = movementSpeed;
        sprite.setOrigin(sprite().getWidth() / 2,
                  sprite().getHeight() / 2);
        setPosition(500,100);
        entity().add(new CollisionComponent(collidables));
        this.healthComponent = new HealthComponent(maxHealth);
        this.shooterComponent = new ShooterComponent(1000,assets.defaultBullet());
        entity().add(healthComponent);
        entity().add(shooterComponent);
        this.enemyComponent = new EnemyComponent(this);
        this.itemEntityComponent = new ItemEntityComponent(new Item<>(this));
        entity().add(enemyComponent);
        entity().add(itemEntityComponent);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Pixmap pixmap = new Pixmap(100, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.drawRectangle(0, 0, 100, 10);
        pixmap.fillRectangle(0, 0, (int) (100 * ((float) healthComponent.health() / (float) healthComponent.maxHealth())), 10);
        Texture texture = new Texture(pixmap);
        batch.draw(texture, getX(), getY() - 10);
        assets.bitmapFont().draw(batch, String.valueOf(healthComponent.health()), getX(), getY() - 10, 100, Align.center, false);
    }


    public EnemyComponent enemyComponent() {
        return enemyComponent;
    }

    public ItemEntityComponent itemEntityComponent() {
        return itemEntityComponent;
    }

    public HealthComponent healthComponent() {
        return healthComponent;
    }

    public ShooterComponent shooterComponent() {
        return shooterComponent;
    }

    public EnemyComponent getEnemyComponent() {
        return enemyComponent;
    }

    public ItemEntityComponent getItemEntityComponent() {
        return itemEntityComponent;
    }

    public void move(Entity entity, Assets assets, Player player, World world, Engine engine, Stage stage, float delta) {
        RenderComponent enemyRenderComponent = rm.get(entity);
        RenderComponent playerRenderComponent = rm.get(player.entity());
        ItemEntityComponent itemEntityComponent = iem.get(entity);
        Vector2 enemyVector2 = new Vector2(enemyRenderComponent.actor().getX(), enemyRenderComponent.actor().getY());
        Vector2 playerVector2 = new Vector2(playerRenderComponent.actor().getX(), playerRenderComponent.actor().getY());
        Vector2 dir = new Vector2(playerVector2.x - enemyVector2.x, playerVector2.y - enemyVector2.y).nor();
        if (enemyVector2.dst(playerVector2) < 100) return;
        Response.Result move = world.move(itemEntityComponent.getEntityItem(), enemyVector2.x + (dir.x * delta * 200),
                                          enemyVector2.y + (dir.y * delta * 200), getCollisionFilter());
        if (move.projectedCollisions.isEmpty()) {
            enemyRenderComponent.actor().setPosition(move.goalX, move.goalY);
        }

        ShooterComponent shooterComponent = sm.get(entity);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis> shooterComponent.lastShot()+ shooterComponent.delay()) {
            shooterComponent.lastShot(currentTimeMillis);
            BulletEntity bulletEntity = new BulletEntity(new Sprite(shooterComponent.texture()), new EnemyBulletComponent(300,dir, 1000,
                                                                                                                          entity,
                                                                                          assets.get("square-splash.party",
                                                                                                     ParticleEffect.class)));
            bulletEntity.sprite().setRotation(dir.angleDeg() - 90);
            bulletEntity.setOrigin(Align.center);
            bulletEntity.setPosition(enemyRenderComponent.actor().getX(),
                                     enemyRenderComponent.actor().getY());
            bulletEntity.sprite().setScale(.2f);

            stage.addActor(bulletEntity);
            engine.addEntity(bulletEntity.entity());
            world.add(bulletEntity.getItemEntityComponent().getEntityItem(), bulletEntity.sprite().getX(),
                      bulletEntity.sprite().getY(),
                      bulletEntity.sprite().getWidth() * bulletEntity.sprite().getScaleX(),
                      bulletEntity.sprite().getHeight()* bulletEntity.sprite().getScaleY());
        }
    }

    public CollisionFilter getCollisionFilter() {
        return (item, other) -> {
            if (other.userData instanceof CollidableTerrain terrain) {
                return Response.slide;
            }
            return null;
        };
    }

    public float movementSpeed() {
        return this.movementSpeed;
    }
}
