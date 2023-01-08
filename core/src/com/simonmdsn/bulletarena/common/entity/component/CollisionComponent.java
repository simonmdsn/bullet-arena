package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;

import java.util.Set;

public record CollisionComponent(
        Set<CollisionTag> collisionTags) implements Component {

    public Set<CollisionTag> getCollisionTags() {
        return collisionTags;
    }
}
