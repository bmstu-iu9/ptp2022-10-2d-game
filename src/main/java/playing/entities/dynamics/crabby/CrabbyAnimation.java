package playing.entities.dynamics.crabby;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static playing.entities.dynamics.crabby.CrabbyAnimation.AnimationState.*;
import static utilz.Constants.GameConstants.ANI_SPEED_ENEMY;
import static utilz.Constants.TextureConstants.Entity.CRABBY_SPRITE_PNG;
import static utilz.Constants.TextureConstants.Entity.ENTITY_LOCATION_TEXTURES;

public class CrabbyAnimation extends CrabbyModule implements PlayingDrawInterface, PlayingUpdateInterface {

    private BufferedImage[][] animations;

    public enum AnimationState {
        IDLE,
        RUNNING,
        ATTACK,
        HIT,
        DEAD;

        public static AnimationState animationState = IDLE;
    }

    private int aniTick, aniIndex;
    private int flipW = 1;
    private int flipX = 0;

    private boolean dead;
    private boolean pause;

    public CrabbyAnimation(Crabby crabby) {
        super(crabby);
        loadImages();
    }

    private void loadImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, CRABBY_SPRITE_PNG);
        animations = new BufferedImage[5][9];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 72, j * 32, 72, 32);
    }


    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        BufferedImage bufferedImage = animations[AnimationState.animationState.ordinal()][aniIndex];
        g.drawImage(bufferedImage,
                (int) ((crabby.getHitBoxTexture().x - lvlOffsetX + flipX) * scale),
                (int) ((crabby.getHitBoxTexture().y - lvlOffsetY) * scale),
                (int) (crabby.getHitBoxTexture().width * flipW * scale),
                (int) (crabby.getHitBoxTexture().height * scale),
                null);
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
                    crabby.setActive(false);
                }
                if (animationState == HIT) {
                    pause = false;
                }
                animationState = IDLE;
                aniIndex = 0;
            }
        }
    }

    private void updateAnimationBox() {
        boolean right = crabby.getCrabbyMove().isRight();
        boolean left = crabby.getCrabbyMove().isLeft();
        BufferedImage bufferedImage = animations[animationState.ordinal()][aniIndex];
        if (left) {
            flipW = 1;
            flipX = 0;
        } else if (right) {
            flipW = -1;
            flipX = bufferedImage.getWidth();
        }
    }

    private static int GetSpriteAmount() {

        switch (animationState) {
            case IDLE:
                return 9;
            case RUNNING:
                return 6;
            case ATTACK:
                return 7;
            case HIT:
                return 4;
            case DEAD:
                return 5;
            default:
                return 1;
        }

    }

    public void setAnimationState(AnimationState state) {
        if (dead || pause) {
            return;
        }
        if (state == DEAD) {
            dead = true;
        }
        if (state == HIT) {
            pause = true;
        }
        animationState = state;
        aniIndex = 0;
    }

    public AnimationState getAnimationState() {
        return animationState;
    }


    public int getAniIndex() {
        return aniIndex;
    }
}