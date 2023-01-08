package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dongbat.jbump.Item;
import com.simonmdsn.bulletarena.common.entity.ActorEntity;

public class ItemEntityComponent implements Component {
    private final Item<Actor> entityItem;

    public ItemEntityComponent(Item<Actor> entityItem) {
        this.entityItem = entityItem;
    }

    public Item<Actor> getEntityItem() {
        return entityItem;
    }
}
