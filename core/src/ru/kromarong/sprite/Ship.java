package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;

public class Ship extends Sprite{

    private Vector2 speed = new Vector2(0, 0);
    private Vector2 buf;
    private Vector2 bufV;
    private Vector2 touch;


    public Ship(TextureRegion region) {
        super(region);
        setHeightProportion(0.2f);
        buf = new Vector2();
        bufV = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(worldBounds.getHeight() * 0.15f);
        setBottom(worldBounds.getBottom() + 0.07f);
        setLeft(0);
        this.pos.set(getLeft(),getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        speed.set(touch.cpy().sub(pos)).setLength(0.5f);
        buf.set(touch.cpy());
        return super.touchDown(touch, pointer);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        buf.set(touch);
        bufV.set(speed);
        bufV.scl(delta);
        if (buf.sub(pos).len() < bufV.len()) {
            pos.set(touch);
        } else {
            pos.mulAdd(speed, delta);
        }
    }
}
