package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.entity.BulletEntity;
import com.simonmdsn.bulletarena.common.entity.component.ShooterComponent;

import java.util.List;

public class AttackAllDirections extends LeafTask<Enemy> {

    private final long delay;
    private final ShooterComponent shooterComponent;
    private final Entity entity;
    private final Assets assets;
    private final Stage stage;
    private final World world;
    private final Engine engine;
    private float bulletTravelSpeed;
    private float maxTravelDistance;

    private long lastShot = 0;

    public AttackAllDirections(long delay, ShooterComponent shooterComponent, Entity entity, Assets assets, Stage stage, World world, Engine engine, float bulletTravelSpeed, float maxTravelDistance) {
        this.delay = delay;
        this.shooterComponent = shooterComponent;
        this.entity = entity;
        this.assets = assets;
        this.stage = stage;
        this.world = world;
        this.engine = engine;
        this.bulletTravelSpeed = bulletTravelSpeed;
        this.maxTravelDistance = maxTravelDistance;
    }

    @Override
    public Status execute() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > lastShot + delay) {
            lastShot = currentTimeMillis;
            List.of(new Vector2(100 - getObject().getX(), 100 - getObject().getY()).nor(),
                    new Vector2(100 + getObject().getX(), 100 - getObject().getY()).nor(),
                    new Vector2(100 - getObject().getX(), 100 + getObject().getY()).nor(),
                    new Vector2(100 + getObject().getX(), 100 + getObject().getY()).nor()).forEach(dir -> {


            BulletEntity bulletEntity = new BulletEntity(new Sprite(shooterComponent.texture()),
                    new EnemyBulletComponent(bulletTravelSpeed,dir, maxTravelDistance,
                            entity,
                            assets.get("square-splash.party",
                                    ParticleEffect.class)));
            bulletEntity.sprite().setRotation(dir.angleDeg() - 90);
            bulletEntity.setOrigin(Align.center);
            bulletEntity.setPosition(getObject().getX(),
                    getObject().getY());
            bulletEntity.sprite().setScale(.2f);
                System.out.println("Spawned");
            stage.addActor(bulletEntity);
            engine.addEntity(bulletEntity.entity());
            world.add(bulletEntity.getItemEntityComponent().getEntityItem(), bulletEntity.sprite().getX(),
                    bulletEntity.sprite().getY(),
                    bulletEntity.sprite().getWidth() * bulletEntity.sprite().getScaleX(),
                    bulletEntity.sprite().getHeight() * bulletEntity.sprite().getScaleY());
            });
        }
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Enemy> copyTo(Task<Enemy> task) {
        return null;
    }
}
