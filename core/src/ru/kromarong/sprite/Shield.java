package ru.kromarong.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.kromarong.base.Sprite;
import ru.kromarong.math.Rect;

public class Shield extends Sprite {

    private int hp;
    private int level;
    private Vector2 v;
    private MainShip mainShip;

    public Shield(TextureAtlas atlas, MainShip mainShip, int level) {
        super(atlas.findRegion("shield"));
        this.v = new Vector2();
        this.mainShip = mainShip;
        this.level = level;
        this.hp = 0;
        setHeightProportion(mainShip.getHeight() + 0.02f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.03f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        v.set(mainShip.getV());
        pos.mulAdd(v, delta);
    }

    public int getHp() {
        return hp;
    }

    public void setDefaultHp() {
        this.hp = Math.round(10);
    }

    public void damage (int damage){
        hp -= damage;
        if (hp <= 0){
            destroy();
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    public void setPos(){
        this.pos.x = mainShip.pos.x;
        this.pos.y = mainShip.pos.y;
    }

}
