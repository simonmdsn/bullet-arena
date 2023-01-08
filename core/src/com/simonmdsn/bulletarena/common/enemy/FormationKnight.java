package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.assets.Assets;
import com.simonmdsn.bulletarena.common.player.Player;

public class FormationKnight extends Enemy {

    private BehaviorTree<Enemy> btree;

    public FormationKnight(Assets assets) {
        super(new Sprite(assets.knight()), 1000, assets, 600);
        entity().add(new FormationComponent(FormationNumberGenerator.getInstance().generate()));
        this.shooterComponent.delay(10000);
        this.shooterComponent.texture(assets.defaultBullet());
    }

    @Override
    public void move(Entity entity, Assets assets, Player player, World world, Engine engine, Stage stage, float delta) {
        if (btree == null) {
            btree = new BehaviorTree<>(createKnightBehavior(player, world, assets, stage, engine), this);
        }
        btree.step();
    }

    @Override
    public boolean remove() {
        FormationNumberGenerator.getInstance().remove(entity().getComponent(FormationComponent.class).formationNumber());
        return super.remove();
    }

    private Task<Enemy> createKnightBehavior(Player player, World world, Assets assets, Stage stage, Engine engine) {
        Selector<Enemy> selector = new Selector<>();
        Sequence<Enemy> sequence = new Sequence<>();
        selector.addChild(sequence);
        AttackTask attackTask = new AttackTask(1000, player, shooterComponent(), entity(), assets, stage, world, engine, 150, 400);
        FormationTask formationTask = new FormationTask(entity().getComponent(FormationComponent.class).formationNumber(), player, world);
        sequence.addChild(formationTask);
        sequence.addChild(attackTask);
        return selector;
    }
}
