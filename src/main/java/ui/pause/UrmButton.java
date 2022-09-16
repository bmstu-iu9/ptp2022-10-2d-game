package ui.pause;

import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Overlay.OVERLAY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Overlay.OVERLAY_URM_BUTTONS_PNG;
import static utilz.Constants.UI.URMButtons.URM_DEFAULT_SIZE;

public class UrmButton extends Button {
    private BufferedImage[] images;
    public UrmButton(int x, int y, int width, int height, int typeButton) {
        super(x, y, width, height);
        this.typeButton = typeButton;
        loadImages();
    }

    @Override
    protected void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(OVERLAY_LOCATION_TEXTURES, OVERLAY_URM_BUTTONS_PNG);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(
                    i * URM_DEFAULT_SIZE, typeButton * URM_DEFAULT_SIZE,
                    URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
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
        g.drawImage(images[stateButton],
                (int) (x * scale),
                (int) (y * scale),
                (int) (width * scale),
                (int) (height * scale),
                null);

    }
}
