package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.simonmdsn.bulletarena.common.entity.component.BulletComponent;

public class EnemyBulletComponent extends BulletComponent {


    public EnemyBulletComponent(float travelSpeed, Vector2 direction, float maxTravelDistance, Entity owner, ParticleEffect onHitEffect) {
        super(travelSpeed, direction, maxTravelDistance, owner, onHitEffect);
    }
}
