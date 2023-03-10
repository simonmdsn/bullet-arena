package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.ai.btree.decorator.AlwaysSucceed;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.player.Player;

public class SlimeEnemy extends Enemy {

    private BehaviorTree<Enemy> btree;

    public SlimeEnemy(Assets assets) {
        super(new Sprite(assets.slimeMonster()), 1000, assets, 100);
        this.shooterComponent.delay(3000);
        this.shooterComponent.texture(assets.slimeBullet());
    }


    @Override
    public void act(Entity entity, Assets assets, Player player, World world, Engine engine, Stage stage, float delta) {
        if (btree == null) {
            btree = new BehaviorTree<>(createSlimeBehavior(player, world, assets, stage, engine), this);
        }
        btree.step();
    }

    private Task<Enemy> createSlimeBehavior(Player player, World world, Assets assets, Stage stage, Engine engine) {
        Sequence<Enemy> sequence = new Sequence<>();
        MoveTowardsPlayer moveTowardsPlayerTask = new MoveTowardsPlayer(player, world);
        AlwaysSucceed<Enemy> enemyAlwaysSucceed = new AlwaysSucceed<>(moveTowardsPlayerTask);
        sequence.addChild(enemyAlwaysSucceed);
        Attack attackTask = new Attack(1000, player, shooterComponent(), entity(), assets, stage, world, engine, 150, 1500);
        sequence.addChild(attackTask);
        return sequence;
    }


}
