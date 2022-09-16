package playing.levels.clouds;

import utilz.LoadSave;

import java.awt.*;

import static utilz.Constants.TextureConstants.Level.LEVEL_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Level.LVL_CLOUDS_BIG_PNG;

public class BigCloud extends Cloud {

    public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
    public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;

    public BigCloud(int x, int y) {
        super(x * BIG_CLOUD_WIDTH_DEFAULT-1, y);
        cloudSpeed = 0.2;
        loadCloudImg();
    }

    private void loadCloudImg() {
        cloud = LoadSave.GetSpriteAtlas(LEVEL_LOCATION_TEXTURES, LVL_CLOUDS_BIG_PNG);
    }

    @Override
    public void update() {
        hitBox.x -=cloudSpeed;
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(cloud,
                (int) ((hitBox.x) * scale),
                (int) (hitBox.y * scale),
                (int) (BIG_CLOUD_WIDTH_DEFAULT * scale),
                (int) (BIG_CLOUD_HEIGHT_DEFAULT * scale),
                null);
    }

}
