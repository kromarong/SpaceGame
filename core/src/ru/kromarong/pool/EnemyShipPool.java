package ru.kromarong.pool;

import com.badlogic.gdx.audio.Sound;

import ru.kromarong.base.ObjectGenerator;
import ru.kromarong.base.SpritesPool;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    private Rect worldBounds;
    private BulletPool bulletPool;
    private Sound shootSound;
    private ExplosionPool explosionPool;
    private ObjectGenerator objectGenerator;

    public EnemyShipPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound,
                         Rect worldBounds, ObjectGenerator objectGenerator) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.objectGenerator = objectGenerator;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, shootSound, worldBounds, objectGenerator);
    }

}
