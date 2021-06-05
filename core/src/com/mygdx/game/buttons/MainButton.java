package com.mygdx.game.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class MainButton extends Actor {
    private Sprite sprite;

    public MainButton(TextureRegion texture, float x, float y, float width, float height) {
        sprite = new Sprite(texture);
        sprite.setBounds(x, y, width, height);
        this.setPosition(x, y);
        this.setSize(width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.draw(batch);
    }
}
