package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;

public class HealthBar extends Sprite {

    private MainShip mainShip;

    public HealthBar(TextureAtlas atlas, MainShip mainShip, Rect worldBounds) {
        super(atlas.findRegion("health_bar"), 6,1,6);
        this.mainShip = mainShip;
        setHeightProportion(0.05f);
        setRight(worldBounds.getLeft() - getWidth());
        setTop(worldBounds.getTop() + 0.48f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (mainShip.getHp()){
            case 30:
            case 29:
            case 28:
            case 27:
            case 26:
                frame = 5;
                break;
            case 25:
            case 24:
            case 23:
            case 22:
            case 21:
                frame = 4;
                break;
            case 20:
            case 19:
            case 18:
            case 17:
            case 16:
                frame = 3;
                break;
            case 15:
            case 14:
            case 13:
            case 12:
            case 11:
                frame = 2;
                break;
            case 10:
            case 9:
            case 8:
            case 7:
            case 6:
                frame = 1;
                break;
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                frame = 0;
                break;
        }
    }
}
