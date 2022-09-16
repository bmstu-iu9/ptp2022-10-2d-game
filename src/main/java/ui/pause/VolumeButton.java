package ui.pause;

import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Overlay.OVERLAY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Overlay.OVERLAY_VOLUME_BUTTONS_PNG;
import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends Button {

    private BufferedImage[] images;

    private BufferedImage slider;

    private int buttonX, minX, maxX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        calcBorder();
        loadImages();
    }

    protected void calcBorder() {
        buttonX = x + width / 2;
        minX = x + VOLUME_WIDTH_DEFAULT / 2;
        maxX = x + width - VOLUME_WIDTH_DEFAULT / 2;

    }

    @Override
    protected void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(OVERLAY_LOCATION_TEXTURES, OVERLAY_VOLUME_BUTTONS_PNG);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(
                    i * VOLUME_WIDTH_DEFAULT, 0,
                    VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);

        slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0,
                SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
    }

    @Override
    public void update() {
        stateButton = 0;
        if (mouseOver)
            stateButton = 1;
        if (mousePressed)
            stateButton = 2;
    }

    @Override
    public void draw(Graphics g, float scale) {
        g.drawImage(slider,
                (int) (x * scale),
                (int) (y * scale),
                (int) (width * scale),
                (int) (height * scale),
                null);
        g.drawImage(images[stateButton],
                (int) ((buttonX - VOLUME_WIDTH_DEFAULT / 2) * scale),
                (int) (y * scale),
                (int) (VOLUME_WIDTH_DEFAULT * scale),
                (int) (height * scale),
                null);

    }

    public void changeX(int x, double scale) {
        if (x < minX * scale)
            buttonX = minX;
        else if (x > maxX * scale)
            buttonX = maxX;
        else
            buttonX = (int) (x / scale);

    }
}