package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;
import ru.kromarong.math.Rnd;

public class Healthpack extends Sprite {
    private Vector2 v;
    private Rect worldBounds;

    public Healthpack(TextureRegion region, Rect worldBounds) {
        super(region);
        this.worldBounds = worldBounds;
        float vx = Rnd.nextFloat(-0.001f, 0.001f);
        float vy = Rnd.nextFloat(-0.08f, -0.081f);
        v = new Vector2(vx, vy);
        setHeightProportion(0.05f);
    }

    public Healthpack() {

    }


    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public boolean isShipCollision(Rect mainship) {
        return !(mainship.getRight() < getLeft()
                || mainship.getLeft() > getRight()
                || mainship.getBottom() > pos.y
                || mainship.getTop() < getBottom()
        );
    }
}
