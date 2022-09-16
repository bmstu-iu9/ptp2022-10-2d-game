package playing.entities.player;

import playing.PlayingDrawInterface;
import playing.PlayingKeyListenerInterface;
import playing.PlayingMouseListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.PlayerLevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class PlayerManager implements PlayingUpdateInterface, PlayingDrawInterface,
        PlayingMouseListenerInterface, PlayingKeyListenerInterface {

    private PlayerLevelManager playerLevelManager;

    private Player player;

    public PlayerManager(PlayerLevelManager playerLevelManager) {
        this.playerLevelManager = playerLevelManager;
        initClasses();
    }

    private void initClasses() {
        player = new Player(this, 100, 250);
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        player.draw(g, scale, lvlOffsetX, lvlOffsetY);
    }

    public void resetAll() {
        player = new Player(this, 100, 250);
    }

    public void resetDirBooleans() {
        player.resetDirBooleans();
    }


    public Player getPlayer() {
        return player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.mouseClicked(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }


    public boolean IsPlayerOnFloor(Rectangle2D.Double hitBox) {
        return playerLevelManager.IsPlayerOnFloor(hitBox);
    }

    public boolean CanMoveHere(Rectangle2D.Double hitBox) {
        return playerLevelManager.CanMoveHere(hitBox);
    }

    public int getPlayerX() {
        return (int) player.getX();
    }

    public int getPlayerY() {
        return (int) player.getY();
    }
}