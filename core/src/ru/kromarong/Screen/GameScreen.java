package ru.kromarong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.kromarong.base.BaseScreen;
import ru.kromarong.base.EnemyGenerator;
import ru.kromarong.base.ObjectGenerator;
import ru.kromarong.math.Rect;
import ru.kromarong.pool.BulletPool;
import ru.kromarong.pool.EnemyShipPool;
import ru.kromarong.pool.ExplosionPool;
import ru.kromarong.pool.HealthpackPool;
import ru.kromarong.sprite.Background;
import ru.kromarong.sprite.Bullet;
import ru.kromarong.sprite.ButtonGameOver;
import ru.kromarong.sprite.ButtonNewGame;
import ru.kromarong.sprite.EnemyShip;
import ru.kromarong.sprite.HealthBar;
import ru.kromarong.sprite.Healthpack;
import ru.kromarong.sprite.MainShip;
import ru.kromarong.sprite.Star;
import ru.kromarong.sprite.TrackingStar;
import ru.kromarong.utils.Font;

public class GameScreen extends BaseScreen {

    private static final String FRAGS = "Frags: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private State state;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star starList[];

    private ButtonGameOver gameOver;
    private ButtonNewGame newGame;

    private MainShip mainShip;
    private HealthBar healthBar;

    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private HealthpackPool healthpackPool;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemyGenerator enemyGenerator;
    private ObjectGenerator objectGenerator;

    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    private int frags = 0;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        starList = new TrackingStar[64];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new TrackingStar(atlas, mainShip.getV());
        }
        Texture hpBar = new Texture("health_bar.png");
        TextureRegion hpbar = new TextureRegion(hpBar);
        Texture hpPack = new Texture("Healthico.png");
        TextureRegion hppack = new TextureRegion(hpPack);
        healthpackPool = new HealthpackPool(hppack, worldBounds);
        objectGenerator = new ObjectGenerator(healthpackPool, worldBounds);

        healthBar = new HealthBar(hpbar, mainShip, worldBounds);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, bulletSound, worldBounds, mainShip);
        enemyGenerator = new EnemyGenerator(atlas, enemyShipPool, worldBounds);
        gameOver = new ButtonGameOver(atlas, worldBounds);
        newGame = new ButtonNewGame(atlas, worldBounds, mainShip, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(0.03f);
        state = State.PLAYING;
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
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            enemyGenerator.generate(delta, frags);
            objectGenerator.generate(delta);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            healthpackPool.updateActiveSprites(delta);
            healthBar.update(delta);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<EnemyShip> enemyList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.destroy();
                mainShip.destroy();
                state = State.GAME_OVER;
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == mainShip) {
                for (EnemyShip enemy : enemyList) {
                    if (enemy.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(bullet.getDamage());
                        bullet.destroy();
                        if (enemy.isDestroyed()) {
                            frags++;
                        }
                        return;
                    }
                }
            } else {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                    bullet.destroy();
                    return;
                }
            }
        }

        List<Healthpack> hpList = healthpackPool.getActiveObjects();

        for (Healthpack healthpack : hpList){
            if (healthpack.isDestroyed()) {
                continue;
            }
            if (healthpack.isShipCollision(mainShip)){
                mainShip.setHp(mainShip.getHp() + 10);
                healthpack.destroy();
            }
            return;
        }
    }

    private void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        healthpackPool.freeAllDestroyedActiveSprites();
    }

    public void freeAllActiveSprites(){
        bulletPool.freeAllActiveSprites();
        enemyShipPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        healthpackPool.freeAllActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipPool.drawActiveSprites(batch);
            healthpackPool.drawActiveSprites(batch);
            healthBar.draw(batch);
        }

        if (state == State.GAME_OVER){
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getBottom() + 0.05f);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyGenerator.getStage()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        if (state == State.PLAYING) {
            mainShip.resize(worldBounds);
        }
        if (state == State.GAME_OVER){
            gameOver.resize(worldBounds);
            newGame.resize(worldBounds);
        }
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.PLAYING) {
            state = State.PAUSE;
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.PAUSE) {
            state = State.PLAYING;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        healthpackPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        }
        if (state == State.GAME_OVER){
            newGame.touchDown(touch,pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        }
        if (state == State.GAME_OVER){
            newGame.touchUp(touch,pointer);
        }
        return false;
    }

    public void setStatePlaying(){
        state = State.PLAYING;
    }
}
