package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.entity.component.EnemyComponent;
import com.simonmdsn.bulletarena.common.entity.component.RenderComponent;
import com.simonmdsn.bulletarena.common.player.PlayerComponent;

import java.util.concurrent.ThreadLocalRandom;

public class EnemySpawner extends IntervalSystem {

    private final World<Actor> world;
    private Assets assets;
    private Stage stage;
    private final ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);

    public EnemySpawner(World<Actor> world, Assets assets, Stage stage) {
        super(2f);
        this.world = world;
        this.assets = assets;
        this.stage = stage;
    }

    @Override
    protected void updateInterval() {
        if (getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get()).size() >= 16) return;
        ImmutableArray<Entity> entitiesFor = getEngine().getEntitiesFor(Family.one(PlayerComponent.class).get());
        if(entitiesFor.size() <= 0) return;
        Entity player = entitiesFor.first();
//        Enemy enemy = ThreadLocalRandom.current().nextBoolean() ? new Enemy(new Sprite(ThreadLocalRandom.current().nextInt(0, 2) > 0 ?
//                                                                               assets.redMonster1() :
//                                                           assets.redMonster2()), 100, assets, 200) : new SlimeEnemy(assets);
        Enemy enemy = new FormationKnight(assets);
        RenderComponent playerRC = rm.get(player);
        Vector2 vec = randomSpawnPoint(playerRC.actor().getX(), playerRC.actor().getY());
        enemy.setPosition(vec.x,vec.y);
        System.out.println("Enemy spawned at " + vec.x + " " + vec.y);
        getEngine().addEntity(enemy.entity());
        stage.addActor(enemy);
        world.add(enemy.getItemEntityComponent().getEntityItem(), enemy.sprite().getX(),
                  enemy.sprite().getY(),
                  enemy.sprite().getWidth() * enemy.sprite().getScaleX(),
                  enemy.sprite().getHeight() * enemy.sprite().getScaleY());
    }

    private Vector2 randomSpawnPoint(float playerX, float playerY) {
        double angle = Math.random() * Math.PI * 2;
        float x = (float) Math.cos(angle)*Gdx.graphics.getWidth() + playerX;
        float y = (float) Math.sin(angle)*Gdx.graphics.getHeight() + playerY;
        return new Vector2(x, y);
    }
}
