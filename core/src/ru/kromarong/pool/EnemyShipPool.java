package ru.kromarong.pool;

import com.badlogic.gdx.audio.Sound;

import ru.kromarong.base.SpritesPool;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.EnemyShip;
import ru.kromarong.sprite.MainShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    private Rect worldBounds;
    private BulletPool bulletPool;
    private Sound shootSound;
    private MainShip mainShip;
    private ExplosionPool explosionPool;

    public EnemyShipPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound,
                         Rect worldBounds, MainShip mainShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, shootSound, worldBounds, mainShip);
    }
}
