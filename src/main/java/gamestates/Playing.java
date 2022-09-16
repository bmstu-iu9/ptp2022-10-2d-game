package gamestates;

import gamestates.playingstates.*;
import playing.PlayingGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing implements GamePanelInterface, GamePanelListenerInterface {

    private PlayingGame playingGame;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompleteOverlay levelCompletedOverlay;

    public Playing() {
        initClasses();
    }

    private void initClasses() {
        playingGame = new PlayingGame();
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompleteOverlay(this);
    }

    @Override
    public void update() {
        switch (EnumPlayState.state) {
            case PLAYING:
                playingGame.update();
                break;
            case PAUSED:
                pauseOverlay.update();
                break;
            case GAME_OVER:
                gameOverOverlay.update();
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.update();
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Graphics g, float scale) {
        playingGame.draw(g, scale);
        switch (EnumPlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.draw(g, scale);
                break;
            case GAME_OVER:
                gameOverOverlay.draw(g, scale);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.draw(g, scale);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, float scale) {
        switch (EnumPlayState.state) {
            case PLAYING:
                playingGame.mouseClicked(e);
                break;
            case PAUSED:
                break;
            case GAME_OVER:
                break;
            case LVL_COMPLETED:
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e, float scale) {
        switch (EnumPlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mousePressed(e, scale);
                break;
            case GAME_OVER:
                gameOverOverlay.mousePressed(e, scale);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.mousePressed(e, scale);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, float scale) {
        switch (EnumPlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mouseReleased(e, scale);
                break;
            case GAME_OVER:
                gameOverOverlay.mouseReleased(e, scale);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.mouseReleased(e, scale);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e, float scale) {
        switch (EnumPlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mouseDragged(e, scale);
                break;
            case GAME_OVER:
                break;
            case LVL_COMPLETED:
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e, float scale) {
        switch (EnumPlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mouseMoved(e, scale);
                break;
            case GAME_OVER:
                gameOverOverlay.mouseMoved(e, scale);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.mouseMoved(e, scale);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e, float scale) {
        switch (EnumPlayState.state) {
            case PLAYING:
                playingGame.keyPressed(e);
                break;
            case PAUSED:
                pauseOverlay.keyPressed(e, scale);
                break;
            case GAME_OVER:
                gameOverOverlay.keyPressed(e, scale);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.keyPressed(e, scale);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e, float scale) {
        switch (EnumPlayState.state) {
            case PLAYING:
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    EnumPlayState.state = EnumPlayState.PAUSED;
                } else {
                    playingGame.keyReleased(e);
                }
                break;
            case PAUSED:
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    EnumPlayState.state = EnumPlayState.PLAYING;
                    playingGame.resetDirBooleans();
                } else {
                    pauseOverlay.keyReleased(e, scale);
                }
                break;
            case GAME_OVER:
                gameOverOverlay.keyReleased(e, scale);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.keyReleased(e, scale);
                break;
            default:
                break;
        }
    }

    public void resetAll() {
        playingGame.resetAll();
    }

    public void resetDirBooleans() {
        playingGame.resetDirBooleans();
    }

    public void nextLevel() {
        playingGame.nextLevel();
    }
}
