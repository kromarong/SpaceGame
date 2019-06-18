package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.kromarong.base.Sprite;

public class ShieldBar extends Sprite {
    private Shield shield;

    public ShieldBar(TextureAtlas atlas, Shield shield) {
        this.regions = initRegions(atlas);
        this.shield = shield;
        setHeightProportion(0.04f);
        setRight( getWidth() * 1.4f);
        setBottom(-0.5f);
    }

    private TextureRegion[] initRegions(TextureAtlas atlas){
        TextureRegion[] regions = new TextureRegion[6];
        int count = 0;
        while(count <= 5){
            regions[count] = findRegions(count, atlas);
            count++;
        }
        return regions;
    }

    private TextureRegion findRegions(int count, TextureAtlas atlas) {

        switch (count){
            case 0:
                return atlas.findRegion("shield0");
            case 1:
                return atlas.findRegion("shield1");
            case 2:
                return atlas.findRegion("shield2");
            case 3:
                return atlas.findRegion("shield3");
            case 4:
                return atlas.findRegion("shield4");
            case 5:
                return atlas.findRegion("shield5");
        }

        return atlas.findRegion("shield0");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (shield.getHp()){
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
