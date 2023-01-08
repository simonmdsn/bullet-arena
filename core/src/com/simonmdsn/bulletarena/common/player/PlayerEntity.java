//package com.simonmdsn.bulletarena.common.player;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.dongbat.jbump.Item;
//import com.simonmdsn.bulletarena.assets.Assets;
//import com.simonmdsn.bulletarena.common.entity.RenderableEntity;
//import com.simonmdsn.bulletarena.common.entity.component.CollisionTag;
//import com.simonmdsn.bulletarena.common.entity.component.CollisionComponent;
//import com.simonmdsn.bulletarena.common.entity.component.HealthComponent;
//import com.simonmdsn.bulletarena.common.entity.component.ItemEntityComponent;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class PlayerEntity extends RenderableEntity {
//
//    private final PlayerComponent playerComponent;
//    private final ItemEntityComponent entityItemComponent;
//    private final CollisionComponent collisionComponent;
//    private final HealthComponent healthComponent;
//    private static final Set<CollisionTag> collidables = new HashSet<>() {
//        {
//            add(CollisionTag.PLAYER);
//        }
//    };
//
//    public PlayerEntity(Assets assets) {
//        super(new Sprite(assets.superHero()));
//        this.collisionComponent = new CollisionComponent(collidables);
//        this.healthComponent = new HealthComponent(100);
//        add(collisionComponent);
//        add(healthComponent);
//        add(new PlayerStatsComponent());
//        System.out.println("player width=" + getRenderComponent().getSprite().getWidth() + " height=" + getRenderComponent().getSprite().getHeight());
//        getRenderComponent().getSprite().setScale(0.5f);
//        getRenderComponent().getSprite().setOriginCenter();
//        System.out.println(getRenderComponent().getSprite().getWidth() * getRenderComponent().getSprite().getScaleX());
//        System.out.println(getRenderComponent().getSprite().getHeight());
//        this.playerComponent = new PlayerComponent();
//        add(playerComponent());
////        this.entityItemComponent = new ItemEntityComponent(new Item<>(this));
////        add(entityItemComponent);
//    }
//
//    public PlayerComponent playerComponent() {
//        return playerComponent;
//    }
//
//    public ItemEntityComponent entityItemComponent() {
//        return entityItemComponent;
//    }
//
//    public CollisionComponent collisionComponent() {
//        return collisionComponent;
//    }
//
//    public HealthComponent healthComponent() {
//        return healthComponent;
//    }
//}
