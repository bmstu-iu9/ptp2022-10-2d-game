package playing.entities.player;

import playing.PlayingDrawInterface;
import playing.PlayingKeyListenerInterface;
import playing.PlayingMouseListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.EntityLevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class PlayerManager implements PlayingUpdateInterface, PlayingDrawInterface,
        PlayingMouseListenerInterface, PlayingKeyListenerInterface {

    private EntityLevelManager entityLevelManager;

    private Player player;

    public PlayerManager(EntityLevelManager entityLevelManager) {
        this.entityLevelManager = entityLevelManager;
        initClasses();
    }

    private void initClasses() {
        player = new Player(this, 100, 100);
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
        player = new Player(this, 100, 100);
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
        return entityLevelManager.IsPlayerOnFloor(hitBox);
    }

    public boolean CanMoveHere(Rectangle2D.Double hitBox) {
        return entityLevelManager.CanMoveHere(hitBox);
    }

    public int getPlayerX() {
        return (int) player.getX();
    }

    public int getPlayerY() {
        return (int) player.getY();
    }

    public Rectangle2D.Double getPlayerHitBox() {
        return player.getHitBox();
    }

    public void attackPlayer(int damage) {
        player.attackPlayer(damage);
    }

    public void attackEnemy(Rectangle2D.Double attackBox, int damage) {
        entityLevelManager.attackEnemy(attackBox, damage);
    }

    public void setSpawnPlayer(int x, int y) {
        player.setX(x);
        player.setY(y);
    }
}
