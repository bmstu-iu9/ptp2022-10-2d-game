package playing.entities.player.playerModules;

import playing.PlayingDrawInterface;
import playing.PlayingMouseListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

public class PlayerAttack extends PlayerModule
        implements PlayingMouseListenerInterface, PlayingUpdateInterface, PlayingDrawInterface {

    protected Rectangle2D.Double attackBox;
    protected Line2D.Double shotBox;

    public PlayerAttack(PlayerModuleManager playerModuleManager,
                        int x, int y,
                        int width, int height) {
        super(playerModuleManager);
        initAttackBox(x, y, width, height);
    }
    protected void initAttackBox(int x, int y ,int width, int height) {
        attackBox = new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void update() {
        updateAttackBox();
    }
    private void updateAttackBox() {
        Rectangle2D.Double hitBox = playerModuleManager.getHitBox();
        boolean right = playerModuleManager.getPlayerMove().isRight();
        boolean left = playerModuleManager.getPlayerMove().isLeft();

        if (right) {
            attackBox.x = hitBox.x + hitBox.width + 3;
        } else if (left) {
            attackBox.x = hitBox.x - hitBox.width - 3;
        }
        attackBox.y = hitBox.y + 10;
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
//        drawAttackBox(g, scale, lvlOffsetX, lvlOffsetY);
    }
    protected void drawAttackBox(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.setColor(Color.red);
        g.drawRect((int) ((attackBox.x - lvlOffsetX) * scale),
                (int) ((attackBox.y - lvlOffsetY) * scale),
                (int) (attackBox.width * scale),
                (int) (attackBox.height * scale));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PlayerAnimation.AnimationType typeAnimation = playerModuleManager.getPlayerAnimation().getAnimationType();
        int damage = 50;
        if (typeAnimation == PlayerAnimation.AnimationType.SWORD) {
            playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.ATTACK);
            playerModuleManager.attackEnemy(attackBox, damage);
        } else if (typeAnimation == PlayerAnimation.AnimationType.PISTOL && playerModuleManager.getPlayerAnimation().getShotCount() < 3) {
            double x = attackBox.x + attackBox.width / 2;
            double y = attackBox.y + attackBox.height / 2;
            double x2 = e.getX();
            double y2 = e.getY();
            if (playerModuleManager.canShot(x, y, x2, y2)){
                playerModuleManager.getPlayerAnimation().setAnimationState(PlayerAnimation.AnimationState.ATTACK);
                playerModuleManager.shotEnemy(damage);
            }
        }
    }

}
