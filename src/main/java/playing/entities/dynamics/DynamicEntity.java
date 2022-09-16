package playing.entities.dynamics;

import playing.entities.Entity;

import java.awt.geom.Rectangle2D;

public abstract class DynamicEntity extends Entity {

    private EnemyManager enemyManager;

    public DynamicEntity(double x, double y) {
        super(x, y);
    }

    public DynamicEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public boolean isPlayerOnFloor() {
        return enemyManager.IsPlayerOnFloor(getHitBox());
    }

    public boolean canMoveHere(Rectangle2D.Double hitBox) {
        return enemyManager.CanMoveHere(hitBox);
    }

    public boolean canMoveFloor(Rectangle2D.Double hitBox) {
        return enemyManager.canMoveFloor(hitBox);
    }

    public boolean canSeePlayer(float range) {
        return enemyManager.canSeePlayer(getHitBox(), range);
    }
    public boolean isPlayerInRange(float range) {
        return enemyManager.isPlayerInRange(getHitBox(), range);
    }

    public int wherePlayerX() {
        return enemyManager.wherePlayerX(getHitBox());
    }

    public void attackPlayer(int damage) {
        enemyManager.attackPlayer(damage);
    }


    public boolean checkPlayerHit(Rectangle2D.Double attackBox) {
        return enemyManager.checkPlayerHit(attackBox);
    }
}