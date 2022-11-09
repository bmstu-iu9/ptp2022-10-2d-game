package playing.entities.player.playerModules;

import playing.PlayingDrawInterface;
import playing.PlayingMouseListenerInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameConstants.ANI_SPEED_ENEMY;
import static utilz.Constants.TextureConstants.Entity.ENTITY_LOCATION_TEXTURES;

public class PlayerAttack extends PlayerModule
        implements PlayingMouseListenerInterface, PlayingUpdateInterface, PlayingDrawInterface {

    protected Rectangle2D.Double attackBox;

    private boolean shotChecked = true;
    private boolean canDrawPlaceShot = false;
    private final int damage = 50;
    private Point2D.Double lastShot;
    private int aniTick, aniIndex;
    private BufferedImage placeShot;
    private boolean shotHit;


    public PlayerAttack(PlayerModuleManager playerModuleManager,
                        int x, int y,
                        int width, int height) {
        super(playerModuleManager);
        initAttackBox(x, y, width, height);
        loadImages();
    }
    protected void initAttackBox(int x, int y ,int width, int height) {
        attackBox = new Rectangle2D.Double(x, y, width, height);
    }
    private void loadImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, "fire.png");
        placeShot = img.getSubimage(0, 0, 1600, 1600);
    }

    @Override
    public void update() {
        updateAttackBox();
        checkShot();
        updateAnimationTick();
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
        if ((shotChecked) && (playerModuleManager.getPlayerAnimation().getAnimationType() == PlayerAnimation.AnimationType.PISTOL) &&
                (playerModuleManager.getPlayerAnimation().getAnimationState() == PlayerAnimation.AnimationState.ATTACK)){
            shotChecked = false;
            aniIndex = 10;
        }
        if ((!shotChecked) && (aniIndex >= 14)){
            shotChecked = true;
            shotHit = playerModuleManager.shotEnemy(damage);
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        drawPlaceShot(g, scale, lvlOffsetX, lvlOffsetY);
//        drawAttackBox(g, scale, lvlOffsetX, lvlOffsetY);
    }
    protected void drawAttackBox(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.setColor(Color.red);
        g.drawRect((int) ((attackBox.x - lvlOffsetX) * scale),
                (int) ((attackBox.y - lvlOffsetY) * scale),
                (int) (attackBox.width * scale),
                (int) (attackBox.height * scale));
    }

    private void drawPlaceShot(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        if (lastShot != null && shotChecked) {
            if (!canDrawPlaceShot) {
                aniIndex = 0;
                canDrawPlaceShot = true;
            } if ((aniIndex < 3) && (!shotHit)) {
                    g.drawImage(placeShot,
                            (int) lastShot.x-8,
                            (int) lastShot.y-8,
                            16,
                            16,
                            null);

                } else {
                    lastShot = null;
                    canDrawPlaceShot = false;
                }
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED_ENEMY) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex > 1000){
                aniIndex =0;
            }
        }
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
                lastShot = new Point2D.Double(x2, y2);
            }
        }
    }
}
