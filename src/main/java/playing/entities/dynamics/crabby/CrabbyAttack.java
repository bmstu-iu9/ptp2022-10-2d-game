package playing.entities.dynamics.crabby;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;
import playing.entities.player.playerModules.PlayerAttack;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.LvlConstants.Entity.CRABBY.CRABBY_ATTACK_RANGE;

public class CrabbyAttack extends CrabbyModule implements PlayingUpdateInterface, PlayingDrawInterface {
    protected boolean attackChecked;
    protected Rectangle2D.Double attackBox;
    private int damage = 10;

    public CrabbyAttack(Crabby crabby) {
        super(crabby);
        initAttackBox();
    }

    private void initAttackBox() {
        Rectangle2D.Double hitBoxTexture = crabby.getHitBoxTexture();
        attackBox = new Rectangle2D.Double(hitBoxTexture.x,
                hitBoxTexture.y,
                hitBoxTexture.width,
                hitBoxTexture.height - 13);
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        drawAttackBox(g, scale, lvlOffsetX, lvlOffsetY);
    }

    protected void drawAttackBox(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.setColor(Color.red);
        g.drawRect((int) ((attackBox.x - lvlOffsetX) * scale),
                (int) ((attackBox.y - lvlOffsetY) * scale),
                (int) (attackBox.width * scale),
                (int) (attackBox.height * scale));
    }

    @Override
    public void update() {
        updateAttackBox();
        checkEnvironment();
    }

    private void checkEnvironment() {
        if (crabby.canSeePlayer(CRABBY_ATTACK_RANGE)) {
            CrabbyAnimation crabbyAnimation = crabby.getCrabbyAnimation();
            if (crabbyAnimation.getAnimationState() != CrabbyAnimation.AnimationState.ATTACK) {
                crabbyAnimation.setAnimationState(CrabbyAnimation.AnimationState.ATTACK);
            } else if (crabbyAnimation.getAnimationState() == CrabbyAnimation.AnimationState.ATTACK) {
                if (crabbyAnimation.getAniIndex() == 0) {
                    attackChecked = false;
                }
                if (crabbyAnimation.getAniIndex() == 3 && !attackChecked) {
                    if (crabby.checkPlayerHit(attackBox)) {
                        attackChecked = true;
                        crabby.attackPlayer(damage);
                    }
                }
            }
        }
    }

    private void updateAttackBox() {
        Rectangle2D.Double hitBoxTexture = crabby.getHitBoxTexture();
//        boolean right = crabby.getCrabbyMove().isRight();
//        boolean left = crabby.getCrabbyMove().isLeft();
//
//        if (right) {
//            attackBox.x = hitBox.x + hitBox.width + 3;
//        } else if (left) {
//            attackBox.x = hitBox.x - hitBox.width - 3;
//        }
        attackBox.x = hitBoxTexture.x;
        attackBox.y = hitBoxTexture.y + 10;
    }
}
