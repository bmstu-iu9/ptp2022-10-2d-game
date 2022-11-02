package playing.entities.player;

import playing.PlayingDrawInterface;
import playing.PlayingKeyListenerInterface;
import playing.PlayingMouseListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.playerModules.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class PlayerModuleManager implements PlayingUpdateInterface, PlayingDrawInterface,
        PlayingMouseListenerInterface, PlayingKeyListenerInterface {

    private Player player;

    private PlayerInPut playerInPut;
    private PlayerOutPut playerOutPut;
    private PlayerListener playerListener;

    private PlayerMove playerMove;
    private PlayerAttack playerAttack;
    private PlayerStatusBar playerStatusBar;
    private PlayerAnimation playerAnimation;

    public PlayerModuleManager(Player player) {
        this.player = player;
        initClasses();
    }

    private void initClasses() {
        playerInPut = new PlayerInPut(this);
        playerOutPut = new PlayerOutPut(this);
        playerListener = new PlayerListener(this);


        playerAttack = new PlayerAttack(this,
                (int) (player.getHitBox().x + player.getHitBox().width) + 3,
                (int) player.getHitBox().y, 20 ,20);
        playerMove = new PlayerMove(this);
        playerAnimation = new PlayerAnimation(this);
        playerStatusBar = new PlayerStatusBar(this);
    }

    public void updateHitBoxX(double x) {
        player.setX(player.getX() + x);
    }

    public void updateHitBoxY(double y) {
        player.setY(player.getY() + y);
    }

    public int getPlayerX() {
        return (int) player.getX();
    }

    public int getPlayerY() {
        return (int) player.getY();
    }

    public void drawHitBox(Graphics g, float scale, int LvlOffsetX, int LvlOffsetY) {
        player.drawHitBox(g, scale, LvlOffsetX, LvlOffsetY);
    }

    @Override
    public void update() {
        playerMove.update();
        playerAttack.update();
        playerAnimation.update();
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        player.drawHitBox(g, scale, lvlOffsetX, lvlOffsetY);
        playerAttack.draw(g, scale, lvlOffsetX, lvlOffsetY);
        playerAnimation.draw(g, scale, lvlOffsetX, lvlOffsetY);
        playerStatusBar.draw(g, scale, lvlOffsetX, lvlOffsetY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        playerListener.mouseClicked(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        playerListener.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerListener.keyReleased(e);
    }

    public PlayerInPut getPlayerInPut() {
        return playerInPut;
    }

    public PlayerOutPut getPlayerOutPut() {
        return playerOutPut;
    }

    public PlayerListener getPlayerListener() {
        return playerListener;
    }


    public PlayerMove getPlayerMove() {
        return playerMove;
    }

    public PlayerAttack getPlayerAttack() {
        return playerAttack;
    }

    public PlayerAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    public void resetAll() {

    }

    public void resetDirBooleans() {
        playerMove.resetDirBooleans();
    }

    public boolean IsPlayerOnFloor() {
        return player.IsPlayerOnFloor(player.getHitBox());
    }

    public boolean CanMoveHere(Rectangle2D.Double hitBox) {
        return player.CanMoveHere(hitBox);
    }

    public void attackPlayer(int damage) {
        playerStatusBar.decreaseHealth(damage);
    }

    public Rectangle2D.Double getHitBox() {
        return player.getHitBox();
    }

    public void attackEnemy(Rectangle2D.Double attackBox, int damage) {
        player.attackEnemy(attackBox, damage);
    }

    public void kill() {
        playerAnimation.setAnimationState(PlayerAnimation.AnimationState.DEAD);
    }

    public void addCoin(int v) {
        playerStatusBar.addCoin(v);
    }

    public int getCoins() {
        return playerStatusBar.getCoins();
    }
}
