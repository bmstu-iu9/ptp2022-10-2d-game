package playing.entities.dynamics.crabby;

import playing.PlayingUpdateInterface;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.GameConstants.GRAVITY;
import static utilz.Constants.LvlConstants.Entity.CRABBY.CRABBY_RANGE;

public class CrabbyMove extends CrabbyModule implements PlayingUpdateInterface {
    private boolean moving;
    private boolean left, right;
    private boolean onFloor;

    private final float speedWalk = 0.35f;
    private float xSpeed = 0;
    private float ySpeed = 0;

    public CrabbyMove(Crabby crabby) {
        super(crabby);
    }

    @Override
    public void update() {
        checkEnvironment();
        updatePos();
    }

    private void checkEnvironment() {
        if (crabby.canSeePlayer(CRABBY_RANGE)) {
            turnTowardsPlayer();
        } else if (!right && !left) {
            left = true;
        }
    }

    private void turnTowardsPlayer() {
        right = false;
        left = false;
        if (crabby.wherePlayerX() > 0) {
            right = true;
        } else if (crabby.wherePlayerX() < 0) {
            left = true;
        }
    }

    private void updatePos() {

        if (left) {
            xSpeed -= speedWalk;
        }
        if (right) {
            xSpeed += speedWalk;
        }

        if (onFloor) {
            if (!crabby.isPlayerOnFloor())
                onFloor = false;
        }

        if (!onFloor) {
            Rectangle2D.Double oldHitBox = crabby.getHitBox();
            Rectangle2D.Double newHitBox = new Rectangle2D.Double(
                    oldHitBox.x, oldHitBox.y + ySpeed,
                    oldHitBox.width, oldHitBox.height);

            if (crabby.canMoveHere(newHitBox)) {
                updateYPos(ySpeed);

//                if (ySpeed > 0) {
//                    crabby.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.FALLING);
//                } else if (ySpeed < 0) {
//                    crabby.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.JUMP);
//                } else {
//                    crabby.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.IDLE);
//                }
                ySpeed += GRAVITY;
            } else {
                if (ySpeed > 0) {
                    onFloor = true;
//                    crabby.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.IDLE);
                }
                ySpeed = 0;
            }
        }

        Rectangle2D.Double oldHitBox = crabby.getHitBox();
        Rectangle2D.Double newHitBox = new Rectangle2D.Double(
                oldHitBox.x + xSpeed, oldHitBox.y,
                oldHitBox.width, oldHitBox.height);
        if (crabby.canMoveFloor(newHitBox)) {
            updateXPos(xSpeed);
        } else {
            changeWalkDir();
        }
        if (xSpeed == 0) {
            if (crabby.getCrabbyAnimation().getAnimationState() == CrabbyAnimation.AnimationState.RUNNING) {
                crabby.getCrabbyAnimation().setAnimationState(CrabbyAnimation.AnimationState.IDLE);
            }
        } else {
            if (crabby.getCrabbyAnimation().getAnimationState() == CrabbyAnimation.AnimationState.IDLE) {
                crabby.getCrabbyAnimation().setAnimationState(CrabbyAnimation.AnimationState.RUNNING);
            }
        }
        xSpeed = 0;
        moving = true;
    }

    private void changeWalkDir() {
        if (left) {
            left = false;
            right = true;
        } else if (right) {
            left = true;
            right = false;
        } else {
            left = true;
        }
    }

    private void updateXPos(double xSpeed) {
        crabby.setX(crabby.getX() + xSpeed);
    }

    private void updateYPos(double ySpeed) {
        crabby.setY(crabby.getY() + ySpeed);
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
}