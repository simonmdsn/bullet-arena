package com.simonmdsn.bulletarena.common.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.simonmdsn.bulletarena.common.entity.component.BulletComponent;

public class PlayerBulletComponent extends BulletComponent {
    public PlayerBulletComponent(float travelSpeed, Vector2 direction, float maxTravelDistance, Entity owner, ParticleEffect onHitEffect) {
        super(travelSpeed, direction, maxTravelDistance, owner, onHitEffect);
    }
}
