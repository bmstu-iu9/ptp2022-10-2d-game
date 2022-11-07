package gamestates;
import gamestates.EnumGameState;
import ui.debug_mode.DebugModeCheat;
import ui.debug_mode.DebugModeCheatList;
import utilz.LoadSave;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameWindowConstants.*;
import static utilz.Constants.TextureConstants.DebugMode.*;
import static utilz.Constants.UI.DebugModeCheats.*;

import static utilz.Constants.GameConstants.*;

public class DebugMode extends GameState implements GamePanelInterface, GamePanelListenerInterface {

    private float scale;
    private Playing playing;

    private final DebugModeCheat[] buttons = new DebugModeCheat[1];
    private final DebugModeCheatList[] cheats = new DebugModeCheatList[COUNT_BUTTONS];
    private BufferedImage debug_modeImg, backgroundImg;
    private int debug_modeX, debug_modeY, debug_modeWidth, debug_modeHeight;

    public DebugMode(Playing playing) {
        loadBackgroundImg();
        loadButtons();
        loadCheats();
        calcBorder();
        this.playing = playing;
    }

    private void loadBackgroundImg() {
        debug_modeImg = LoadSave.GetSpriteAtlas(DEBUG_MODE_LOCATION_TEXTURES, DEBUG_MODE_ATLAS_PNG);
        backgroundImg = LoadSave.GetSpriteAtlas(DEBUG_MODE_LOCATION_TEXTURES, DEBUG_MODE_BACKGROUND_PNG);
    }


    private void loadButtons() {
        buttons[0] = new DebugModeCheat(
                GAME_WIDTH_DEFAULT / 2,
                GAME_HEIGHT_DEFAULT / 2,
                APPLY, EnumGameState.PLAYING);
    }

    private void loadCheats() {
        cheats[0] = new DebugModeCheatList(
                GAME_WIDTH_DEFAULT / 2,
                GAME_HEIGHT_DEFAULT / 2,
                ZERO_GRAVITY_CHEAT, EnumGameState.PLAYING);
        cheats[1] = new DebugModeCheatList(
                GAME_WIDTH_DEFAULT / 2,
                GAME_HEIGHT_DEFAULT / 2 + CHEAT_BUTTON_OFFSET,
                LEVEL_SELECT_1, EnumGameState.PLAYING);
        cheats[2] = new DebugModeCheatList(
                GAME_WIDTH_DEFAULT / 2,
                GAME_HEIGHT_DEFAULT / 2 + CHEAT_BUTTON_OFFSET * 2,
                LEVEL_SELECT_2, EnumGameState.PLAYING);
        cheats[3] = new DebugModeCheatList(
                GAME_WIDTH_DEFAULT / 2,
                GAME_HEIGHT_DEFAULT / 2 + CHEAT_BUTTON_OFFSET * 3,
                LEVEL_SELECT_3, EnumGameState.PLAYING);
    }

    private void calcBorder() {
        debug_modeWidth = debug_modeImg.getWidth();
        debug_modeHeight = debug_modeImg.getHeight();
        debug_modeX = GAME_WIDTH_DEFAULT / 2 - debug_modeWidth / 2;
        debug_modeY = GAME_HEIGHT_DEFAULT / 2 - debug_modeHeight / 2;
    }

    @Override
    public void update() {

        for (DebugModeCheat dmb : buttons) {
            dmb.update();
        }

        for (DebugModeCheatList dmb : cheats) {
            dmb.update();
        }
    }


    @Override
    public void draw(Graphics g, float scale) {

        g.drawImage(backgroundImg, 0 , 0,
                (int) (GAME_WIDTH_DEFAULT * scale),
                (int) (GAME_HEIGHT_DEFAULT * scale),
                null);
        g.drawImage(debug_modeImg,
                (int) (debug_modeX * scale),
                (int) (debug_modeY * scale),
                (int) (debug_modeWidth * scale),
                (int) (debug_modeHeight * scale),
                null);

        buttons[0].draw(g, scale);

        for (DebugModeCheatList dmb : cheats) {
            dmb.draw(g, scale);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, float scale) {

    }

    @Override
    public void mousePressed(MouseEvent e, float scale) {
        if (EnumGameState.state != EnumGameState.DEBUG_MODE)
            return;
        for (DebugModeCheat dmb : buttons) {
            if (isIn(e, dmb, scale)) {
                dmb.setMousePressed(true);
                break;
            }
        }

        for (DebugModeCheatList dmb : cheats) {
            if (isIn(e, dmb, scale)) {
                dmb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, float scale) {
        for (DebugModeCheat dmb : buttons) {
            if (isIn(e, dmb, scale)) {
                if (dmb.isMousePressed()) {
                    dmb.applyGameState();
                    break;
                }
            }
        }

        for (DebugModeCheatList dmb : cheats) {
            if (isIn(e, dmb, scale)) {
                if (dmb.isMousePressed()) {
                    switch (dmb.getTypeButton()) {
                        case ZERO_GRAVITY_CHEAT:
                            if (COU % 2 == 1) {
                                GRAVITY = 0.035f;
                                TEMP_GRAVITY = GRAVITY;

                                dmb.applyGameState();

                                COU++;
                                break;
                            } else if (GRAVITY == 0.035f) {
                                GRAVITY = 0.00f;
                                TEMP_GRAVITY = GRAVITY;

                                dmb.applyGameState();

                                COU++;
                                break;
                            } else {
                                dmb.applyGameState();

                                COU++;
                                break;
                            }
                        case LEVEL_SELECT_1:
                        case LEVEL_SELECT_2:
                        case LEVEL_SELECT_3:
                            playing.setLevel(dmb.getTypeButton() - 2);
                            dmb.applyGameState();
                            break;
                    }
                }
            }
        }

        resetButtons();
    }

    private void resetButtons() {
        for (DebugModeCheat dmb : buttons) {
            dmb.resetBool();
        }

        for (DebugModeCheatList dmb : cheats) {
            dmb.resetBool();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e, float scale) {

    }

    @Override
    public void mouseMoved(MouseEvent e, float scale) {
        for (DebugModeCheat dmb : buttons) {
            dmb.setMouseOver(false);
        }

        for (DebugModeCheat dmb : buttons) {
            if (isIn(e, dmb, scale)) {
                dmb.setMouseOver(true);
                break;
            }
        }

        for (DebugModeCheatList dmb : cheats) {
            dmb.setMouseOver(false);
        }

        for (DebugModeCheatList dmb : cheats) {
            if (isIn(e, dmb, scale)) {
                dmb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e, float scale) {

    }

    @Override
    public void keyReleased(KeyEvent e, float scale) {

    }
}