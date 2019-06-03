package ru.kromarong.sprite;

import com.badlogic.gdx.audio.Sound;

import ru.kromarong.base.ObjectGenerator;
import ru.kromarong.math.Rect;
import ru.kromarong.pool.BulletPool;
import ru.kromarong.pool.ExplosionPool;


public class EnemyBoss extends EnemyShip {

    private float moveInterval = 10f;
    private float moveTimer;

    public EnemyBoss(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds, ObjectGenerator objectGenerator) {
        super(bulletPool, explosionPool, shootSound, worldBounds, objectGenerator);
        this.v0.set(0.5f, 0);
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    public void move(float delta) {
        if (!isDestroyed()) {
            moveTimer += delta;
            if (moveTimer >= moveInterval) {
                float typeMove = (float) Math.random();
                if (typeMove <= 0.3f) {
                    moveLeft();
                } else if (typeMove <= 0.6f) {
                    moveRight();
                } else {
                    stop();
                }
            }
        }
    }
}
