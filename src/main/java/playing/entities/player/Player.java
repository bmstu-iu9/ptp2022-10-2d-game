package playing.entities.player;

import playing.PlayingDrawInterface;
import playing.PlayingKeyListenerInterface;
import playing.PlayingMouseListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.Entity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Player extends Entity implements PlayingUpdateInterface, PlayingDrawInterface,
        PlayingMouseListenerInterface, PlayingKeyListenerInterface {

    private PlayerManager playerManager;

    private PlayerModuleManager playerModuleManager;

    public Player(PlayerManager playerManager, int x, int y) {
        super(x, y, 20, 27);
        this.playerManager = playerManager;
        initClasses();
    }

    private void initClasses() {
        playerModuleManager = new PlayerModuleManager(this);
    }

    @Override
    public void update() {
        playerModuleManager.update();
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        playerModuleManager.draw(g, scale, lvlOffsetX, lvlOffsetY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        playerModuleManager.mouseClicked(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        playerModuleManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerModuleManager.keyReleased(e);
    }

    public void resetAll() {
        playerModuleManager.resetAll();
    }

    public void resetDirBooleans() {
        playerModuleManager.resetDirBooleans();
    }

    public void kill() {
        playerModuleManager.kill();
    }
    public boolean IsPlayerOnFloor(Rectangle2D.Double hitBox) {
        return playerManager.IsPlayerOnFloor(hitBox);
    }
    public boolean CanMoveHere(Rectangle2D.Double hitBox) {
        return playerManager.CanMoveHere(hitBox);
    }

    public void attackPlayer(int damage) {
        playerModuleManager.attackPlayer(damage);
    }

    public void attackEnemy(Rectangle2D.Double attackBox, int damage) {
        playerManager.attackEnemy(attackBox, damage);
    }

    public void addCoin(int v) {
        playerModuleManager.addCoin(v);
    }

    public int getCoins() {
        return playerModuleManager.getCoins();
    }
}
