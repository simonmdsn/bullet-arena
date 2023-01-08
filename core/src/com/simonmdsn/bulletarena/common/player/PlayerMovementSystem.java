package com.simonmdsn.bulletarena.common.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.CollidableTerrain;
import com.simonmdsn.bulletarena.common.Pair;
import com.simonmdsn.bulletarena.common.StatModifier;
import com.simonmdsn.bulletarena.common.StatType;
import com.simonmdsn.bulletarena.common.enemy.EnemyBulletComponent;
import com.simonmdsn.bulletarena.common.entity.BulletEntity;
import com.simonmdsn.bulletarena.common.entity.component.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerMovementSystem extends IteratingSystem {

    private final ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);
    private final ComponentMapper<ItemEntityComponent> iem = ComponentMapper.getFor(ItemEntityComponent.class);
    private final ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    private final ComponentMapper<PlayerStatsComponent> psc = ComponentMapper.getFor(PlayerStatsComponent.class);


    private final World<Actor> world;
    private final Engine engine;
    private final Camera camera;
    private Stage stage;
    private Assets assets;
    private final int delay = 100;
    private long lastShot = Long.MIN_VALUE;

    public PlayerMovementSystem(World<Actor> world, Engine engine, Camera camera, Stage stage, Assets assets) {
        super(Family.one(PlayerComponent.class).get());
        this.world = world;
        this.engine = engine;
        this.camera = camera;
        this.stage = stage;
        this.assets = assets;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        RenderComponent renderComponent = rm.get(entity);
        ItemEntityComponent itemEntityComponent = iem.get(entity);
        PlayerStatsComponent playerStatsComponent = psc.get(entity);
        com.badlogic.gdx.scenes.scene2d.Actor sprite = renderComponent.actor();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Response.Result move = world.move(itemEntityComponent.getEntityItem(), sprite.getX() - 1, sprite.getY(),
                                              getCollisionFilter());
            if (move.projectedCollisions.isEmpty()) {
                sprite.setX(sprite.getX() - playerStatsComponent.speed().value());
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            Response.Result move = world.move(itemEntityComponent.getEntityItem(), sprite.getX(), sprite.getY() + playerStatsComponent.speed().value(),
                                              getCollisionFilter());
            if (move.projectedCollisions.types.contains(Response.slide) || move.projectedCollisions.isEmpty()) {
                sprite.setY(move.goalY);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Response.Result move = world.move(itemEntityComponent.getEntityItem(), sprite.getX() + playerStatsComponent.speed().value(), sprite.getY(),
                                              getCollisionFilter());
            if (move.projectedCollisions.isEmpty()) {
                sprite.setX(sprite.getX() + playerStatsComponent.speed().value());
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            Response.Result move = world.move(itemEntityComponent.getEntityItem(), sprite.getX(), sprite.getY() - playerStatsComponent.speed().value(),
                                              getCollisionFilter());
            if (move.projectedCollisions.types.contains(Response.slide) || move.projectedCollisions.isEmpty()) {
                sprite.setY(move.goalY);
            }
        }

        long currentTimeMillis = System.currentTimeMillis();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && currentTimeMillis > lastShot + delay) {
            lastShot = currentTimeMillis;
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            Vector3 unproject = camera.unproject(new Vector3(x, y, 0));
            Vector2 dir =
                    new Vector2(unproject.x - sprite.getX(), unproject.y - sprite.getY()).nor();
            Sprite bulletSprite = new Sprite(new Texture("base-bullet.png"));
            BulletEntity bulletEntity = new BulletEntity(bulletSprite, new PlayerBulletComponent(300,dir, 1000, entity, assets.get(
                    "square-splash.party", ParticleEffect.class)));
            bulletEntity.sprite().setOriginCenter();
            bulletEntity.sprite().setRotation(dir.angleDeg() - 90);
//            bulletEntity.setRotation(dir.angleDeg() - 90);
            bulletEntity.setPosition(sprite.getX(),
                                     sprite.getY());

            engine.addEntity(bulletEntity.entity());
            stage.addActor(bulletEntity);
            world.add(bulletEntity.getItemEntityComponent().getEntityItem(), bulletEntity.sprite().getX(),
                      bulletEntity.sprite().getY(),
                      bulletEntity.sprite().getWidth() * bulletEntity.sprite().getScaleX(),
                      bulletEntity.sprite().getHeight() * bulletEntity.sprite().getScaleY());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            playerStatsComponent.speed().addStatModifier(new StatModifier(1, null, StatType.FLAT));
        }
//        List<Entity> collect = new ArrayList<>();
//        engine.getEntitiesFor(Family.all(EnemyBulletComponent.class).get()).forEach(collect::add);
//
//        List<Vector2> closeBullets = collect.stream().filter(entity1 -> {
//            Actor actor = entity1.getComponent(RenderComponent.class).actor();
//            return Vector2.dst(actor.getX(), actor.getY(), sprite.getX(), sprite.getY()) < 1000;
//        }).map(entity1 -> {
//            Actor actor = entity1.getComponent(RenderComponent.class).actor();
//            return entity1.getComponent(EnemyBulletComponent.class).direction();
//        }).toList();
//
//        closeBullets.stream().reduce(Vector2::add).ifPresent(vector2 -> {
//            System.out.println(vector2);
//            Response.Result move = world.move(itemEntityComponent.getEntityItem(),
//                                              sprite.getX() + vector2.x * v * playerStatsComponent.speed().value(),
//                                              sprite.getY() + vector2.y* v * playerStatsComponent.speed().value(),
//                                              getCollisionFilter());
//            if (move.projectedCollisions.types.contains(Response.slide) || move.projectedCollisions.isEmpty()) {
//                sprite.setX(move.goalX);
//                sprite.setY(move.goalY);
//            }
//        });

    }

    private CollisionFilter getCollisionFilter() {
        return (item, other) -> {
            if (other.userData instanceof CollidableTerrain terrain) {
                return Response.slide;
            }
            return null;
        };
    }
}
