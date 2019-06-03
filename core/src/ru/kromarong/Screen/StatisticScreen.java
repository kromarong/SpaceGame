package ru.kromarong.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import ru.kromarong.base.BaseScreen;
import ru.kromarong.math.Rect;
import ru.kromarong.sprite.Background;
import ru.kromarong.utils.Font;

public class StatisticScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    private int frags = 0;
    private int smallFrags = 0;
    private int mediumFrags = 0;
    private int bigFrags = 0;
    private int repairKit = 0;

    private static final String SMALL_FRAGS = "Small ship destroyed: ";
    private static final String MEDIUM_FRAGS = "Medium ship destroyed: ";
    private static final String BIG_FRAGS = "Big ship destroyed: ";
    private static final String REPAIR_KIT = "Repair kit get: ";

    private Font font;
    private StringBuilder sbSmallFrags = new StringBuilder();
    private StringBuilder sbMediumFrags = new StringBuilder();
    private StringBuilder sbBigFrags = new StringBuilder();
    private StringBuilder sbRepairKit = new StringBuilder();


    public StatisticScreen(int frags, int smallFrags, int mediumFrags, int bigFrags, int repairKit) {
        this.frags = frags;
        this.smallFrags = smallFrags;
        this.mediumFrags = mediumFrags;
        this.bigFrags = bigFrags;
        this.repairKit = repairKit;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background.png");
        background = new Background(new TextureRegion(bg));
        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(0.025f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
    }

    public void draw(){
        batch.begin();
        background.draw(batch);
        printStatistic();
        batch.end();
    }
    private void printStatistic() {
        sbSmallFrags.setLength(0);
        sbMediumFrags.setLength(0);
        sbBigFrags.setLength(0);
        sbRepairKit.setLength(0);

        font.draw(batch, sbSmallFrags.append(SMALL_FRAGS).append(smallFrags), worldBounds.getLeft() + worldBounds.getHalfWidth(), worldBounds.getBottom() + 0.7f, Align.center);
        font.draw(batch, sbMediumFrags.append(MEDIUM_FRAGS).append(mediumFrags), worldBounds.getLeft() + worldBounds.getHalfWidth(), worldBounds.getBottom() + 0.6f, Align.center);
        font.draw(batch, sbBigFrags.append(BIG_FRAGS).append(bigFrags), worldBounds.getLeft() + worldBounds.getHalfWidth(), worldBounds.getBottom() + 0.5f, Align.center);
        font.draw(batch, sbRepairKit.append(REPAIR_KIT).append(repairKit), worldBounds.getLeft() + worldBounds.getHalfWidth(), worldBounds.getBottom() + 0.4f, Align.center);

    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
    }
}
