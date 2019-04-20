package ru.kromarong.sprite;

import ru.kromarong.base.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.ScaledTouchUpButton;
import ru.kromarong.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.02f);
        setRight(worldBounds.getRight() - 0.02f);
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }
}
