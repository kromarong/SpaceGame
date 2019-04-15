package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;

public class MainObject extends Sprite {
    private Vector2 speed = new Vector2(0, 0);
    private Vector2 buf;
    private Vector2 touchPoint;

    public MainObject(TextureRegion region) {
        super(region);
        buf = new Vector2();
        touchPoint = new Vector2();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        buf.set(touchPoint);
        if (buf.sub(pos).len() > 0.003f) {
            pos.add(speed);
        } else {
            pos.set(touchPoint);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(worldBounds.getHeight() * 0.15f);
        pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        speed.set(touch.cpy().sub(pos).setLength(0.003f));
        touchPoint.set(touch.cpy());
        return super.touchDown(touch, pointer);
    }
}
