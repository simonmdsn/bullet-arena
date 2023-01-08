package com.simonmdsn.bulletarena.common.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simonmdsn.bulletarena.common.entity.TextActor;

public class PlayerHud implements Disposable {

    private final Player player;

    private final Stage stage;

    public PlayerHud(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;
        Table table = new Table();
        table.bottom();
        table.right();
        table.pad(0, 0, 20, 20);
        table.setFillParent(true);

        var font = new FreeTypeFontGenerator(Gdx.files.internal("cartoon.ttf"));
        var fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 60;
        BitmapFont font12 = font.generateFont(fontParameter);
        font.dispose();

        Label label = new Label("LvL: " + ((int)player.statsComponent().level().value()), new Label.LabelStyle(font12, Color.WHITE));
        player.addListener(event -> {
            if (event instanceof LevelUpEvent) {
                label.setText("LvL: " + ((LevelUpEvent) event).level);
            }
            return true;
        });
        table.add(label);
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
