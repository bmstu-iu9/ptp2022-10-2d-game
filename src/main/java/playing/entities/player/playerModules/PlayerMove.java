package playing.entities.player.playerModules;

import playing.PlayingDrawInterface;
import playing.PlayingKeyListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.GameConstants.GRAVITY;
import static utilz.Constants.GameConstants.TEMP_GRAVITY;

public class PlayerMove extends PlayerModule implements PlayingKeyListenerInterface, PlayingUpdateInterface, PlayingDrawInterface {

    private boolean moving;
    private boolean left, right, jump, fall;
    private boolean inAir, inWater;
    private boolean onFloor;

    private float speedJump = -2.65f + GRAVITY;

    private final float speedWalk = 1.f;
    private float speedInAir;
    private float speedInWater;
    private float ySpeed = 0;
    private float xSpeed = 0;

    public PlayerMove(PlayerModuleManager playerModuleManager) {
        super(playerModuleManager);
    }

    @Override
    public void update() {
        updatePos();
    }

    private void updatePos() {
//        playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.IDLE);

        if (jump) {
            if(onFloor) {
                onFloor = false;
                ySpeed = speedJump;
            }
            if(TEMP_GRAVITY == 0.0f) {
                GRAVITY = TEMP_GRAVITY;
            }
        }

        if (left) {
            xSpeed -= speedWalk;
        }
        if (right) {
            xSpeed += speedWalk;
        }

        if(fall) {
            ySpeed = 1.5f;
            GRAVITY = 0.035f;
        }

        if (onFloor) {
            if (!playerModuleManager.IsPlayerOnFloor())
                onFloor = false;
        }

        if (!onFloor) {
            Rectangle2D.Double oldHitBox = playerModuleManager.getHitBox();
            Rectangle2D.Double newHitBox = new Rectangle2D.Double(
                    oldHitBox.x, oldHitBox.y + ySpeed,
                    oldHitBox.width, oldHitBox.height);

            if (playerModuleManager.CanMoveHere(newHitBox)) {
                updateYPos(ySpeed);

                if (ySpeed > 0) {
                    playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.FALLING);
                } else if (ySpeed < 0) {
                    playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.JUMP);
                } else {
                    playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.IDLE);
                }
                ySpeed += GRAVITY;
            } else {
                if (ySpeed > 0) {
                    onFloor = true;
                    playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.IDLE);
                }
                ySpeed = 0;
            }
        }

        Rectangle2D.Double oldHitBox = playerModuleManager.getHitBox();
        Rectangle2D.Double newHitBox = new Rectangle2D.Double(
                oldHitBox.x + xSpeed, oldHitBox.y,
                oldHitBox.width, oldHitBox.height);
        if (playerModuleManager.CanMoveHere(newHitBox)) {
            updateXPos(xSpeed);
        }
        if (xSpeed == 0) {
            if (playerModuleManager.getPlayerAnimation().getAnimationState() == PlayerAnimation.AnimationState.RUNNING) {
                playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.IDLE);
            }
        } else {
            if (playerModuleManager.getPlayerAnimation().getAnimationState() == PlayerAnimation.AnimationState.IDLE) {
                playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.RUNNING);
            }
        }
        xSpeed = 0;
        moving = true;
    }

    private void updateXPos(double xSpeed) {
        playerModuleManager.updateHitBoxX(xSpeed);
    }

    private void updateYPos(double ySpeed) {
        playerModuleManager.updateHitBoxY(ySpeed);
    }

    @Override
    public void draw(Graphics g, float scale, int LvlOffsetX, int LvlOffsetY) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                setLeft(true);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                setRight(true);
                break;
            case KeyEvent.VK_W:
                setJump(true);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
                setJump(true);
                break;
            case KeyEvent.VK_S:
                setFall(true);
                break;
            case KeyEvent.VK_DOWN:
                setFall(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                setLeft(false);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                setRight(false);
                break;
            case KeyEvent.VK_W:
                setJump(false);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
                setJump(false);
                break;
            case KeyEvent.VK_S:
                setFall(false);
                break;
            case KeyEvent.VK_DOWN:
                setFall(false);
                break;
        }
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setFall(boolean fall) {
        this.fall = fall;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isJump() {
        return jump;
    }

    public void resetVertBooleans() {
        setJump(false);
        setFall(false);
    }

    public void resetHorBooleans() {
        setRight(false);
        setLeft(false);
    }

}
