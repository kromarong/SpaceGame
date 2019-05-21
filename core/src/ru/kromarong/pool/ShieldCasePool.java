package ru.kromarong.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.SpritesPool;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.ShieldCase;

public class ShieldCasePool extends SpritesPool<ShieldCase> {

    private TextureAtlas atlas;
    private Rect worldBounds;

    public ShieldCasePool(TextureAtlas atlas, Rect worldBounds) {
        this.atlas = atlas;
        this.worldBounds = worldBounds;
    }

    @Override
    protected ShieldCase newObject() {
        return new ShieldCase(atlas, worldBounds);
    }
}
