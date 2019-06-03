package ru.kromarong.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.ObjectGenerator;
import ru.kromarong.base.Ship;
import ru.kromarong.math.Rect;
import ru.kromarong.pool.BulletPool;
import ru.kromarong.pool.ExplosionPool;

public class EnemyShip extends Ship {
    private enum State {DESCENT, FIGHT}
    private State state;
    private Vector2 descentV;

    private boolean horizontalMove = false;
    private float moveTimer;
    private float moveInterval = 1f;

    public enum Type {SMALL, MEDIUM, BIG, BOSS}
    public Type type;

    private ObjectGenerator objectGenerator;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds, ObjectGenerator objectGenerator) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.objectGenerator = objectGenerator;
        this.descentV = new Vector2(0, -0.3f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() <= worldBounds.getTop() && state == state.DESCENT) {
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
        if (horizontalMove == true){
            moveTimer += delta;
            if (getRight() > worldBounds.getRight()) {
                setRight(worldBounds.getRight());
                stop();
            }
            if (getLeft() < worldBounds.getLeft()) {
                setLeft(worldBounds.getLeft());
                stop();
            }
            if (moveTimer >= moveInterval){
                float direction = (float) Math.random();
                if (direction <= 0.3f){
                    moveLeft();
                } else if (direction <= 0.6f){
                    moveRight();
                } else {
                    stop();
                }
                moveTimer = 0f;
            }

        }
    }

    @Override
    public void destroy() {
        super.destroy();
        objectGenerator.generate(this);
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
            int hp,
            Type type
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
        this.type = type;
        v.set(descentV);
        reloadTimer = reloadInterval;
        state = State.DESCENT;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    public void startMoveHorizontal(){
        horizontalMove = true;
    }

    private void moveRight() {
        v.set(0.2f, v.y);
    }

    private void moveLeft() {
        v.set(-0.2f, v.y);
    }

    private void stop() {
        v.set(0, v.y);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
