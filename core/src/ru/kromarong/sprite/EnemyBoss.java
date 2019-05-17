package ru.kromarong.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.ObjectGenerator;
import ru.kromarong.math.Rect;
import ru.kromarong.pool.BulletPool;
import ru.kromarong.pool.ExplosionPool;
import ru.kromarong.utils.Regions;

public class EnemyBoss extends EnemyShip {

    public EnemyBoss(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds, ObjectGenerator objectGenerator) {
        super(bulletPool, explosionPool, shootSound, worldBounds, objectGenerator);
    }
}
