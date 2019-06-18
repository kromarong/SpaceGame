package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.kromarong.base.Sprite;

public class HealthBar extends Sprite {

    private MainShip mainShip;

    public HealthBar(TextureAtlas atlas, MainShip mainShip) {
        this.regions = initRegions(atlas);
        this.mainShip = mainShip;
        setHeightProportion(0.04f);
        setLeft( -getWidth() * 1.3f);
        setBottom(-0.5f);
    }

    private TextureRegion[] initRegions(TextureAtlas atlas){
        TextureRegion[] regions = new TextureRegion[11];
        int count = 0;
        while(count <= 10){
            regions[count] = findRegions(count, atlas);
            count++;
        }
        return regions;
    }

    private TextureRegion findRegions(int count, TextureAtlas atlas) {
        switch (count){
            case 0:
                return atlas.findRegion("health0");
            case 1:
                return atlas.findRegion("health1");
            case 2:
                return atlas.findRegion("health2");
            case 3:
                return atlas.findRegion("health3");
            case 4:
                return atlas.findRegion("health4");
            case 5:
                return atlas.findRegion("health5");
            case 6:
                return atlas.findRegion("health6");
            case 7:
                return atlas.findRegion("health7");
            case 8:
                return atlas.findRegion("health8");
            case 9:
                return atlas.findRegion("health9");
            case 10:
                return atlas.findRegion("health10");
        }

        return atlas.findRegion("health10");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (mainShip.getHp()){
            case 10:
                frame = 10;
                break;
            case 9:
                frame = 9;
                break;
            case 8:
                frame = 8;
                break;
            case 7:
                frame = 7;
                break;
            case 6:
                frame = 6;
                break;
            case 5:
                frame = 5;
                break;
            case 4:
                frame = 4;
                break;
            case 3:
                frame = 3;
                break;
            case 2:
                frame = 2;
                break;
            case 1:
                frame = 1;
                break;
            case 0:
                frame = 0;
                break;
        }
    }
}
