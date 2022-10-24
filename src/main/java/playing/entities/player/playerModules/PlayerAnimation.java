package playing.entities.player.playerModules;

import gamestates.playingstates.EnumPlayState;
import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static playing.entities.player.playerModules.PlayerAnimation.AnimationState.*;
import static utilz.Constants.GameConstants.ANI_SPEED_ENEMY;
import static utilz.Constants.TextureConstants.Player.PLAYER_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Player.PLAYER_SPRITES_PNG;

public class PlayerAnimation extends PlayerModule implements PlayingUpdateInterface, PlayingDrawInterface {

    private BufferedImage[][] animations;

    public enum AnimationState {
        IDLE,
        RUNNING,
        JUMP,
        FALLING,
        ATTACK,
        HIT,
        DEAD;

        public static AnimationState animationState = IDLE;
    }

    protected Rectangle2D.Double animationBox;
    private int aniTick, aniIndex;
    private int flipW = 1;
    private int flipX = 0;

    private boolean dead;


    public PlayerAnimation(PlayerModuleManager playerModuleManager) {
        super(playerModuleManager);
        loadImages();
        initAnimationBox();
    }

    private void initAnimationBox() {
        animationBox = new Rectangle2D.Double();
    }

    private void loadImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(PLAYER_LOCATION_TEXTURES, PLAYER_SPRITES_PNG);
        animations = new BufferedImage[7][8];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
    }

    @Override
    public void update() {
        updateAnimationTick();
        updateAnimationBox();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED_ENEMY) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount()) {
                if (animationState == DEAD) {
                    EnumPlayState.state = EnumPlayState.GAME_OVER;
                }
                animationState = IDLE;
                aniIndex = 0;
            }
        }
    }

    private void updateAnimationBox() {
        boolean right = playerModuleManager.getPlayerMove().isRight();
        boolean left = playerModuleManager.getPlayerMove().isLeft();
        BufferedImage bufferedImage = animations[animationState.ordinal()][aniIndex];
        if (right) {
            flipW = 1;
            flipX = 0;
        } else if (left) {
            flipW = -1;
            flipX = bufferedImage.getWidth();
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        Rectangle2D.Double hitBox = playerModuleManager.getHitBox();
        BufferedImage bufferedImage = animations[animationState.ordinal()][aniIndex];
        g.drawImage(bufferedImage,
                (int) ((hitBox.x - 21 - lvlOffsetX + flipX) * scale),
                (int) ((hitBox.y - 4 - lvlOffsetY) * scale),
                (int) (bufferedImage.getWidth() * flipW * scale),
                (int) (bufferedImage.getHeight() * scale),
                null);
    }

    private static int GetSpriteAmount() {

        switch (animationState) {
            case DEAD:
                return 8;
            case RUNNING:
                return 6;
            case IDLE:
                return 5;
            case HIT:
                return 4;
            case JUMP:
            case ATTACK:
                return 3;
            case FALLING:
            default:
                return 1;
        }

    }

    public void setAnimationState(AnimationState state) {
        if (dead) {
            return;
        }
        if (state == DEAD) {
            dead = true;
        }
        animationState = state;
        aniIndex = 0;
    }

    public AnimationState getAnimationState() {
        return animationState;
    }
}
