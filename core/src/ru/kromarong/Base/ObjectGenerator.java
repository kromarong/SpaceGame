package ru.kromarong.base;

import ru.kromarong.math.Rect;
import ru.kromarong.math.Rnd;
import ru.kromarong.pool.HealthpackPool;
import ru.kromarong.sprite.Healthpack;

public class ObjectGenerator {

    private Rect worldBounds;

    private float generateInterval = 16f;
    private float generateTimer;

    private HealthpackPool healthpackPool;

    public ObjectGenerator(HealthpackPool healthpackPool, Rect worldBounds) {
        this.healthpackPool = healthpackPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Healthpack healthpack = healthpackPool.obtain();

            healthpack.pos.x = Rnd.nextFloat(worldBounds.getLeft() + healthpack.getHalfWidth(),
                    worldBounds.getRight() - healthpack.getHalfWidth());
            healthpack.setBottom(worldBounds.getTop());
        }
    }
}
