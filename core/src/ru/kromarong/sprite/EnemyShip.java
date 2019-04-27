package ru.kromarong.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.Ship;
import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;
import ru.kromarong.pool.BulletPool;

public class EnemyShip extends Ship {
    private enum State {DESCENT, FIGHT}
    private State state;
    private Vector2 descentV;

    private MainShip mainShip;

    public EnemyShip(BulletPool bulletPool, Sound shootSound, Rect worldBounds, MainShip mainShip) {
        this.mainShip = mainShip;
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.descentV = new Vector2(0, -0.3f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() <= worldBounds.getTop()) {
            state = State.FIGHT;
            v.set(v0);
        }
        if (state == State.FIGHT) {
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                shoot();
            }
        }
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        v.set(descentV);
        reloadTimer = reloadInterval;
        state = State.DESCENT;
    }
}
