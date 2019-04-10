package ru.kromarong.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.Base.BaseScreen;

public class GameScreen extends BaseScreen {

    private Vector2 touch;
    private Vector2 objPos;
    private Vector2 speed;
    private Vector2 targetPos;
    private Texture img;
    private Texture donut;
    private boolean leftMove = false;
    private boolean rightMove = false;
    private boolean isExistDonut = false;
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    @Override
    public void show() {
        super.show();
        touch = new Vector2();
        objPos = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        targetPos = new Vector2();
        speed = new Vector2(0.0f, 0.0f);
        img = new Texture("rightMiku.png");
        donut = new Texture("donut.png");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if ((speed.x > 0) && (!rightMove)) {
            img = new Texture("rightMiku.png");
            rightMove = true;
            leftMove = false;
        } else if ((speed.x < 0) && (!leftMove)) {
            img = new Texture("leftMiku.png");
            leftMove = true;
            rightMove = false;
        }
        objPos.add(speed);
        batch.begin();
        batch.draw(img, objPos.x, objPos.y);
        if (isExistDonut) {
            batch.draw(donut, targetPos.x, targetPos.y);
        }
        batch.end();
        if ((objPos.y > Gdx.graphics.getHeight() - 65) || (objPos.y < -66) ||
                (objPos.x > Gdx.graphics.getWidth() - 65) || (objPos.x < -66)) {
            speed.setZero();
        }
        if (isExistDonut && (Math.abs(objPos.x - targetPos.x) < 32) && ((Math.abs(objPos.y - targetPos.y) < 32))) {
            speed.setZero();
            img = new Texture("happyMiku.png");
            isExistDonut = false;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        donut.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case (19): //up
                speed.set(speed.x, 1);
                isUpPressed = true;
                break;
            case (20): //down
                speed.set(speed.x, -1);
                isDownPressed = true;
                break;
            case (21): //left
                speed.set(-1, speed.y);
                isLeftPressed = true;
                break;
            case (22): //right
                speed.set(1, speed.y);
                isRightPressed = true;
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case (19): //up
                isUpPressed = false;
                if (isDownPressed) {
                    break;
                }
                speed.set(speed.x, 0);
                break;
            case (20): //down
                isDownPressed = false;
                if (isUpPressed) {
                    break;
                }
                speed.set(speed.x, 0);
                break;
            case (21): //left
                isLeftPressed = false;
                if (isRightPressed) {
                    break;
                }
                speed.set(0, speed.y);
                break;
            case (22): //right
                isRightPressed = false;
                if (isLeftPressed) {
                    break;
                }
                speed.set(0, speed.y);
                break;
        }
        return super.keyUp(keycode);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        targetPos.set(touch.x - 32, touch.y - 32);
        isExistDonut = true;
        speed = touch.sub(objPos);
        speed.nor();
        return false;
    }
}
