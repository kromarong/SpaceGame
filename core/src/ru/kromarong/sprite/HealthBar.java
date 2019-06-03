package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;

public class HealthBar extends Sprite {

    private MainShip mainShip;

    public HealthBar(TextureRegion region, MainShip mainShip, Rect worldBounds) {
        super(region, 33,1,33);
        this.mainShip = mainShip;
        setHeightProportion(0.05f);
        setLeft( -getWidth() * 1.4f);
        setBottom(-0.5f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (mainShip.getHp()){
            case 33:
                frame = 0;
                break;
            case 32:
                frame = 1;
                break;
            case 31:
                frame = 2;
                break;
            case 30:
                frame = 3;
                break;
            case 29:
                frame = 4;
                break;
            case 28:
                frame = 5;
                break;
            case 27:
                frame = 6;
                break;
            case 26:
                frame = 7;
                break;
            case 25:
                frame = 8;
                break;
            case 24:
                frame = 9;
                break;
            case 23:
                frame = 10;
                break;
            case 22:
                frame = 11;
                break;
            case 21:
                frame = 12;
                break;
            case 20:
                frame = 13;
                break;
            case 19:
                frame = 14;
                break;
            case 18:
                frame = 15;
                break;
            case 17:
                frame = 16;
                break;
            case 16:
                frame = 17;
                break;
            case 15:
                frame = 18;
                break;
            case 14:
                frame = 19;
                break;
            case 13:
                frame = 20;
                break;
            case 12:
                frame = 21;
                break;
            case 11:
                frame = 22;
                break;
            case 10:
                frame = 23;
                break;
            case 9:
                frame = 24;
                break;
            case 8:
                frame = 25;
                break;
            case 7:
                frame = 26;
                break;
            case 6:
                frame = 27;
                break;
            case 5:
                frame = 28;
                break;
            case 4:
                frame = 29;
                break;
            case 3:
                frame = 30;
                break;
            case 2:
                frame = 31;
                break;
            case 1:
                frame = 32;
                break;
            case 0:
                frame = 33;
                break;
        }
    }
}
