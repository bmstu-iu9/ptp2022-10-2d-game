package playing;

import gamestates.GamePanelInterface;
import playing.entities.EntityLevelManager;
import playing.entities.dynamics.EnemyManager;
import playing.entities.player.PlayerManager;
import playing.entities.statics.ObjectManager;
import playing.levels.Level;
import playing.levels.LevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.GameWindowConstants.*;

public class PlayingGame implements GamePanelInterface,
        PlayingMouseListenerInterface, PlayingKeyListenerInterface{

    private LevelManager levelManager;
    private PlayerManager playerManager;
    private Level currentLevel;

    private EnemyManager enemyManager;

    private ObjectManager objectManager;
    private EntityLevelManager entityLevelManager;

    private int lvlOffsetX, lvlOffsetY;
    private int maxLvlOffsetX, maxLvlOffsetY;


    public PlayingGame() {
        initClasses();
    }

    private void initClasses() {
        entityLevelManager = new EntityLevelManager(this);
        levelManager = new LevelManager(entityLevelManager);
        currentLevel = levelManager.getCurrentLevel();
        playerManager = new PlayerManager(entityLevelManager);

        initCurrentLevelManager();

        calcLvlOffset();
    }

    private void initCurrentLevelManager() {
        enemyManager = new EnemyManager(entityLevelManager, currentLevel);
        objectManager = new ObjectManager(entityLevelManager, currentLevel);
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getLvlOffsetX();
        maxLvlOffsetY = levelManager.getLvlOffsetY();
    }

    public void nextLevel() {
        levelManager.nextLevel();
        currentLevel = levelManager.getCurrentLevel();
        playerManager = new PlayerManager(entityLevelManager);
        playerManager.setSpawnPlayer(100, 100);

        initCurrentLevelManager();
        calcLvlOffset();
    }

    @Override
    public void update() {
        levelManager.update();
        playerManager.update();
        enemyManager.update();
        objectManager.update();
        checkCloseToBorder();
        objectManager.checkSpikesTouched(playerManager.getPlayer());
        objectManager.checkPortalTouched(playerManager.getPlayer());
        objectManager.checkCoinsTouched(playerManager.getPlayer());
    }

    private void checkCloseToBorder() {
        int playerX = playerManager.getPlayerX();
        int diffX = playerX - lvlOffsetX;

        if (diffX > RIGHT_BORDER) {
            lvlOffsetX += diffX - RIGHT_BORDER;
        } else if (diffX < LEFT_BORDER) {
            lvlOffsetX += diffX - LEFT_BORDER;
        }

        if (lvlOffsetX > maxLvlOffsetX) {
            lvlOffsetX = maxLvlOffsetX;
        } else if( lvlOffsetX < 0) {
            lvlOffsetX = 0;
        }


        int playerY = playerManager.getPlayerY();
        int diffY = playerY - lvlOffsetY;

        if (diffY > DOWN_BORDER) {
            lvlOffsetY += diffY - DOWN_BORDER;
        } else if (diffY < TOP_BORDER) {
            lvlOffsetY += diffY - TOP_BORDER;
        }

        if (lvlOffsetY > maxLvlOffsetY) {
            lvlOffsetY = maxLvlOffsetY;
        } else if( lvlOffsetY < 0) {
            lvlOffsetY = 0;
        }

    }

    @Override
    public void draw(Graphics g, float scale) {
        levelManager.draw(g, scale, lvlOffsetX, lvlOffsetY);
        playerManager.draw(g, scale, lvlOffsetX, lvlOffsetY);
        enemyManager.draw(g, scale, lvlOffsetX, lvlOffsetY);
        objectManager.draw(g, scale, lvlOffsetX, lvlOffsetY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        playerManager.mouseClicked(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        playerManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerManager.keyReleased(e);
    }

    public void resetAll() {
        playerManager.resetAll();
        initCurrentLevelManager();
    }

    public void resetDirBooleans() {
        playerManager.resetDirBooleans();
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public Rectangle2D.Double getPlayerHitBox() {
        return playerManager.getPlayerHitBox();
    }

    public void attackPlayer(int damage) {
        playerManager.attackPlayer(damage);
    }

    public void attackEnemy(Rectangle2D.Double attackBox, int damage) {
        enemyManager.attackEnemy(attackBox, damage);
    }
}
