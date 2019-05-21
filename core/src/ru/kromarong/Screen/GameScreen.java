package ru.kromarong.screen;

import com.badlogic.gdx.Game;
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
import ru.kromarong.pool.ShieldCasePool;
import ru.kromarong.sprite.Background;
import ru.kromarong.sprite.Bullet;
import ru.kromarong.sprite.ButtonGameOver;
import ru.kromarong.sprite.ButtonNewGame;
import ru.kromarong.sprite.EnemyShip;
import ru.kromarong.sprite.HealthBar;
import ru.kromarong.sprite.Healthpack;
import ru.kromarong.sprite.MainShip;
import ru.kromarong.sprite.Shield;
import ru.kromarong.sprite.ShieldCase;
import ru.kromarong.sprite.Star;
import ru.kromarong.sprite.TrackingStar;
import ru.kromarong.utils.Font;

public class GameScreen extends BaseScreen {

    private Game game;

    private static final String FRAGS = "Frags: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, PAUSE, GAME_OVER, BOSS_FIGHT, LEVEL_COMPLETE}
    private enum ShieldState {ENABLED, DISABLED}
    private boolean bossFightState = false;

    private State state;
    private ShieldState shieldState;

    private Texture bg;
    private Background background;
    private TextureAtlas mainAtlas;
    private TextureAtlas objectAtlas;
    private TextureAtlas menuAtlas;
    private Star starList[];

    private ButtonGameOver gameOver;
    private ButtonNewGame newGame;

    private MainShip mainShip;
    private HealthBar healthBar;
    private Shield shield;

    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private HealthpackPool healthpackPool;
    private ShieldCasePool shieldCasePool;

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
    private int smallFrags = 0;
    private int mediumFrags = 0;
    private int bigFrags = 0;
    private int repairKit = 0;

    private int level;

    public GameScreen(Game game, int level) {
        this.game = game;
        this.level = level;
    }

    public GameScreen(MainShip mainShip, int level) {
        this.mainShip = mainShip;
        this.level = level;
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bg = new Texture("textures/background.png");
        background = new Background(new TextureRegion(bg));
        mainAtlas = new TextureAtlas("textures/mainAtlas.pack");
        objectAtlas = new TextureAtlas("textures/objectAtlas.pack");
        menuAtlas = new TextureAtlas("textures/menuAtlas.pack");

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(mainAtlas, explosionSound);
        mainShip = new MainShip(mainAtlas, bulletPool, explosionPool, laserSound);
        shield = new Shield(objectAtlas, mainShip, level);


        healthpackPool = new HealthpackPool(objectAtlas, worldBounds);
        shieldCasePool = new ShieldCasePool(objectAtlas, worldBounds);
        objectGenerator = new ObjectGenerator(healthpackPool, shieldCasePool, worldBounds);

        healthBar = new HealthBar(mainAtlas, mainShip, worldBounds);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, bulletSound, worldBounds, objectGenerator);
        enemyGenerator = new EnemyGenerator(mainAtlas, enemyShipPool, worldBounds);
        enemyGenerator.setLevel(level);


