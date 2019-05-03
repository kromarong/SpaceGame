package ru.kromarong.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.kromarong.base.SpritesPool;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.Healthpack;

public class HealthpackPool extends SpritesPool<Healthpack> {

    private TextureRegion region;
    private Rect worldBounds;

    public HealthpackPool(TextureRegion region, Rect worldBounds) {
    this.region = region;
    this.worldBounds = worldBounds;
    }

    @Override
    protected Healthpack newObject() {
        return new Healthpack(region, worldBounds);
    }
}
