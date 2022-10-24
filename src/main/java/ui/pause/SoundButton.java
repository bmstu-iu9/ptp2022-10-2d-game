package ui.pause;

import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Overlay.OVERLAY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Overlay.OVERLAY_SOUND_BUTTONS_PNG;
import static utilz.Constants.UI.SoundButtons.SOUND_SIZE_DEFAULT;

public class SoundButton extends Button {
    private BufferedImage[][] soundImages;

    private boolean muted;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadImages();
    }

    @Override
    protected void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(OVERLAY_LOCATION_TEXTURES, OVERLAY_SOUND_BUTTONS_PNG);
        soundImages = new BufferedImage[2][3];
        for (int j = 0; j < soundImages.length; j++)
            for (int i = 0; i < soundImages[j].length; i++)
                soundImages[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT,
                        j * SOUND_SIZE_DEFAULT,
                        SOUND_SIZE_DEFAULT,
                        SOUND_SIZE_DEFAULT);
    }

    @Override
    public void update() {
        if (muted)
            typeButton = 1;
        else
            typeButton = 0;

        stateButton = 0;
        if (mouseOver)
            stateButton = 1;
        if (mousePressed)
            stateButton = 2;
    }

    @Override
    public void draw(Graphics g, float scale) {
        g.drawImage(soundImages[typeButton][stateButton],
                (int) (x * scale),
                (int) (y * scale),
                (int) (width * scale),
                (int) (height * scale),
                null);
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
