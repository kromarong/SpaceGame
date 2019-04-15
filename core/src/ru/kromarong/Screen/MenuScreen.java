package ru.kromarong.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.BaseScreen;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.Background;
import ru.kromarong.sprite.MainObject;


public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private Texture obj;
    private MainObject object;

    @Override
    public void show() {
        super.show();
        bg = new Texture("background2.png");
        background = new Background(new TextureRegion(bg));
        obj = new Texture("rightMiku.png");
        object = new MainObject(new TextureRegion(obj));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        object.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        object.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        obj.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        object.touchDown(touch,pointer);
        return false;
    }
}
