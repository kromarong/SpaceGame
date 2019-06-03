package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;

public class ShieldBar extends Sprite {
    private Shield shield;

    public ShieldBar(TextureRegion region, Shield shield, Rect worldBounds) {
        super(region, 11,1,11);
        this.shield = shield;
        setHeightProportion(0.05f);
        setRight( getWidth() * 1.5f);
        setBottom(-0.5f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (shield.getHp()){

            case 10:
                frame = 0;
                break;
            case 9:
                frame = 1;
                break;
            case 8:
                frame = 2;
                break;
            case 7:
                frame = 3;
                break;
            case 6:
                frame = 4;
                break;
            case 5:
                frame = 5;
                break;
            case 4:
                frame = 6;
                break;
            case 3:
                frame = 7;
                break;
            case 2:
                frame = 8;
                break;
            case 1:
                frame = 9;
                break;
            case 0:
                frame = 10;
                break;
        }
    }
}
