package playing.entities.dynamics;

import playing.PlayingDrawInterface;
import playing.PlayingGame;
import playing.PlayingUpdateInterface;
import playing.entities.EntityLevelManager;
import playing.entities.dynamics.crabby.Crabby;
import playing.levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EnemyManager implements PlayingUpdateInterface, PlayingDrawInterface {
    private EntityLevelManager entityLevelManager;

    private ArrayList<Crabby> crabbies;

    public EnemyManager(EntityLevelManager entityLevelManager, Level level) {
        this.entityLevelManager = entityLevelManager;
        loadObjects(level);
    }

    private void loadObjects(Level level) {
        crabbies = level.getCrabbies();
        for (Crabby crabby : crabbies) {
            crabby.setEnemyManager(this);
        }
    }

    @Override
    public void update() {
        updateCrabbies();
    }

    private void updateCrabbies() {
        for (Crabby crabby : crabbies) {
            if (crabby.isActive()) {
                crabby.update();
            }
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        drawCrabbies(g, scale, lvlOffsetX, lvlOffsetY);
    }

    private void drawCrabbies(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (Crabby crabby : crabbies) {
            if (crabby.isActive()) {
                crabby.draw(g, scale, lvlOffsetX, lvlOffsetY);
            }
        }
    }

    public void attackEnemy(Rectangle2D.Double attackBox, int damage) {
        for (Crabby crabby : crabbies) {
            if (crabby.isActive()) {
                if (attackBox.intersects(crabby.getHitBox())) {
                    crabby.attackEnemy(damage);
                }
            }
        }
    }

    public void resetAll() {

    }

    public boolean IsPlayerOnFloor(Rectangle2D.Double hitBox) {
        return entityLevelManager.IsPlayerOnFloor(hitBox);
    }

    public boolean CanMoveHere(Rectangle2D.Double hitBox) {
        return entityLevelManager.CanMoveHere(hitBox);
    }


    public boolean canMoveFloor(Rectangle2D.Double hitBox) {
        return entityLevelManager.canMoveFloor(hitBox);
    }

    public boolean canSeePlayer(Rectangle2D.Double hitBox, float range) {
        return entityLevelManager.canSeePlayer(hitBox, range);
    }
    public boolean isPlayerInRange(Rectangle2D.Double hitBox, float range) {
        return entityLevelManager.isPlayerInRange(hitBox, range);
    }

    public int wherePlayerX(Rectangle2D.Double hitBox) {
        return entityLevelManager.wherePlayerX(hitBox);
    }

    public void attackPlayer(int damage) {
        entityLevelManager.attackPlayer(damage);
    }

    public boolean checkPlayerHit(Rectangle2D.Double attackBox) {
        return entityLevelManager.checkPlayerHit(attackBox);
    }
}
