package ru.kromarong.base;

import ru.kromarong.math.Rect;
import ru.kromarong.math.Rnd;
import ru.kromarong.pool.HealthpackPool;
import ru.kromarong.pool.ShieldCasePool;
import ru.kromarong.sprite.EnemyShip;
import ru.kromarong.sprite.Healthpack;
import ru.kromarong.sprite.ShieldCase;

public class ObjectGenerator {

    private Rect worldBounds;

    private float generateInterval = 16f;
    private float generateTimer;

    private HealthpackPool healthpackPool;
    private ShieldCasePool shieldCasePool;

    public ObjectGenerator(HealthpackPool healthpackPool, ShieldCasePool shieldCasePool, Rect worldBounds) {
        this.healthpackPool = healthpackPool;
        this.shieldCasePool = shieldCasePool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            float type = (float) Math.random();
            if (type <= 0.02f) {
                Healthpack healthpack = healthpackPool.obtain();

                healthpack.pos.x = Rnd.nextFloat(worldBounds.getLeft() + healthpack.getHalfWidth(),
                        worldBounds.getRight() - healthpack.getHalfWidth());
                healthpack.setBottom(worldBounds.getTop());
            } else if (type <= 0.03f){
                ShieldCase shieldCase = shieldCasePool.obtain();

                shieldCase.pos.x = Rnd.nextFloat(worldBounds.getLeft() + shieldCase.getHalfWidth(),
                        worldBounds.getRight() - shieldCase.getHalfWidth());
                shieldCase.setBottom(worldBounds.getTop());
            }
        }
    }

    public void generate(EnemyShip enemyShip) {
        float type;
        switch (enemyShip.getType()){
            case SMALL:
                type = (float) Math.random() * 100;
                break;
            case MEDIUM:
                type = (float) Math.random() * 30;
                break;
            case BIG:
                type = (float) Math.random() * 10;
                break;
            default:
                type = (float) Math.random();
        }

        if (type <= 5f) {
            Healthpack healthpack = healthpackPool.obtain();

            healthpack.pos.x = enemyShip.pos.x;
            healthpack.pos.y = enemyShip.pos.y;

        } else if (type <= 8f){
            ShieldCase shieldCase = shieldCasePool.obtain();

            shieldCase.pos.x = enemyShip.pos.x;
            shieldCase.pos.y = enemyShip.pos.y;
        }
    }
}

