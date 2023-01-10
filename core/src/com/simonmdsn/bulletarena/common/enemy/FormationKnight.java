package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.*;
import com.badlogic.gdx.ai.btree.branch.RandomSelector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.ai.btree.decorator.AlwaysFail;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.player.Player;

import java.time.Instant;

public class FormationKnight extends Enemy {

    private BehaviorTree<Enemy> btree;

    public FormationKnight(Assets assets) {
        super(new Sprite(assets.knight()), 1000, assets, 600);
        entity().add(new FormationComponent(FormationNumberGenerator.getInstance().generate()));
        this.shooterComponent.delay(10000);
        this.shooterComponent.texture(assets.defaultBullet());
    }

    @Override
    public void act(Entity entity, Assets assets, Player player, World world, Engine engine, Stage stage, float delta) {
        if (btree == null) {
            btree = new BehaviorTree<>(createKnightBehavior(player, world, assets, stage, engine), this);
        }
        long time = System.nanoTime();
        btree.step();
        System.out.println("Time: " + (System.nanoTime() - time));
    }

    @Override
    public boolean remove() {
        FormationNumberGenerator.getInstance().remove(entity().getComponent(FormationComponent.class).formationNumber());
        return super.remove();
    }

    private Task<Enemy> createKnightBehavior(Player player, World world, Assets assets, Stage stage, Engine engine) {
        Sequence<Enemy> sequence = new Sequence<>();
        Attack attackTask = new Attack(1000, player, shooterComponent(), entity(), assets, stage, world, engine, 150, 400);
        FormationTask formationTask = new FormationTask(entity().getComponent(FormationComponent.class).formationNumber(), player, world);
        RandomSelector<Enemy> randomSelector = new RandomSelector<>();
        randomSelector.addChild(formationTask);
        randomSelector.addChild(new AlwaysFail<>(new LeafTask<Enemy>() {
            @Override
            public Status execute() {
                return Status.FAILED;
            }

            @Override
            protected Task<Enemy> copyTo(Task<Enemy> task) {
                return null;
            }
        }));
        AttackAllDirections attackAllDirections = new AttackAllDirections(1000, shooterComponent(), entity(), assets, stage, world, engine, 100, 400);
        sequence.addChild(randomSelector);
        sequence.addChild(attackAllDirections);
        sequence.addChild(attackTask);
        return sequence;
    }
}
