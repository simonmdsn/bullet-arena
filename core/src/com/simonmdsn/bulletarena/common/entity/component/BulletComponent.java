package com.simonmdsn.bulletarena.common.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

public class BulletComponent implements Component {
    private final float travelSpeed;
    private final Vector2 direction;
    private final float maxTravelDistance;
    private final Entity owner;
    private float travelled = 0;
    private boolean collided = false;
    private final ParticleEffect onHitEffect;


    public BulletComponent(float travelSpeed, Vector2 direction, float maxTravelDistance, Entity owner, ParticleEffect onHitEffect) {
        this.travelSpeed = travelSpeed;
        this.direction = direction;
        this.maxTravelDistance = maxTravelDistance;
        this.owner = owner;
        this.onHitEffect = onHitEffect;
    }

    public float travelSpeed() {
        return travelSpeed;
    }

    public Vector2 direction() {
        return direction;
    }

    public float maxTravelDistance() {
        return maxTravelDistance;
    }

    public Entity owner() {
        return owner;
    }

    public float travelled() {
        return travelled;
    }

    public void travelled(float travelled) {
        this.travelled = travelled;
    }

    public boolean collided() {
        return collided;
    }

    public void collided(boolean collided) {
        this.collided = collided;
    }

    public ParticleEffect onHitEffect() {
        return onHitEffect;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BulletComponent) obj;
        return Objects.equals(this.direction, that.direction) &&
                Float.floatToIntBits(this.maxTravelDistance) == Float.floatToIntBits(that.maxTravelDistance) &&
                Objects.equals(this.owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, maxTravelDistance, owner);
    }

    @Override
    public String toString() {
        return "BulletComponent[" +
                "direction=" + direction + ", " +
                "maxTravelDistance=" + maxTravelDistance + ", " +
                "owner=" + owner + ']';
    }


}
