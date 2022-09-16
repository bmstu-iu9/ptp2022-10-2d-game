package gamestates.playingstates;

import gamestates.EnumGameState;
import gamestates.Playing;
import main.Game;
import ui.pause.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameWindowConstants.GAME_HEIGHT_DEFAULT;
import static utilz.Constants.GameWindowConstants.GAME_WIDTH_DEFAULT;
import static utilz.Constants.TextureConstants.Overlay.*;
import static utilz.Constants.UI.Overlay.GameOver.*;
import static utilz.Constants.UI.Overlay.GameOver.GAME_OVER_URM_POS_Y;
import static utilz.Constants.UI.Overlay.LevelCompleted.*;
import static utilz.Constants.UI.URMButtons.*;

public class LevelCompleteOverlay extends PlayState {


    private BufferedImage backgroundImg;
    private int completeX, completeY, completeWidth, completeHeight;
    private UrmButton menu, next;

    public LevelCompleteOverlay(Playing playing) {
        super(playing);
        loadImages();
        calcBorder();
        createButtons();
    }

    protected void loadImages() {
        backgroundImg = LoadSave.GetSpriteAtlas(OVERLAY_LOCATION_TEXTURES, COMPLETED_ATLAS_PNG);
    }

    protected void calcBorder() {
        completeWidth = backgroundImg.getWidth();
        completeHeight = backgroundImg.getHeight();
        completeX = GAME_WIDTH_DEFAULT / 2 - completeWidth / 2;
        completeY = GAME_HEIGHT_DEFAULT / 2 - completeHeight / 2;
    }

    protected void createButtons() {
        next = new UrmButton(LEVEL_COMPLETED_URM_NEXT_POS_X,
                LEVEL_COMPLETED_URM_POS_Y,
                URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_PLAY);
        menu = new UrmButton(LEVEL_COMPLETED_URM_MENU_POS_X,
                LEVEL_COMPLETED_URM_POS_Y,
                URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_MENU);
    }

    @Override
    public void update() {
        menu.update();
        next.update();
    }

    @Override
    public void draw(Graphics g, float scale) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0,
                (int) (GAME_WIDTH_DEFAULT * scale),
                (int) (GAME_HEIGHT_DEFAULT * scale));

        g.drawImage(backgroundImg,
                (int) (completeX * scale),
                (int) (completeY * scale),
                (int) (completeWidth * scale),
                (int) (completeHeight * scale),
                null);
        next.draw(g, scale);
        menu.draw(g, scale);
    }

    @Override
    public void mouseClicked(MouseEvent e, float scale) {

    }

    @Override
    public void mousePressed(MouseEvent e, float scale) {
        if (isIn(e, menu, scale))
            menu.setMousePressed(true);
        else if (isIn(e, next, scale))
            next.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e, float scale) {
        if (isIn(e, menu, scale)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                EnumGameState.state = EnumGameState.MENU;
                EnumPlayState.state = EnumPlayState.PLAYING;
                playing.resetDirBooleans();
            }
        } else if (isIn(e, next, scale))
            if (next.isMousePressed()) {
                playing.nextLevel();
                EnumPlayState.state = EnumPlayState.PLAYING;
                playing.resetDirBooleans();
            }

        menu.resetBool();
        next.resetBool();
    }

    @Override
    public void mouseDragged(MouseEvent e, float scale) {

    }

    @Override
    public void mouseMoved(MouseEvent e, float scale) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(e, menu, scale))
            menu.setMouseOver(true);
        else if (isIn(e, next, scale))
            next.setMouseOver(true);
    }

    @Override
    public void keyPressed(KeyEvent e, float scale) {

    }

    @Override
    public void keyReleased(KeyEvent e, float scale) {

    }
}