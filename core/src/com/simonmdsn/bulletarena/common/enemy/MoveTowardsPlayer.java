package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.common.player.Player;

public class MoveTowardsPlayer extends LeafTask<Enemy> {

    private final Player player;
    private final World world;

    public MoveTowardsPlayer(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public Status execute() {
        Vector2 enemyVector2 = new Vector2(getObject().getX(),
                                           getObject().getY());
        Vector2 playerVector2 = new Vector2(player.getX(), player.getY());
        Vector2 direction = playerVector2.sub(enemyVector2).nor();
        if (enemyVector2.dst(playerVector2) < 100) return Status.FAILED;
        Response.Result move = world.move(getObject().getItemEntityComponent().getEntityItem(),
                                          enemyVector2.x + (direction.x * Gdx.graphics.getDeltaTime() * getObject().movementSpeed()),
                                          enemyVector2.y + (direction.y * Gdx.graphics.getDeltaTime() * getObject().movementSpeed()),
                                          getObject().getCollisionFilter());
        if (move.projectedCollisions.isEmpty()) {
            getObject().setPosition(move.goalX, move.goalY);
            return Status.SUCCEEDED;
        }
        return Status.FAILED;
    }

    @Override
    protected Task<Enemy> copyTo(Task<Enemy> task) {
        return null;
    }
}
