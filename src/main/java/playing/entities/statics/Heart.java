package playing.entities.statics;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameConstants.ANI_SPEED_OBJECT;
import static utilz.Constants.LvlConstants.Entity.Heart.HEART_HEIGHT_DEFAULT;
import static utilz.Constants.LvlConstants.Entity.Heart.HEART_WIDTH_DEFAULT;
import static utilz.Constants.TextureConstants.Entity.ENTITY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Entity.HEART_ATLAS_PNG;

public class Heart extends ObjectEntity implements PlayingDrawInterface, PlayingUpdateInterface {

    private BufferedImage[] heartAnimation;

    private int aniTick, aniIndex;

    private boolean isActive = true;

    public Heart(double x, double y) {
        super(x, y, HEART_WIDTH_DEFAULT, HEART_HEIGHT_DEFAULT);
        setHitBoxTexture(x, y, HEART_WIDTH_DEFAULT, HEART_HEIGHT_DEFAULT);
        loadImages();
    }

    private void loadImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, HEART_ATLAS_PNG);
        heartAnimation = new BufferedImage[4];
        for (int i = 0; i < heartAnimation.length; i++) {
            heartAnimation[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(heartAnimation[aniIndex],
                (int) ((hitBoxTexture.x - lvlOffsetX) * scale),
                (int) ((hitBoxTexture.y - lvlOffsetY) * scale),
                (int) (hitBoxTexture.width * scale),
                (int) (hitBoxTexture.height * scale),
                null);
        drawHitBox(g, scale, lvlOffsetX, lvlOffsetY);
    }

    @Override
    public void update() {
        updateAnimationTick();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED_OBJECT) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= heartAnimation.length) {
                aniIndex = 0;
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
