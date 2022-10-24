package playing.entities.statics;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameConstants.ANI_SPEED_ENEMY;
import static utilz.Constants.GameWindowConstants.TILE_SIZE_DEFAULT;
import static utilz.Constants.LvlConstants.Entity.Portal.PORTAL_HEIGHT_DEFAULT;
import static utilz.Constants.LvlConstants.Entity.Portal.PORTAL_WIDTH_DEFAULT;
import static utilz.Constants.TextureConstants.Entity.*;

public class Portal extends ObjectEntity implements PlayingDrawInterface, PlayingUpdateInterface {

    private BufferedImage[] portalAnimation;

    private int aniTick, aniIndex;

    public Portal(double x, double y) {
        super(x, y - TILE_SIZE_DEFAULT, PORTAL_WIDTH_DEFAULT, PORTAL_HEIGHT_DEFAULT);
        setHitBoxTexture(x, y - TILE_SIZE_DEFAULT, PORTAL_WIDTH_DEFAULT, PORTAL_HEIGHT_DEFAULT);
        loadImages();
    }

    private void loadImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, PORTAL_ATLAS_PNG);
        portalAnimation = new BufferedImage[4];
        for (int i = 0; i < portalAnimation.length; i++) {
            portalAnimation[i] = img.getSubimage(i * 32, 0, 32, 64);
        }
    }



    @Override
    public void update() {
        updateAnimationTick();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED_ENEMY * 2) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= portalAnimation.length) {
                aniIndex = 0;
            }
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(portalAnimation[aniIndex],
                (int) ((hitBoxTexture.x - lvlOffsetX) * scale),
                (int) ((hitBoxTexture.y - lvlOffsetY) * scale),
                (int) (hitBoxTexture.width * scale),
                (int) (hitBoxTexture.height * scale),
                null);
        drawHitBox(g, scale, lvlOffsetX, lvlOffsetY);
    }
}
