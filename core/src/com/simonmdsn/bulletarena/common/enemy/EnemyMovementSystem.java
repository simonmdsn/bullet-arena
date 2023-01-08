package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.CollidableTerrain;
import com.simonmdsn.bulletarena.common.entity.BulletEntity;
import com.simonmdsn.bulletarena.common.entity.component.*;
import com.simonmdsn.bulletarena.common.player.Player;

public class EnemyMovementSystem extends IteratingSystem {
    ComponentMapper<EnemyComponent> em = ComponentMapper.getFor(EnemyComponent.class);


    private final World<Actor> world;
    private final Player player;
    private final Stage stage;
    private final Assets assets;

    public EnemyMovementSystem(World<Actor> world, Player player, Stage stage, Assets assets) {
        super(Family.all(EnemyComponent.class).get());
        this.world = world;
        this.player = player;
        this.stage = stage;
        this.assets = assets;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        em.get(entity).enemy().move(entity, assets, player, world, getEngine(), stage, deltaTime);
    }


}
