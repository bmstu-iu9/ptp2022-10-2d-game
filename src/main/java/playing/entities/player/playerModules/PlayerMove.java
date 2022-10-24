package playing.entities.player.playerModules;

import playing.PlayingDrawInterface;
import playing.PlayingKeyListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.GameConstants.GRAVITY;

public class PlayerMove extends PlayerModule implements PlayingKeyListenerInterface, PlayingUpdateInterface, PlayingDrawInterface {

    private boolean moving;
    private boolean left, right, jump;
    private boolean inAir, inWater;
    private boolean onFloor;

    private final float speedWalk = 1.f;
    private final float speedJump = -2.6f;
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
        }

        if (left) {
            xSpeed -= speedWalk;
        }
        if (right) {
            xSpeed += speedWalk;
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
                setLeft(true);
                break;
            case KeyEvent.VK_D:
                setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                setJump(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                setLeft(false);
                break;
            case KeyEvent.VK_D:
                setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                setJump(false);
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

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isJump() {
        return jump;
    }

    public void resetDirBooleans() {
        setJump(false);
        setRight(false);
        setLeft(false);
    }

}
