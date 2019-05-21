package ru.kromarong.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.ScaledTouchUpButton;
import ru.kromarong.math.Rect;
import ru.kromarong.screen.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

    private Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(0);
        setRight(getHalfWidth());
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen(game, 1));
    }
}
