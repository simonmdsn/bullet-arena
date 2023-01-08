package com.simonmdsn.bulletarena.common.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.common.CollidableTerrain;
import com.simonmdsn.bulletarena.common.entity.ActorEntity;
import com.simonmdsn.bulletarena.common.entity.TextActor;
import com.simonmdsn.bulletarena.common.entity.component.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerBulletSystem extends IteratingSystem {
    ComponentMapper<PlayerBulletComponent> bcm = ComponentMapper.getFor(PlayerBulletComponent.class);
    ComponentMapper<ItemEntityComponent> iem = ComponentMapper.getFor(ItemEntityComponent.class);
    ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);
    ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    ComponentMapper<PlayerStatsComponent> psc = ComponentMapper.getFor(PlayerStatsComponent.class);

    private final World<Actor> world;
    private Stage stage;
    private List<ParticleEffect> particles;
    private final static Set<CollisionTag> collidables = new HashSet<>() {{
        add(CollisionTag.TERRAIN);
        add(CollisionTag.ENEMY);
    }};

    public PlayerBulletSystem(World<Actor> world, Stage stage, List<ParticleEffect> particles) {
        super(Family.all(PlayerBulletComponent.class).get());
        this.world = world;
        this.stage = stage;
        this.particles = particles;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        BulletComponent bulletComponent = bcm.get(entity);
        ItemEntityComponent itemEntityComponent = iem.get(entity);
        Item<Actor> entityItem = itemEntityComponent.getEntityItem();
        Actor sprite = rm.get(entity).actor();


        if (bulletComponent.travelled() >= bulletComponent.maxTravelDistance() || bulletComponent.collided()) {
            bulletComponent.collided(true);
            bulletComponent.onHitEffect().setPosition(sprite.getX(), sprite.getY());
            bulletComponent.onHitEffect().start();
            particles.add(bulletComponent.onHitEffect());
            world.remove(entityItem);
            getEngine().removeEntity(entity);
            sprite.remove();
            return;
        }

        Response.Result move = world.move(entityItem, sprite.getX() + (bulletComponent.direction().x * v * bulletComponent.travelSpeed()),
                                          sprite.getY() + (bulletComponent.direction().y * v * bulletComponent.travelSpeed()),
                                          (item, other) -> {

                    if (other.userData instanceof CollidableTerrain) {
                        bulletComponent.collided(true);
                        return Response.touch;
                    }
                    if (other.userData instanceof ActorEntity entity1) {
                        if (cm.has(entity1.entity())) {
                            CollisionComponent collisionComponent = cm.get(entity1.entity());
                            if (collisionComponent.collisionTags().stream().anyMatch(collidables::contains) && !bulletComponent.collided()) {
                                bulletComponent.collided(true);
                                if (hm.has(entity1.entity())) {
                                    HealthComponent healthComponent = hm.get(entity1.entity());
                                    PlayerStatsComponent playerStatsComponent = psc.get(bulletComponent.owner());
                                    System.out.println("Player hit " + healthComponent.health());
                                    healthComponent.health(healthComponent.health() - (int) playerStatsComponent.damage().value());
                                    entity1.addAction(new TemporalAction(.5f) {
                                        @Override
                                        protected void update(float percent) {
                                            if (percent == 0)
                                                entity1.sprite().setColor(Color.RED);
                                            else if (percent == 1)
                                                entity1.sprite().setColor(Color.WHITE);
                                            else {
                                                float r = Color.RED.a + (Color.WHITE.r - Color.RED.a) * percent;
                                                float g = Color.RED.g + (Color.WHITE.g - Color.RED.g) * percent;
                                                float b = Color.RED.b + (Color.WHITE.b - Color.RED.b) * percent;
                                                float a = Color.RED.a + (Color.WHITE.a - Color.RED.a) * percent;
                                                entity1.sprite().setColor(r, g, b, a);
                                            }
                                        }
                                    });
                                    stage.addActor(new TextActor(String.valueOf(Math.round(playerStatsComponent.damage().value())),
                                                                 new BitmapFont(),
                                                                 3,
                                                                 entity1.getX(),
                                                                 entity1.getY() + entity1.getHeight() + 100));
                                    System.out.println("health is " + healthComponent.health());
                                }

                                return Response.touch;
                            }
                        }
                    }
                    return null;
                });
        if (move.projectedCollisions.isEmpty()) {
            bulletComponent.travelled(bulletComponent.travelled() + Vector2.dst(sprite.getX(), sprite.getY(), move.goalX, move.goalY));
            sprite.setPosition(move.goalX, move.goalY);
        } else {
            bulletComponent.collided(true);
            bulletComponent.onHitEffect().setPosition(sprite.getX(), sprite.getY());
            bulletComponent.onHitEffect().start();
            particles.add(bulletComponent.onHitEffect());

            sprite.remove();
            world.remove(entityItem);
            getEngine().removeEntity(entity);
        }

    }
}