        starList = new TrackingStar[64];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new TrackingStar(objectAtlas, mainShip.getV());
        }

        gameOver = new ButtonGameOver(menuAtlas, worldBounds);
        newGame = new ButtonNewGame(menuAtlas, worldBounds, mainShip, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(0.03f);
        shieldState = ShieldState.ENABLED;
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
        if (frags >=20 && state != State.BOSS_FIGHT){
            enemyGenerator.generateBoss();
            state = State.BOSS_FIGHT;
            bossFightState = true;
        }
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            mainShip.update(delta);
            objectGenerator.generate(delta);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            healthpackPool.updateActiveSprites(delta);
            shieldCasePool.updateActiveSprites(delta);
            healthBar.update(delta);
            if (state != State.BOSS_FIGHT){
                enemyGenerator.generate(delta);
            }
            if (shieldState == ShieldState.ENABLED){
                shield.update(delta);
            }
        } else if (state == State.LEVEL_COMPLETE){
            game.setScreen(new StatisticScreen(frags, smallFrags, mediumFrags, bigFrags, repairKit));
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING && state != State.BOSS_FIGHT) {
            return;
        }
        List<EnemyShip> enemyList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                mainShip.damage(enemy.getHp());
                enemy.destroy();
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
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
                            switch (enemy.getType()){
                                case SMALL:
                                    smallFrags++;
                                    break;
                                case MEDIUM:
                                    mediumFrags++;
                                    break;
                                case BIG:
                                    bigFrags++;
                                    break;
                            }
                            frags = smallFrags + mediumFrags + bigFrags;
                        }
                        return;
                    }
                }
            } else {
                if (shieldState == ShieldState.ENABLED){
                    if (shield.isBulletCollision(bullet)){
                        shield.damage(bullet.getDamage());
                        bullet.destroy();
                        if (shield.isDestroyed()){
                            shieldState = ShieldState.DISABLED;
                        }
                        return;
                    }
                }else if (mainShip.isBulletCollision(bullet)) {
                        System.out.println("1 Shieldstate: " + shieldState + "mainShip HP: " + mainShip.getHp());
                        mainShip.damage(bullet.getDamage());
                        System.out.println("2 Shieldstate: " + shieldState + "mainShip HP: " + mainShip.getHp());
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
                repairKit++;
            }
            return;
        }

        List<ShieldCase> shieldList = shieldCasePool.getActiveObjects();

        for (ShieldCase shieldCase : shieldList){
            if (shieldCase.isDestroyed()) {
                continue;
            }
            if (shieldCase.isShipCollision(mainShip)){
                if (shieldState == ShieldState.DISABLED){
                    shield.flushDestroy();
                    shieldState = ShieldState.ENABLED;
                }
                shield.setDefaultHp();
                shield.setPos();
                shieldCase.destroy();
            }
            return;
        }
    }

    private void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        healthpackPool.freeAllDestroyedActiveSprites();
        shieldCasePool.freeAllDestroyedActiveSprites();
    }

    public void freeAllActiveSprites(){
        bulletPool.freeAllActiveSprites();
        enemyShipPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        healthpackPool.freeAllActiveSprites();
        shieldCasePool.freeAllActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipPool.drawActiveSprites(batch);
            healthpackPool.drawActiveSprites(batch);
            shieldCasePool.drawActiveSprites(batch);
            healthBar.draw(batch);
            if (shieldState == ShieldState.ENABLED){
                shield.draw(batch);
            }
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
        font.draw(batch, sbLevel.append(LEVEL).append(enemyGenerator.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            mainShip.resize(worldBounds);
            if (shieldState == ShieldState.ENABLED){
                shield.resize(worldBounds);
            }
        }
        if (state == State.GAME_OVER){
            gameOver.resize(worldBounds);
            newGame.resize(worldBounds);
        }
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            state = State.PAUSE;
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.PAUSE) {
            if (bossFightState == false) {
                state = State.PLAYING;
            }else {
                state = State.BOSS_FIGHT;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        mainAtlas.dispose();
        objectAtlas.dispose();
        menuAtlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        healthpackPool.dispose();
        shieldCasePool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            mainShip.touchDown(touch, pointer);
        }
        if (state == State.GAME_OVER){
            newGame.touchDown(touch,pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING || state == State.BOSS_FIGHT) {
            mainShip.touchUp(touch, pointer);
        }
        if (state == State.GAME_OVER){
            newGame.touchUp(touch,pointer);
        }
        return false;
    }

    public void setFragsToZero() {
        this.frags = 0;
        this.smallFrags = 0;
        this.mediumFrags = 0;
        this.bigFrags = 0;
        this.repairKit = 0;
    }

    public void setStatePlaying(){
        state = State.PLAYING;
    }
}
