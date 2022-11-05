package playing.entities;

import main.Game;
import playing.PlayingGame;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static utilz.Constants.GameWindowConstants.TILE_SIZE_DEFAULT;

public class EntityLevelManager {

    private PlayingGame playingGame;

    public EntityLevelManager(PlayingGame playingGame) {
        this.playingGame = playingGame;
    }

    public boolean IsPlayerOnFloor(Rectangle2D.Double hitBox) {
        int[][] lvlData = playingGame.getLevelManager().getLvlData();
        if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) {
            if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public boolean CanMoveHere(Rectangle2D.Double hitBox) {
        int[][] lvlData = playingGame.getLevelManager().getLvlData();
        if (!IsSolid(hitBox.x, hitBox.y, lvlData)) {
            if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height, lvlData)) {
                if (!IsSolid(hitBox.x + hitBox.width, hitBox.y, lvlData)) {
                    if (!IsSolid(hitBox.x, hitBox.y + hitBox.height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canMoveFloor(Rectangle2D.Double hitBox) {
        int[][] lvlData = playingGame.getLevelManager().getLvlData();
        return CanMoveHere(hitBox) && IsSolid(hitBox.x,
                hitBox.y + hitBox.height + 1, lvlData);
    }

    public boolean canSeePlayer(Rectangle2D.Double hitBox, float range) {
        int[][] lvlData = playingGame.getLevelManager().getLvlData();
        Rectangle2D.Double playerHitBox = playingGame.getPlayerHitBox();
        int playerTileY = (int) (playerHitBox.y / TILE_SIZE_DEFAULT);
        int enemyTileY = (int) (hitBox.y / TILE_SIZE_DEFAULT);

        if (playerTileY == enemyTileY) {
            if (isPlayerInRange(hitBox, range)) {
                if (IsSightClear(lvlData, hitBox, playerHitBox, enemyTileY))
                    return true;
            }
        }

        return false;
    }
    public boolean isPlayerInRange(Rectangle2D.Double hitBox, float range) {
        Rectangle2D.Double playerHitBox = playingGame.getPlayerHitBox();
        int absValue = (int) Math.abs(playerHitBox.x - hitBox.x);
        return absValue <= range;
    }

    public int wherePlayerX(Rectangle2D.Double hitBox) {
        Rectangle2D.Double playerHitBox = playingGame.getPlayerHitBox();
        return (int) (playerHitBox.x - hitBox.x);
    }

    public void attackPlayer(int damage) {
        playingGame.attackPlayer(damage);
    }

    public boolean checkPlayerHit(Rectangle2D.Double attackBox) {
        Rectangle2D.Double playerHitBox = playingGame.getPlayerHitBox();
        return attackBox.intersects(playerHitBox);
    }

    public void attackEnemy(Rectangle2D.Double attackBox, int damage) {
        playingGame.attackEnemy(attackBox,damage);
    }

    private boolean IsSightClear(int[][] lvlData, Rectangle2D.Double firstHitbox, Rectangle2D.Double secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / TILE_SIZE_DEFAULT);
        int secondXTile = (int) (secondHitbox.x  / TILE_SIZE_DEFAULT);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
    }

    private boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        if (IsAllTilesClear(xStart, xEnd, y, lvlData))
            for (int i = 0; i < xEnd - xStart; i++) {
                if (!IsTileSolid(xStart + i, y + 1, lvlData))
                    return false;
            }
        return true;
    }

    private boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++)
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
        return true;
    }

    private boolean IsSolid(double x, double y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * TILE_SIZE_DEFAULT;
        int maxHeight = lvlData.length * TILE_SIZE_DEFAULT;

        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= maxHeight)
            return true;

        double xIndex = x / TILE_SIZE_DEFAULT;
        double yIndex = y / TILE_SIZE_DEFAULT;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    private boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public boolean canShot(double x, double y, double x1, double y1) {
        int[][] lvlData = playingGame.getLevelManager().getLvlData();
        ArrayList<Point2D.Double> points = playingGame.canShot(x, y, x1, y1);
        for (Point2D.Double point : points) {
            if (IsSolid(point.x, point.y, lvlData)){
                return false;
            }
        }
        return true;
    }

    public void shotEnemy(int damage) {
        playingGame.shotEnemy(damage);
    }
}
