package playing.entities.dynamics.crabby;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.dynamics.DynamicEntity;
import playing.entities.dynamics.EnemyManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.LvlConstants.Entity.CRABBY.CRABBY_HEIGHT_DEFAULT;
import static utilz.Constants.LvlConstants.Entity.CRABBY.CRABBY_WIDTH_DEFAULT;

public class Crabby extends DynamicEntity implements PlayingDrawInterface, PlayingUpdateInterface {

    private CrabbyMove crabbyMove;
    private CrabbyAnimation crabbyAnimation;
    private CrabbyAttack crabbyAttack;

    public Crabby(double x, double y) {
        super(x, y, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
        setHitBoxTexture(x - 26, y - 9, 72, 32);
        loadImages();
        initModules();
    }

    private void loadImages() {

    }

    private void initModules() {
        crabbyMove = new CrabbyMove(this);
        crabbyAnimation = new CrabbyAnimation(this);
        crabbyAttack = new CrabbyAttack(this);
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        crabbyAnimation.draw(g, scale, lvlOffsetX, lvlOffsetY);
        drawHitBox(g, scale, lvlOffsetX, lvlOffsetY);
        drawHitBoxTexture(g, scale, lvlOffsetX, lvlOffsetY);
        crabbyAttack.draw(g, scale, lvlOffsetX, lvlOffsetY);
    }

    @Override
    public void update() {
        crabbyMove.update();
        crabbyAnimation.update();
        crabbyAttack.update();
    }

    public CrabbyMove getCrabbyMove() {
        return crabbyMove;
    }

    public CrabbyAnimation getCrabbyAnimation() {
        return crabbyAnimation;
    }
}