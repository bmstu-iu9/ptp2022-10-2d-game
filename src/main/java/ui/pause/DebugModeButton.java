package ui.pause;

import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Overlay.OVERLAY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Overlay.OVERLAY_DEBUG_MODE_SUMMON_PNG;
import static utilz.Constants.UI.DebugModeSummon.DM_SUMMON_DEFAULT_SIZE;


public class DebugModeButton extends Button {
    private BufferedImage[] images;

    public DebugModeButton(int x, int y, int width, int height, int typeButton) {
        super(x, y, width, height);
        this.typeButton = typeButton;
        loadImages();
    }

    @Override
    protected void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(OVERLAY_LOCATION_TEXTURES, OVERLAY_DEBUG_MODE_SUMMON_PNG);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(
                    i * DM_SUMMON_DEFAULT_SIZE, typeButton * DM_SUMMON_DEFAULT_SIZE,
                    DM_SUMMON_DEFAULT_SIZE, DM_SUMMON_DEFAULT_SIZE);
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
