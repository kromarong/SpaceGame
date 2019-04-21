package ru.kromarong.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.math.Rect;
import ru.kromarong.math.Rnd;
import ru.kromarong.pool.EnemyShipPool;
import ru.kromarong.sprite.EnemyShip;
import ru.kromarong.utils.Regions;

public class EnemiesControl {

    private EnemyShipPool enemyPool;
    private TextureAtlas atlas;
    private TextureRegion enemyRegion;
    private TextureRegion[] regions;
    private Rect worldBounds;

    private Vector2 enemyPos;
    private Vector2 enemySpeed = new Vector2(0, -0.1f);

    private float createInterval = 3.0f;
    private float createTimer;

    public EnemiesControl(EnemyShipPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.atlas = atlas;
        this.worldBounds = worldBounds;
        this.enemyRegion = atlas.findRegion("enemy0");
        this.regions = Regions.split(enemyRegion, 1,2,2);
        this.enemyPos = new Vector2();
    }

    public void createEnemy(){
        float x = Rnd.nextFloat(worldBounds.getLeft() + 0.1f, worldBounds.getRight() - 0.1f);
        enemyPos.set(x,worldBounds.getTop() + 0.05f);
        EnemyShip enemyShip = enemyPool.obtain();
        enemyShip.set(regions[0], enemyPos, enemySpeed, 0.2f, worldBounds);

    }

    public void update(float delta){
        createTimer += delta;
        if (createTimer >= createInterval) {
            createTimer = 0f;
            createEnemy();
        }
    }
}
