package com.simonmdsn.bulletarena.common.entity;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.common.entity.component.HealthComponent;
import com.simonmdsn.bulletarena.common.entity.component.ItemEntityComponent;
import com.simonmdsn.bulletarena.common.entity.component.RenderComponent;
import com.simonmdsn.bulletarena.common.player.Player;
import com.simonmdsn.bulletarena.common.player.PlayerStatsComponent;

public class HealthSystem extends IteratingSystem {
    private final ComponentMapper<HealthComponent> hc = ComponentMapper.getFor(HealthComponent.class);
    private final ComponentMapper<ItemEntityComponent> iem = ComponentMapper.getFor(ItemEntityComponent.class);
    private final ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);

    private final ComponentMapper<PlayerStatsComponent> pscm = ComponentMapper.getFor(PlayerStatsComponent.class);


    private World<Actor> world;
    private Player player;

    public HealthSystem(World<Actor> world, Player player) {
        super(Family.all(HealthComponent.class).get());
        this.world = world;
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        HealthComponent healthComponent = hc.get(entity);
        if(healthComponent.health() <= 0) {
            PlayerStatsComponent playerStatsComponent = pscm.get(player.entity());
            playerStatsComponent.level().gainExperience(100);
            Item<Actor> entityItem = iem.get(entity).getEntityItem();
            rm.get(entity).actor().remove();
            world.remove(entityItem);
            getEngine().removeEntity(entity);
        }
    }
}
