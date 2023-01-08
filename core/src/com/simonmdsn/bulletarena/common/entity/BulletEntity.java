package com.simonmdsn.bulletarena.common.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dongbat.jbump.Item;
import com.simonmdsn.bulletarena.common.entity.component.BulletComponent;
import com.simonmdsn.bulletarena.common.entity.component.ItemEntityComponent;

public  class BulletEntity extends ActorEntity {

    private final BulletComponent bulletComponent;
    private final ItemEntityComponent itemEntityComponent;

    public BulletEntity(Sprite sprite, BulletComponent bulletComponent) {
        super(sprite);
        sprite.setScale(.5f);
        sprite.setOrigin((sprite.getWidth()) * sprite.getScaleX() / 2,
                                                   sprite.getHeight() * sprite.getScaleY() / 2);
        this.bulletComponent = bulletComponent;
        this.itemEntityComponent = new ItemEntityComponent(new Item<>(this));
        entity().add(bulletComponent);
        entity().add(itemEntityComponent);
    }

    public BulletComponent getBulletComponent() {
        return bulletComponent;
    }

    public ItemEntityComponent getItemEntityComponent() {
        return itemEntityComponent;
    }
}
