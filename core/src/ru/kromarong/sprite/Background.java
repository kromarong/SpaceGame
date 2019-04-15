package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;


public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
