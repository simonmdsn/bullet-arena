package com.simonmdsn.bulletarena;

import com.badlogic.gdx.math.Vector2;

public class Helpers {

    public static Vector2 moveTowards(Vector2 from, Vector2 to, float distance) {
        Vector2 direction = to.cpy().sub(from).nor();
        return from.cpy().add(direction.scl(distance));
    }
}
