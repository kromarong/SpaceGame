package ru.kromarong.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.math.Rect;
import ru.kromarong.pool.BulletPool;
import ru.kromarong.sprite.Bullet;

public class Ship extends Sprite {
    protected Sound shootSound;
    protected Rect worldBounds;
    protected Vector2 v;
    protected Vector2 v0;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected int damage;


    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
    }

    public Ship() {
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
    }

    public void shoot() {
        if (!isDestroyed()) {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
            shootSound.play(0.3f);
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
