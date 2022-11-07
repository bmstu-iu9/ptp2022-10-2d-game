package ui.debug_mode;

import gamestates.EnumGameState;
import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.DebugMode.*;
import static utilz.Constants.UI.Button.*;
import static utilz.Constants.UI.DebugModeCheats.*;

public class DebugModeCheat extends Button {

    private final EnumGameState state;
    private BufferedImage[] images;

    public DebugModeCheat(int x, int y, int typeButton, EnumGameState state) {
        super(x - BUTTON_WIDTH_DEFAULT / 2, y + BUTTON_HEIGHT_DEFAULT * 3 - 3,
                BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
        this.typeButton = typeButton;
        this.state = state;
        loadImages();
    }

    protected void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(DEBUG_MODE_LOCATION_TEXTURES, DEBUG_MODE_BUTTONS_PNG);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(
                    i * BUTTON_WIDTH_DEFAULT, typeButton * BUTTON_HEIGHT_DEFAULT,
                    BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
        }
    }

    public void update() {
        stateButton = ON;
        if (mouseOver) {
            stateButton = OVER;
        }
        if (mousePressed) {
            stateButton = PRESSED;
        }
    }

    public void draw(Graphics g, float scale) {
        int buttonWidth = (int) (BUTTON_WIDTH_DEFAULT * scale);
        int buttonHeight = (int) (BUTTON_HEIGHT_DEFAULT * scale);
        int buttonX = (int) (x * scale);
        int buttonY = (int) (y * scale);
        g.drawImage(images[stateButton], buttonX, buttonY,
                buttonWidth, buttonHeight, null);
    }

    public void applyGameState() {
        EnumGameState.state = state;
    }

}
