package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;

import java.util.Objects;
import java.util.Set;

public final class CollisionComponent implements Component {
    private final Set<CollisionTag> collisionTags;

    public CollisionComponent(
            Set<CollisionTag> collisionTags) {
        this.collisionTags = collisionTags;
    }

    public Set<CollisionTag> getCollisionTags() {
        return collisionTags;
    }

    public Set<CollisionTag> collisionTags() {
        return collisionTags;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CollisionComponent) obj;
        return Objects.equals(this.collisionTags, that.collisionTags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collisionTags);
    }

    @Override
    public String toString() {
        return "CollisionComponent[" +
                "collisionTags=" + collisionTags + ']';
    }

}
