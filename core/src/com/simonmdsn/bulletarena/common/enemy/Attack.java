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
import com.simonmdsn.bulletarena.common.player.Player;


public class Attack extends LeafTask<Enemy> {

    private final float range;
    private final Player player;
    private final ShooterComponent shooterComponent;
    private final Entity entity;
    private final Assets assets;
    private final Stage stage;
    private final World world;
    private final Engine engine;
    private float bulletTravelSpeed;
    private float maxTravelDistance;

    public Attack(float range, Player player, ShooterComponent shooterComponent, Entity entity, Assets assets, Stage stage,
                  World world, Engine engine, float bulletTravelSpeed, float maxTravelDistance) {
        this.range = range;
        this.player = player;
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
        if(Vector2.dst(player.getX(),player.getY(),getObject().getX(),getObject().getY()) > range) {
            return Status.FAILED;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > shooterComponent.lastShot() + shooterComponent.delay()) {
            shooterComponent.lastShot(currentTimeMillis);
            Vector2 dir = new Vector2(player.getX() - getObject().getX(), player.getY() - getObject().getY()).nor();
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

            stage.addActor(bulletEntity);
            engine.addEntity(bulletEntity.entity());
            world.add(bulletEntity.getItemEntityComponent().getEntityItem(), bulletEntity.sprite().getX(),
                      bulletEntity.sprite().getY(),
                      bulletEntity.sprite().getWidth() * bulletEntity.sprite().getScaleX(),
                      bulletEntity.sprite().getHeight() * bulletEntity.sprite().getScaleY());
            return Status.SUCCEEDED;
        }
        return Status.FAILED;
    }

    @Override
    protected Task<Enemy> copyTo(Task<Enemy> task) {
        return null;
    }
}
