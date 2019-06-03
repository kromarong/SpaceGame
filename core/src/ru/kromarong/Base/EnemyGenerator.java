package ru.kromarong.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.math.Rect;
import ru.kromarong.math.Rnd;
import ru.kromarong.pool.EnemyShipPool;
import ru.kromarong.sprite.EnemyShip;
import ru.kromarong.utils.Regions;

import static ru.kromarong.sprite.EnemyShip.Type.BIG;
import static ru.kromarong.sprite.EnemyShip.Type.BOSS;
import static ru.kromarong.sprite.EnemyShip.Type.MEDIUM;
import static ru.kromarong.sprite.EnemyShip.Type.SMALL;

public class EnemyGenerator {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 2;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 2;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.1f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_DAMAGE = 4;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 4;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 8;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_BIG_HP = 10;

    private static final float ENEMY_BOSS_HEIGHT = 0.2f;
    private static final float ENEMY_BOSS_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BOSS_BULLET_VY = -0.3f;
    private static final int ENEMY_BOSS_DAMAGE = 8;
    private static final float ENEMY_BOSS_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_BOSS_HP = 30;

    private Rect worldBounds;

    private float generateInterval = 4f;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMediumRegion;
    private final TextureRegion[] enemyBigRegion;
    private final TextureRegion[] enemyBossRegion;

    private final Vector2 enemySmallV = new Vector2(0, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0, -0.03f);
    private final Vector2 enemyBigV = new Vector2(0, -0.005f);
    private final Vector2 enemyBossV = new Vector2(0, -0.005f);

    private final TextureRegion bulletRegion;
    private final TextureRegion bulletBossRegion;

    private final EnemyShipPool enemyPool;

    private EnemyShip enemyBoss;

    private int level;
    private int damage_modifier;
    private int hp_modifier;

    public EnemyGenerator(TextureAtlas atlas, EnemyShipPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(enemy0, 1, 2, 2);
        TextureRegion enemy1 = atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(enemy1, 1, 2, 2);
        TextureRegion enemy2 = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(enemy2, 1, 2, 2);
        TextureRegion boss = atlas.findRegion("enemy5");
        this.enemyBossRegion = Regions.split(boss, 1, 2, 2);
        this.bulletBossRegion = atlas.findRegion("rocket");
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemyShip.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE * damage_modifier,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP * hp_modifier,
                        SMALL
                );
            } else if (type < 0.85f) {
                enemyShip.set(
                        enemyMediumRegion,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_DAMAGE * damage_modifier,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP * hp_modifier,
                        MEDIUM
                );
            } else {
                enemyShip.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE * damage_modifier,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP * hp_modifier,
                        BIG
                );
            }
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(),
                    worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }

    public void generateBoss(){
        EnemyShip enemyShip = enemyPool.obtain();

        enemyShip.set(
                enemyBossRegion,
                enemyBossV,
                bulletBossRegion,
                ENEMY_BOSS_BULLET_HEIGHT,
                ENEMY_BOSS_BULLET_VY,
                ENEMY_BOSS_DAMAGE * damage_modifier,
                ENEMY_BOSS_RELOAD_INTERVAL,
                ENEMY_BOSS_HEIGHT,
                ENEMY_BOSS_HP * hp_modifier,
                BIG
        );
        enemyShip.setType(BOSS);
        enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(),
                worldBounds.getRight() - enemyShip.getHalfWidth());
        enemyShip.setBottom(worldBounds.getTop());
        enemyShip.startMoveHorizontal();
    }

    public void moveBoss(float delta){

    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        switch (level){
            case 1:
                damage_modifier = 1;
                hp_modifier = 1;
                break;
            case 2:
                damage_modifier = 2;
                hp_modifier = 2;
                break;
            case 3:
                damage_modifier = 3;
                hp_modifier = 3;
                break;
            case 4:
                damage_modifier = 4;
                hp_modifier = 4;
                break;
            case 5:
                damage_modifier = 5;
                hp_modifier = 5;
                break;
            case 6:
                damage_modifier = 6;
                hp_modifier = 6;
                break;
            case 7:
                damage_modifier = 7;
                hp_modifier = 7;
                break;
            case 8:
                damage_modifier = 8;
                hp_modifier = 8;
                break;
            default:
                damage_modifier = 1;
                hp_modifier = 1;
                break;
        }
    }
}
