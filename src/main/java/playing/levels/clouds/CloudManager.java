package playing.levels.clouds;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CloudManager implements PlayingUpdateInterface, PlayingDrawInterface {
    private ArrayList<BigCloud> bigClouds;
    private ArrayList<SmallCloud> smallClouds;
    private final Random rnd = new Random();

    public CloudManager() {
        initClasses();
    }

    private void initClasses() {
        bigClouds = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bigClouds.add(new BigCloud(i, 204));
        }

        smallClouds = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            smallClouds.add(new SmallCloud(i, 90 + rnd.nextInt(100)));
        }
    }


    @Override
    public void update() {
        for (BigCloud bigCloud : bigClouds) {
            bigCloud.update();
        }

        for (SmallCloud smallCloud : smallClouds) {
            smallCloud.update();
        }

        if (bigClouds.get(0).getX() <= -BigCloud.BIG_CLOUD_WIDTH_DEFAULT) {
            bigClouds.remove(0);
            bigClouds.add(new BigCloud(3, 204));
        }

        if (smallClouds.get(0).getX() <= -SmallCloud.SMALL_CLOUD_WIDTH_DEFAULT) {
            smallClouds.remove(0);
            smallClouds.add(new SmallCloud(7, 90 + rnd.nextInt(100)));
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (int i = 0; i < bigClouds.size(); i++) {
            BigCloud bigCloud = bigClouds.get(i);
            bigCloud.draw(g, scale, lvlOffsetX, lvlOffsetY);
        }

        for (int i = 0; i < smallClouds.size(); i++) {
            SmallCloud smallCloud = smallClouds.get(i);
            smallCloud.draw(g, scale, lvlOffsetX, lvlOffsetY);
        }
    }
}