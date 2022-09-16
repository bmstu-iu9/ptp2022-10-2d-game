package gamestates.playingstates;

import gamestates.EnumGameState;
import gamestates.Playing;
import ui.pause.SoundButton;
import ui.pause.UrmButton;
import ui.pause.VolumeButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameWindowConstants.GAME_HEIGHT_DEFAULT;
import static utilz.Constants.GameWindowConstants.GAME_WIDTH_DEFAULT;
import static utilz.Constants.TextureConstants.Overlay.PAUSE_ATLAS_PNG;
import static utilz.Constants.TextureConstants.Overlay.OVERLAY_LOCATION_TEXTURES;
import static utilz.Constants.UI.Overlay.Pause.*;
import static utilz.Constants.UI.SoundButtons.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay extends PlayState {

    private BufferedImage pauseImg;
    private int pauseX, pauseY, pauseWidth, pauseHeight;

    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        super(playing);
        loadImages();
        calcBorder();
        createButtons();
    }

    protected void loadImages() {
        pauseImg = LoadSave.GetSpriteAtlas(OVERLAY_LOCATION_TEXTURES, PAUSE_ATLAS_PNG);
    }

    protected void calcBorder() {
        pauseWidth = pauseImg.getWidth();
        pauseHeight = pauseImg.getHeight();
        pauseX = GAME_WIDTH_DEFAULT / 2 - pauseWidth / 2;
        pauseY = GAME_HEIGHT_DEFAULT / 2 - pauseHeight / 2;
    }

    protected void createButtons() {
        musicButton = new SoundButton(PAUSE_SOUND_POS_X,
                PAUSE_SOUND_MUSIC_POS_Y,
                SOUND_SIZE_DEFAULT,
                SOUND_SIZE_DEFAULT);
        sfxButton = new SoundButton(PAUSE_SOUND_POS_X,
                PAUSE_SOUND_SFX_POS_Y,
                SOUND_SIZE_DEFAULT,
                SOUND_SIZE_DEFAULT);

        unpauseB = new UrmButton(PAUSE_URM_PLAY_POS_X,
                PAUSE_URM_POS_Y, URM_DEFAULT_SIZE,
                URM_DEFAULT_SIZE, URM_PLAY);
        replayB = new UrmButton(PAUSE_URM_REPLAY_POS_X,
                PAUSE_URM_POS_Y, URM_DEFAULT_SIZE,
                URM_DEFAULT_SIZE, URM_REPLAY);
        menuB = new UrmButton(PAUSE_URM_MENU_POS_X,
                PAUSE_URM_POS_Y, URM_DEFAULT_SIZE,
                URM_DEFAULT_SIZE, URM_MENU);

        volumeButton = new VolumeButton(PAUSE_VOLUME_POS_X,
                PAUSE_VOLUME_POS_Y,
                SLIDER_WIDTH_DEFAULT,
                VOLUME_HEIGHT_DEFAULT);
    }

    @Override
    public void update() {
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();
    }

    @Override
    public void draw(Graphics g, float scale) {
        g.drawImage(pauseImg,
                (int) (pauseX * scale),
                (int) (pauseY * scale),
                (int) (pauseWidth * scale),
                (int) (pauseHeight * scale),
                null);

        musicButton.draw(g, scale);
        sfxButton.draw(g, scale);

        menuB.draw(g, scale);
        replayB.draw(g, scale);
        unpauseB.draw(g, scale);

        volumeButton.draw(g, scale);
    }

    @Override
    public void mouseClicked(MouseEvent e, float scale) {
    }

    @Override
    public void mousePressed(MouseEvent e, float scale) {
        if (isIn(e, musicButton, scale))
            musicButton.setMousePressed(true);
        else if (isIn(e, sfxButton, scale))
            sfxButton.setMousePressed(true);
        else if (isIn(e, menuB, scale))
            menuB.setMousePressed(true);
        else if (isIn(e, replayB, scale))
            replayB.setMousePressed(true);
        else if (isIn(e, unpauseB, scale))
            unpauseB.setMousePressed(true);
        else if (isIn(e, volumeButton, scale))
            volumeButton.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e, float scale) {
        if (isIn(e, musicButton, scale)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton, scale)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, menuB, scale)) {
            if (menuB.isMousePressed()) {
                EnumGameState.state = EnumGameState.MENU;
                EnumPlayState.state = EnumPlayState.PLAYING;
                playing.resetDirBooleans();
            }
        } else if (isIn(e, replayB, scale)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                EnumPlayState.state = EnumPlayState.PLAYING;
                playing.resetDirBooleans();
            }
        } else if (isIn(e, unpauseB, scale)) {
            if (unpauseB.isMousePressed()) {
                EnumPlayState.state = EnumPlayState.PLAYING;
                playing.resetDirBooleans();
            }
        }

        musicButton.resetBool();
        sfxButton.resetBool();
        menuB.resetBool();
        replayB.resetBool();
        unpauseB.resetBool();
        volumeButton.resetBool();
    }

    @Override
    public void mouseDragged(MouseEvent e, float scale) {
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX(), scale);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e, float scale) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton, scale))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton, scale))
            sfxButton.setMouseOver(true);
        else if (isIn(e, menuB, scale))
            menuB.setMouseOver(true);
        else if (isIn(e, replayB, scale))
            replayB.setMouseOver(true);
        else if (isIn(e, unpauseB, scale))
            unpauseB.setMouseOver(true);
        else if (isIn(e, volumeButton, scale))
            volumeButton.setMouseOver(true);
    }

    @Override
    public void keyPressed(KeyEvent e, float scale) {

    }

    @Override
    public void keyReleased(KeyEvent e, float scale) {

    }

}