package ru.kromarong.screen;

import com.badlogic.gdx.utils.Align;

import ru.kromarong.base.BaseScreen;
import ru.kromarong.sprite.Star;
import ru.kromarong.utils.Font;

public class StatisticScreen extends BaseScreen {

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
        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(0.015f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
    }

    public void draw(){
        batch.begin();
        printInfo();
        batch.end();
    }
    private void printInfo() {
        sbSmallFrags.setLength(0);
        sbMediumFrags.setLength(0);
        sbBigFrags.setLength(0);
        sbRepairKit.setLength(0);

        font.draw(batch, sbSmallFrags.append(SMALL_FRAGS).append(smallFrags), worldBounds.getLeft(), worldBounds.getBottom() + 1f, Align.left);
        font.draw(batch, sbMediumFrags.append(MEDIUM_FRAGS).append(mediumFrags), worldBounds.getLeft(), worldBounds.getBottom() + 0.80f, Align.left);
        font.draw(batch, sbBigFrags.append(BIG_FRAGS).append(bigFrags), worldBounds.getLeft(), worldBounds.getBottom() + 0.60f, Align.left);
        font.draw(batch, sbRepairKit.append(REPAIR_KIT).append(repairKit), worldBounds.getLeft(), worldBounds.getBottom() + 0.40f, Align.left);

    }
}
