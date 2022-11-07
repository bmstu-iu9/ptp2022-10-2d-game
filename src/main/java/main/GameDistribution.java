package main;

import gamestates.EnumGameState;
import gamestates.Menu;
import gamestates.DebugMode;
import gamestates.Playing;

import java.awt.*;
import java.awt.event.*;

public class GameDistribution implements MainGameInterface, MouseListener, MouseMotionListener, KeyListener{

    private final Game game;
    private final float scale;

    private Menu menu;
    private DebugMode debug_mode;
    private Playing playing;

    public GameDistribution(Game game) {
        this.game = game;
        this.scale = game.getScale();
        initClasses();
    }

    private void initClasses() {
        menu = new Menu();
        debug_mode = new DebugMode();
        playing = new Playing();
    }

    @Override
    public void update() {
        switch (EnumGameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case QUIT:
                game.setGameExit();
            case DEBUG_MODE:
                debug_mode.update();
                break;
            default:
                System.exit(0);
                break;
        }

    }

    @Override
    public void draw(Graphics g) {

        switch (EnumGameState.state) {
            case PLAYING:
                playing.draw(g, scale);
                break;
            case MENU:
                menu.draw(g, scale);
                break;
            case QUIT:
            case DEBUG_MODE:
                debug_mode.draw(g, scale);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (EnumGameState.state) {
            case PLAYING:
                playing.mouseClicked(e, scale);
                break;
            case MENU:
                menu.mouseClicked(e, scale);
            case QUIT:
            case DEBUG_MODE:
                debug_mode.mouseClicked(e, scale);
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (EnumGameState.state) {
            case PLAYING:
                playing.mousePressed(e, scale);
                break;
            case MENU:
                menu.mousePressed(e, scale);
            case QUIT:
            case DEBUG_MODE:
                debug_mode.mousePressed(e, scale);
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (EnumGameState.state) {
            case PLAYING:
                playing.mouseReleased(e, scale);
                break;
            case MENU:
                menu.mouseReleased(e, scale);
            case QUIT:
            case DEBUG_MODE:
                debug_mode.mouseReleased(e, scale);
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (EnumGameState.state) {
            case PLAYING:
                playing.mouseDragged(e, scale);
                break;
            case MENU:
                menu.mouseDragged(e, scale);
            case QUIT:
            case DEBUG_MODE:
                debug_mode.mouseDragged(e, scale);
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (EnumGameState.state) {
            case PLAYING:
                playing.mouseMoved(e, scale);
                break;
            case MENU:
                menu.mouseMoved(e, scale);
            case QUIT:
            case DEBUG_MODE:
                debug_mode.mouseMoved(e, scale);
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (EnumGameState.state) {
            case PLAYING:
                playing.keyPressed(e, scale);
                break;
            case MENU:
            case QUIT:
            case DEBUG_MODE:
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (EnumGameState.state) {
            case PLAYING:
                playing.keyReleased(e, scale);
                break;
            case MENU:
            case QUIT:
            case DEBUG_MODE:
            default:
                break;
        }
    }

    public void windowFocusLost() {
        if (EnumGameState.state == EnumGameState.PLAYING)
            playing.resetHorBooleans();
            playing.resetVertBooleans();
    }
}
