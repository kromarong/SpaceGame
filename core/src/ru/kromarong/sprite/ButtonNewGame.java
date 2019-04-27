package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.ScaledTouchUpButton;
import ru.kromarong.math.Rect;
import ru.kromarong.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    private MainShip mainShip;
    private GameScreen screen;

    public ButtonNewGame(TextureAtlas atlas, Rect worldBounds, MainShip mainShip, GameScreen screen) {
        super(atlas.findRegion("button_new_game"));
        this.screen = screen;
        this.mainShip = mainShip;

        setHeightProportion(0.08f);
        setBottom(worldBounds.getHalfHeight() - 0.25f);
        setLeft(worldBounds.getLeft() - 0.26f);

    }

    @Override
    protected void action() {
        mainShip.setHp(30);
        mainShip.flushDestroy();
        mainShip.pos.x = 0f;
        screen.freeAllActiveSprites();
        screen.setStatePlaying();
    }

}
