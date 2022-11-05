package playing.entities.player.playerModules;

import gamestates.playingstates.EnumPlayState;
import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static playing.entities.player.playerModules.PlayerAnimation.AnimationState.*;
import static playing.entities.player.playerModules.PlayerAnimation.AnimationType.*;
import static utilz.Constants.GameConstants.ANI_SPEED_ENEMY;
import static utilz.Constants.TextureConstants.Player.*;

public class PlayerAnimation extends PlayerModule implements PlayingUpdateInterface, PlayingDrawInterface {

    private ArrayList<BufferedImage[][]> animations = new ArrayList<>();
    private int shotCount = 0;

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

    public enum AnimationType{
        SWORD,
        PISTOL;

        public static AnimationType animationType = SWORD;
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
        BufferedImage[][] animation= new BufferedImage[7][8];
        for (int j = 0; j < animation.length; j++)
            for (int i = 0; i < animation[j].length; i++)
                animation[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
        animations.add(animation);
        img = LoadSave.GetSpriteAtlas(PLAYER_LOCATION_TEXTURES, PLAYER_WITH_PISTOL_SPRITES_PNG);
        animation = new BufferedImage[7][8];
        for (int j = 0; j < animation.length; j++)
            for (int i = 0; i < animation[j].length; i++)
                animation[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
        animations.add(animation);
        animationType = SWORD;
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
                if (shotCount >= 3) {
                    shotCount = 0;
                    animationType = SWORD;
                }
            }
        }
    }

    private void updateAnimationBox() {
        boolean right = playerModuleManager.getPlayerMove().isRight();
        boolean left = playerModuleManager.getPlayerMove().isLeft();
        BufferedImage bufferedImage = animations.get(animationType.ordinal())[animationState.ordinal()][aniIndex];
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
        BufferedImage bufferedImage = animations.get(animationType.ordinal())[animationState.ordinal()][aniIndex];
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
        if (animationType == PISTOL && state == ATTACK) {
            shotCount++;
        }
        animationState = state;
        aniIndex = 0;
    }

    public AnimationState getAnimationState() {
        return animationState;
    }

    public void setAnimationType(AnimationType type){
        if (dead) {
            return;
        }
        animationType = type;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public int getShotCount() {
        return shotCount;
    }
}
