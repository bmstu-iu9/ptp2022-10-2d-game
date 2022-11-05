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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

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
    private float scale;

    private Line2D.Double shotBox;

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
        objectManager.checkPistolsTouched(playerManager.getPlayer());
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
        this.scale = scale;
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

    public void shotEnemy(int damage) {
        enemyManager.shotEnemy(shotBox, damage);
    }

    public ArrayList<Point2D.Double> canShot(double x, double y, double x1, double y1) {
        ArrayList<Point2D.Double> points = new ArrayList<>();
        double xIndex = (x - lvlOffsetX) * scale;
        double yIndex = (y - lvlOffsetY) * scale;
        shotBox = new Line2D.Double(x, y, x1/scale + lvlOffsetX, y1/scale + lvlOffsetY);

        double t = y1 - yIndex;
        if (t > 0) {
            for (double i = t - (int) t; i <= t; i+=t/150) {
                double pointX = (xIndex + i * (x1 - xIndex) / t)/scale +lvlOffsetX;
                double pointY = (yIndex + i)/scale + lvlOffsetY;
                Point2D.Double point = new Point2D.Double(pointX, pointY);
                points.add(point);
            }
        } else if (t < 0) {
            for (double i = t - (int) t; i >= t; i+=t/150) {
                double pointX = (xIndex + i * (x1 - xIndex) / t)/scale + lvlOffsetX;
                double pointY = (yIndex + i)/scale + lvlOffsetY;
                Point2D.Double point = new Point2D.Double(pointX, pointY);
                points.add(point);
            }
        }
        if ((int) t == 0){
            for (double i = xIndex; i <= x1; i++){
                double pointX = i/scale + lvlOffsetX;
                double pointY = yIndex/scale + lvlOffsetY;
                Point2D.Double point = new Point2D.Double(pointX, pointY);
                points.add(point);
            }
            for (double i = x1; i <= xIndex; i++) {
                double pointX = i/scale + lvlOffsetX;
                double pointY = yIndex/scale + lvlOffsetY;
                Point2D.Double point = new Point2D.Double(pointX, pointY);
                points.add(point);
            }
        }
        return points;
    }
}
