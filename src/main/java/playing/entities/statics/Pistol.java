package playing.entities.statics;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameConstants.ANI_SPEED_OBJECT;
import static utilz.Constants.TextureConstants.Entity.*;
import static utilz.Constants.LvlConstants.Entity.Pistol.*;

public class Pistol extends ObjectEntity implements PlayingDrawInterface, PlayingUpdateInterface {
    private BufferedImage[] pistolAnimation;

    private int aniTick, aniIndex;
    private boolean turnAniIndex = false;

    private boolean isActive = true;
    public Pistol(double x, double y) {
        super(x, y, PISTOL_WIDTH_DEFAULT, PISTOL_HEIGHT_DEFAULT);
        setHitBoxTexture(x, y, PISTOL_WIDTH_DEFAULT, PISTOL_HEIGHT_DEFAULT);
        loadImages();
    }

    private void loadImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, PISTOL_ATLAS_PNG);
        pistolAnimation = new BufferedImage[6];
        for (int i = 0; i < pistolAnimation.length; i++) {
            pistolAnimation[i] = img.getSubimage(i * 64, 0, 64, 24);
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(pistolAnimation[aniIndex],
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
            if (!turnAniIndex){
                aniIndex++;
            } else {
                aniIndex--;
            }
            if (aniIndex >= pistolAnimation.length) {
                aniIndex = pistolAnimation.length-2;
                turnAniIndex = true;
            }
            if (aniIndex < 0){
                aniIndex = 1;
                turnAniIndex = false;
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
