package playing.entities.statics;

import playing.PlayingDrawInterface;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Entity.ENTITY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Entity.TRAP_ATLAS_PNG;

public class Spike extends StaticEntity implements PlayingDrawInterface {
    private BufferedImage spikeImg;
    private final Rectangle2D.Double hitBox;

    public enum SpikeState {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }

    public Spike(double x, double y, SpikeState type) {
        super(x, y);
        hitBox = new Rectangle2D.Double(x, y, 32, 32);
        switch (type) {
            case DOWN:
                break;
            case UP:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
        }
        loadImages();
    }

    private void loadImages() {
        spikeImg = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, TRAP_ATLAS_PNG);
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(spikeImg,
                (int) ((hitBox.x - lvlOffsetX) * scale),
                (int) ((hitBox.y - lvlOffsetY) * scale),
                (int) (hitBox.width * scale),
                (int) (hitBox.height * scale),
                null);

    }

    public Rectangle2D.Double getHitBox() {
        return hitBox;
    }
}