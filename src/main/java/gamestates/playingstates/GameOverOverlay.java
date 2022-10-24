package gamestates.playingstates;

import gamestates.EnumGameState;
import gamestates.Playing;
import ui.pause.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameWindowConstants.GAME_HEIGHT_DEFAULT;
import static utilz.Constants.GameWindowConstants.GAME_WIDTH_DEFAULT;
import static utilz.Constants.TextureConstants.Overlay.GAME_OVER_ATLAS_PNG;
import static utilz.Constants.TextureConstants.Overlay.OVERLAY_LOCATION_TEXTURES;
import static utilz.Constants.UI.Overlay.GameOver.*;
import static utilz.Constants.UI.URMButtons.*;

public class GameOverOverlay extends PlayState {

    private BufferedImage backgroundImg;
    private int gameOverX, gameOverY, gameOverWidth, gameOverHeight;
    private UrmButton menu, play;

    public GameOverOverlay(Playing playing) {
        super(playing);
        loadImages();
        calcBorder();
        createButtons();
    }


    @Override
    protected void loadImages() {
        backgroundImg = LoadSave.GetSpriteAtlas(OVERLAY_LOCATION_TEXTURES, GAME_OVER_ATLAS_PNG);
    }

    @Override
    protected void calcBorder() {
        gameOverWidth = backgroundImg.getWidth();
        gameOverHeight = backgroundImg.getHeight();
        gameOverX = GAME_WIDTH_DEFAULT / 2 - gameOverWidth / 2;
        gameOverY = GAME_HEIGHT_DEFAULT / 2 - gameOverHeight / 2;
    }

    @Override
    protected void createButtons() {
        play = new UrmButton(GAME_OVER_URM_PLAY_POS_X,
                GAME_OVER_URM_POS_Y,
                URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_PLAY);
        menu = new UrmButton(GAME_OVER_URM_MENU_POS_X,
                GAME_OVER_URM_POS_Y,
                URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_MENU);
    }

    @Override
    public void update() {
        menu.update();
        play.update();
    }

    @Override
    public void draw(Graphics g, float scale) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0,
                (int) (GAME_WIDTH_DEFAULT * scale),
                (int) (GAME_HEIGHT_DEFAULT * scale));

        g.drawImage(backgroundImg,
                (int) (gameOverX * scale),
                (int) (gameOverY * scale),
                (int) (gameOverWidth * scale),
                (int) (gameOverHeight * scale),
                null);
        menu.draw(g, scale);
        play.draw(g, scale);
    }

    @Override
    public void mouseClicked(MouseEvent e, float scale) {

    }

    @Override
    public void mousePressed(MouseEvent e, float scale) {
        if (isIn(e, menu, scale))
            menu.setMousePressed(true);
        else if (isIn(e, play, scale))
            play.setMousePressed(true);
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
        } else if (isIn(e, play, scale))
            if (play.isMousePressed()) {
                playing.resetAll();
                EnumPlayState.state = EnumPlayState.PLAYING;
                playing.resetDirBooleans();
            }

        menu.resetBool();
        play.resetBool();
    }

    @Override
    public void mouseDragged(MouseEvent e, float scale) {

    }

    @Override
    public void mouseMoved(MouseEvent e, float scale) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(e, menu, scale))
            menu.setMouseOver(true);
        else if (isIn(e, play, scale))
            play.setMouseOver(true);
    }

    @Override
    public void keyPressed(KeyEvent e, float scale) {

    }

    @Override
    public void keyReleased(KeyEvent e, float scale) {

    }

}
