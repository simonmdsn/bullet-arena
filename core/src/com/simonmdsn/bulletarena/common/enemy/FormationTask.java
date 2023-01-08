package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.simonmdsn.bulletarena.Helpers;
import com.simonmdsn.bulletarena.common.player.Player;

public class FormationTask extends LeafTask<Enemy> {

    private final int formationNumber;
    private final Player player;
    private final World world;

    public FormationTask(int formationNumber, Player player, World world) {
        this.formationNumber = formationNumber;
        this.player = player;
        this.world = world;
    }

    @Override
    public Status execute() {
        float angle =
                (float) ((float) FormationNumberGenerator.getInstance().indexOf(formationNumber) / (float) FormationNumberGenerator.getInstance().size() * 360 * Math.PI / 180f);
        float goalX = player.getX() + ((float) Math.cos(angle) * 300f);
        float goalY = player.getY() + ((float) Math.sin(angle) * 300f);
        // error margin to avoid some cycles of movement
        if (Math.abs(getObject().getX() - goalX) < 10 && Math.abs(getObject().getY() - goalY) < 10) {
            return Status.SUCCEEDED;
        }
        Vector2 vector2 = Helpers.moveTowards(new Vector2(getObject().getX(), getObject().getY()), new Vector2(goalX, goalY),
                                              getObject().movementSpeed() * Gdx.graphics.getDeltaTime());
        Response.Result move = world.move(getObject().getItemEntityComponent().getEntityItem(), vector2.x, vector2.y, getObject().getCollisionFilter());
        if (move.projectedCollisions.isEmpty()) {
            getObject().setPosition(move.goalX, move.goalY);
            return Status.FAILED;
        }
        return Status.FAILED;
    }

    @Override
    protected Task<Enemy> copyTo(Task<Enemy> task) {
        return null;
    }
}
