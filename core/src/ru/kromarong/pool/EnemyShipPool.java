package ru.kromarong.pool;

import ru.kromarong.base.SpritesPool;
import ru.kromarong.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }
}
