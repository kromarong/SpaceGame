package ru.kromarong.pool;

import ru.kromarong.base.SpritesPool;
import ru.kromarong.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
