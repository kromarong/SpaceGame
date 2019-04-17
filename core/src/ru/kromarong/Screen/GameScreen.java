package ru.kromarong.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.kromarong.base.BaseScreen;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.Background;
import ru.kromarong.sprite.Ship;
import ru.kromarong.sprite.Star;

public class GameScreen extends BaseScreen {

    private Ship ship;
    private Texture bg;
    private Texture shipImg;
    private Background background;
    private TextureAtlas atlas;
    private Star starList[];

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        shipImg = new Texture("textures/ship.png");
        ship = new Ship(new TextureRegion(shipImg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        starList = new Star[256];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starList) {
            star.resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
        ship.update(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        ship.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        shipImg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch,pointer);
        return super.touchDown(touch, pointer);
    }
}
