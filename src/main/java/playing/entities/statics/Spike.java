package playing.entities.statics;

import playing.PlayingDrawInterface;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


import static utilz.Constants.LvlConstants.Entity.Spike.*;
import static utilz.Constants.TextureConstants.Entity.ENTITY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Entity.TRAP_ATLAS_PNG;

public class Spike extends ObjectEntity implements PlayingDrawInterface {
    private BufferedImage spikeImg;

    public enum SpikeState {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }
    public Spike(double x, double y, SpikeState type) {
        super(0,0,0,0);
        loadImages();
        setHitBoxTexture(x, y, SPIKE_WIDTH_TEXTURE, SPIKE_HEIGHT_TEXTURE);
        switch (type) {
            case DOWN:
                setHitBox(x,
                        y + SPIKE_HEIGHT_DEFAULT_UD,
                        SPIKE_WIDTH_DEFAULT_UD,
                        SPIKE_HEIGHT_DEFAULT_UD);
                break;
            case UP:
                spikeImg = createRotated(spikeImg, 180);
                setHitBox(x,
                        y,
                        SPIKE_WIDTH_DEFAULT_UD,
                        SPIKE_HEIGHT_DEFAULT_UD);
                break;
            case LEFT:
                spikeImg = createRotated(spikeImg, 90);
                setHitBox(x,
                        y,
                        SPIKE_WIDTH_DEFAULT_LR,
                        SPIKE_HEIGHT_DEFAULT_LR);
                break;
            case RIGHT:
                spikeImg = createRotated(spikeImg, 270);
                setHitBox(x + SPIKE_WIDTH_DEFAULT_LR,
                        y,
                        SPIKE_WIDTH_DEFAULT_LR,
                        SPIKE_HEIGHT_DEFAULT_LR);
                break;
        }

    }

    private static BufferedImage createRotated(BufferedImage image, int x) {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.PI / 180 * x, image.getWidth()/2.0, image.getHeight()/2.0);
        return createTransformed(image, at);
    }

    private static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    private void loadImages() {
        spikeImg = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, TRAP_ATLAS_PNG);
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(spikeImg,
                (int) ((hitBoxTexture.x - lvlOffsetX) * scale),
                (int) ((hitBoxTexture.y - lvlOffsetY) * scale),
                (int) (hitBoxTexture.width * scale),
                (int) (hitBoxTexture.height * scale),
                null);
        drawHitBox(g, scale, lvlOffsetX, lvlOffsetY);
//        drawHitBoxTexture(g, scale, lvlOffsetX, lvlOffsetY);
    }
}
