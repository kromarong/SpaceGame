package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;

public class ButtonGameOver extends Sprite {
    public ButtonGameOver(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("button_game_over"));
        setHeightProportion(0.1f);
        setBottom(worldBounds.getHalfHeight() + 0.06f);
        setLeft(worldBounds.getLeft() - 0.36f);
    }


}
