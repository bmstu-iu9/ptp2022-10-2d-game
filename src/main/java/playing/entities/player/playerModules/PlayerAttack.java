package playing.entities.player.playerModules;

import playing.PlayingDrawInterface;
import playing.PlayingMouseListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class PlayerAttack extends PlayerModule
        implements PlayingMouseListenerInterface, PlayingUpdateInterface, PlayingDrawInterface {

    protected Rectangle2D.Double attackBox;

    private boolean shotChecked = true;
    private int aniCount = 0;
    private PlayerAnimation.AnimationState prevAnimState;
    private int prevAniIndex;
    private final int damage = 50;

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
        checkShot();
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
    private void checkShot() {
        if ((playerModuleManager.getPlayerAnimation().getAnimationType() == PlayerAnimation.AnimationType.PISTOL) &&
            (playerModuleManager.getPlayerAnimation().getAnimationState() == PlayerAnimation.AnimationState.ATTACK)){
            shotChecked = false;
            prevAnimState = PlayerAnimation.AnimationState.ATTACK;
            prevAniIndex = playerModuleManager.getPlayerAnimation().getAniIndex();
        }
        if (!shotChecked) {
            if ((prevAnimState != playerModuleManager.getPlayerAnimation().getAnimationState()) ||
                    (prevAniIndex != playerModuleManager.getPlayerAnimation().getAniIndex())) {
                prevAnimState = playerModuleManager.getPlayerAnimation().getAnimationState();
                prevAniIndex = playerModuleManager.getPlayerAnimation().getAniIndex();
                aniCount++;
            }
        }
        if ((!shotChecked) &&(aniCount >= 2)){
            shotChecked = true;
            aniCount=0;
            playerModuleManager.shotEnemy(damage);
        }
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
            }
        }
    }

}
