package ui.debug_mode;

import gamestates.EnumGameState;
import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.DebugMode.*;
import static utilz.Constants.UI.Button.*;
import static utilz.Constants.UI.DebugModeCheats.*;

public class DebugModeCheatList extends Button {

    private final EnumGameState state;
    private BufferedImage[] cheats;

    public DebugModeCheatList(int x, int y, int typeButton, EnumGameState state) {
        super(x - CHEAT_WIDTH_DEFAULT / 2, y - 11,
                CHEAT_WIDTH_DEFAULT, CHEAT_HEIGHT_DEFAULT);
        this.typeButton = typeButton;
        this.state = state;
        loadImages();
    }

    protected void loadImages() {
        cheats = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(DEBUG_MODE_LOCATION_TEXTURES, ZG_CHEAT_BUTTONS_PNG);
        for (int i = 0; i < cheats.length; i++) {
            cheats[i] = temp.getSubimage(
                    i * CHEAT_WIDTH_DEFAULT, 0,
                    CHEAT_WIDTH_DEFAULT, CHEAT_HEIGHT_DEFAULT);
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
        int buttonWidth = (int) (CHEAT_WIDTH_DEFAULT * scale);
        int buttonHeight = (int) (CHEAT_HEIGHT_DEFAULT * scale);
        int buttonX = (int) (x * scale);
        int buttonY = (int) (y * scale);
        g.drawImage(cheats[stateButton], buttonX, buttonY,
                buttonWidth, buttonHeight, null);
    }

    public void applyGameState() {
        EnumGameState.state = state;
    }

}
