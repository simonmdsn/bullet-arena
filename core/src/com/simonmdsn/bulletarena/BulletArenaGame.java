package com.simonmdsn.bulletarena;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.CollidableTerrain;
import com.simonmdsn.bulletarena.common.enemy.Enemy;
import com.simonmdsn.bulletarena.common.enemy.EnemyBulletSystem;
import com.simonmdsn.bulletarena.common.enemy.EnemyMovementSystem;
import com.simonmdsn.bulletarena.common.enemy.EnemySpawner;
import com.simonmdsn.bulletarena.common.entity.ActorEntity;
import com.simonmdsn.bulletarena.common.entity.HealthSystem;
import com.simonmdsn.bulletarena.common.entity.TextActor;
import com.simonmdsn.bulletarena.common.entity.component.EnemyComponent;
import com.simonmdsn.bulletarena.common.player.Player;
import com.simonmdsn.bulletarena.common.player.PlayerBulletSystem;
import com.simonmdsn.bulletarena.common.player.PlayerHud;
import com.simonmdsn.bulletarena.common.player.PlayerMovementSystem;

import java.util.ArrayList;
import java.util.List;

public class BulletArenaGame extends ApplicationAdapter {
    SpriteBatch batch;
    Player player;
    World<Actor> world;
    Engine engine = new Engine();
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Assets assets;
    Stage stage;
    BitmapFont bitmapFont;
    TiledMap tiledMap;
    PlayerHud playerHud;
    Stage staticStage;

    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private final List<ParticleEffect> particleEffectList = new ArrayList<>();


    @Override
    public void create() {
        assets = new Assets();
        batch = new SpriteBatch();
        world = new World<>(8f);
        world.setTileMode(false);
        camera = new OrthographicCamera();
        FillViewport fillViewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(fillViewport, batch);
        player = new Player(camera, new Sprite(assets.superHero()));

        staticStage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), batch);
        playerHud = new PlayerHud(staticStage, player);
        shapeRenderer = new ShapeRenderer();
        engine.addEntity(player.entity());
        world.add(player.entityItemComponent().getEntityItem(),
                  player.sprite().getX(),
                  player.sprite().getY(),
                  player.sprite().getWidth() * player.sprite().getScaleX(),
                  player.sprite().getHeight() * player.sprite().getScaleY());
        engine.addSystem(new PlayerMovementSystem(world, engine, camera, stage, assets));
        engine.addSystem(new EnemyMovementSystem(world, player, stage, assets));
        engine.addSystem(new PlayerBulletSystem(world, stage, particleEffectList));
        engine.addSystem(new HealthSystem(world, player));
        engine.addSystem(new EnemyBulletSystem(world, stage));
        engine.addSystem(new EnemySpawner(world, assets, stage));
        stage.addActor(player);
        tiledMap = assets.get("bigbig-arena.tmx", TiledMap.class);
        bitmapFont = new BitmapFont();
//        TextActor textActor = new TextActor("hello world!", bitmapFont, 1, 100, 100);
//        stage.addActor(textActor);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 4, batch);
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        for (int i = 0; i < tiledMapTileLayer.getWidth(); i++) {
            for (int j = 0; j < tiledMapTileLayer.getHeight(); j++) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(i, j);

                if (cell.getTile().getProperties().containsKey("collidable")
                        && cell.getTile().getProperties().get("collidable", Boolean.class)) {
                    world.add(new Item<>(new CollidableTerrain(i * 16 * 4, j * 16 * 4)), i * 16 * 4, j * 16 * 4, 16 * 4,
                              16 * 4);
                }
            }
        }
    }

    @Override
    public void render() {

        ScreenUtils.clear(0, 0, .5f, .5f);
        batch.setProjectionMatrix(camera.combined);

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render(new int[1]);
        stage.draw();
        orthogonalTiledMapRenderer.render(new int[0]);

        for (Item item : world.getItems()) {
            if (item.userData instanceof ActorEntity actorEntity) {

                Sprite sprite = actorEntity.sprite();

                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.setAutoShapeType(true);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.rect(sprite.getX(),
                                   sprite.getY(),
                                   sprite.getOriginX(),
                                   sprite.getOriginY(),
                                   sprite.getWidth(),
                                   sprite.getHeight(),
                                   sprite.getScaleX(),
                                   sprite.getScaleY(),
                                   sprite.getRotation());
                shapeRenderer.end();
            }
            if (item.userData instanceof CollidableTerrain collidableTerrain) {
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.setAutoShapeType(true);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.rect(collidableTerrain.x(),
                                   collidableTerrain.y(),
                                   collidableTerrain.getOriginX(),
                                   collidableTerrain.getOriginY(),
                                   16,
                                   16,
                                   4,
                                   4,
                                   0);
                shapeRenderer.end();
            }
        }
        batch.begin();
        for (ParticleEffect particleEffect : particleEffectList) {
            particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
        }
        // paints the enemy-far-away indicator arrows
        for (Entity entity : engine.getEntitiesFor(Family.all(EnemyComponent.class).get())) {
            Enemy enemy = entity.getComponent(EnemyComponent.class).enemy();
            if (Vector2.dst(enemy.getX(), enemy.getY(), player.getX(), player.getY()) >= 1000) {
                Vector2 scl = new Vector2(player.getX() - enemy.getX(), player.getY() - enemy.getY()).nor();
                Sprite sprite = new Sprite(assets.arrow());
                sprite.setScale(0.2f);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setRotation(scl.angleDeg());
                sprite.setPosition(player.getX() + (scl.x *  -400),
                                   player.getY() + (scl.y *  -300));
                sprite.draw(batch);
            }
        }

        batch.end();

        ArrayList<ParticleEffect> toBeRemoved = new ArrayList<>();
        for (int i = 0, particleEffectListSize = particleEffectList.size(); i < particleEffectListSize; i++) {
            ParticleEffect particleEffect = particleEffectList.get(i);
            if (particleEffect.isComplete()) {
                toBeRemoved.add(particleEffect);
            }
        }
        particleEffectList.removeAll(toBeRemoved);

        stage.act(Gdx.graphics.getDeltaTime());
        staticStage.act(Gdx.graphics.getDeltaTime());
        staticStage.draw();
        engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.assetManager().dispose();
    }
}
