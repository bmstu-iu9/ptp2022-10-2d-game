package playing.levels.clouds;

import utilz.LoadSave;

import java.awt.*;

import static utilz.Constants.TextureConstants.Level.*;

public class SmallCloud extends Cloud {


    public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
    public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

    public SmallCloud(int x, int y) {
        super(x * 4 * SMALL_CLOUD_WIDTH_DEFAULT, y);
        cloudSpeed = 0.1;
        loadCloudImg();
    }

    private void loadCloudImg() {
        cloud = LoadSave.GetSpriteAtlas(LEVEL_LOCATION_TEXTURES, LVL_CLOUDS_SMALL_PNG);
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
                (int) (SMALL_CLOUD_WIDTH_DEFAULT * scale),
                (int) (SMALL_CLOUD_HEIGHT_DEFAULT * scale),
                null);
    }

}
