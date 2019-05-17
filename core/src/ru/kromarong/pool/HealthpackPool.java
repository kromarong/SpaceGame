package ru.kromarong.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.SpritesPool;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.Healthpack;

public class HealthpackPool extends SpritesPool<Healthpack> {

    private TextureAtlas atlas;
    private Rect worldBounds;

    public HealthpackPool(TextureAtlas atlas, Rect worldBounds) {
    this.atlas = atlas;
    this.worldBounds = worldBounds;
    }

    @Override
    protected Healthpack newObject() {
        return new Healthpack(atlas, worldBounds);
    }
}
