package ru.kromarong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.kromarong.base.BaseScreen;
import ru.kromarong.base.EnemiesControl;
import ru.kromarong.math.Rect;
import ru.kromarong.pool.BulletPool;
import ru.kromarong.pool.EnemyShipPool;
import ru.kromarong.sprite.Background;
import ru.kromarong.sprite.Bullet;
import ru.kromarong.sprite.EnemyShip;
import ru.kromarong.sprite.MainShip;
import ru.kromarong.sprite.Star;

public class GameScreen extends BaseScreen {

    private MainShip mainShip;
    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star starList[];

    private BulletPool bulletPool;
    private EnemyShipPool enemyPool;
    private EnemiesControl enemiesControl;

    private Music music;
    private Sound shootSound;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bulletPool = new BulletPool();
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        mainShip = new MainShip(atlas,bulletPool, shootSound);
        enemyPool = new EnemyShipPool(bulletPool, shootSound, worldBounds, mainShip);
        enemiesControl = new EnemiesControl(atlas, enemyPool, worldBounds);
        starList = new Star[64];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyedSprites();
        draw();
    }

    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
        enemiesControl.generate(delta);
        enemyPool.updateActiveSprites(delta);
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
    }

    private void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()){mainShip.draw(batch);}
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
    }

    private void checkCollisions(){

        for (Bullet bullet : bulletPool.getActiveObjects() ) {
            if (bullet.getOwner() == mainShip){
                for (EnemyShip enemy : enemyPool.getActiveObjects()){
                    if (!bullet.isOutside(enemy)){
                        bullet.destroy();
                        enemy.setHp(enemy.getHp()-bullet.getDamage());
                        if (enemy.getHp() <= 0){enemy.destroy();}
                    }
                }
            } else {
                if (!bullet.isOutside(mainShip)){
                    bullet.destroy();
                    mainShip.setHp(mainShip.getHp() - bullet.getDamage());
                    System.out.println(mainShip.getHp() + " " + bullet.getDamage());
                }
            }
        }

        for(EnemyShip enemy : enemyPool.getActiveObjects()){
            if (!enemy.isOutside(mainShip)){
                mainShip.setHp(mainShip.getHp() - enemy.getHp());
                enemy.destroy();
            }
        }

        if (mainShip.getHp() <= 0) {
            mainShip.destroy();
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return false;
    }
}
