package com.simonmdsn.bulletarena.common.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.simonmdsn.bulletarena.common.entity.component.CollisionComponent;

import java.util.HashSet;
import java.util.Set;

public class TextActor extends Actor {

    private String text;
    private BitmapFont bitmapFont;
    private float x;
    private float y;
    private float alpha = 1;

    public TextActor(String text, BitmapFont bitmapFont, float fontSize, float x, float y) {
        this.text = text;
        this.bitmapFont = bitmapFont;
        this.x = x;
        this.y = y;
        bitmapFont.getData().setScale(fontSize);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        bitmapFont.setColor(255,255,255,alpha);
        alpha -= .01;
        if(alpha <= 0) {
            remove();
        }
        bitmapFont.draw(batch, text, x, y);
    }
}
