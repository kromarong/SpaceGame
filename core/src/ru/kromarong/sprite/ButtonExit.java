package ru.kromarong.sprite;

import ru.kromarong.base.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.ScaledTouchUpButton;
import ru.kromarong.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("button_exit"));
        setHeightProportion(0.12f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight());
        setRight(getHalfWidth());
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }
}
