package playing.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected Rectangle2D.Double hitBox;
    protected Rectangle2D.Double hitBoxTexture;

    public Entity(double x, double y) {
        hitBox = new Rectangle2D.Double(x, y, 0 ,0);
    }

    public Entity(double x, double y, double width, double height) {
        this.hitBox = new Rectangle2D.Double(x, y, width, height);
    }
    public void drawHitBox(Graphics g, float scale, int LvlOffsetX, int LvlOffsetY) {
//        g.setColor(Color.BLUE);
//        g.drawRect((int) ((hitBox.x - LvlOffsetX) * scale),
//                (int) ((hitBox.y - LvlOffsetY) * scale),
//                (int) (hitBox.width * scale),
//                (int) (hitBox.height * scale));
    }

    protected void drawHitBoxTexture(Graphics g, float scale, int LvlOffsetX, int LvlOffsetY) {
//        g.setColor(Color.YELLOW);
//        g.drawRect((int) ((hitBoxTexture.x - LvlOffsetX) * scale),
//                (int) ((hitBoxTexture.y - LvlOffsetY) * scale),
//                (int) (hitBoxTexture.width * scale),
//                (int) (hitBoxTexture.height * scale));
    }


    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }


    public Rectangle2D.Double getHitBoxTexture() {
        return hitBoxTexture;
    }

    protected void setHitBox(double x, double y, double width, double height) {
        this.hitBox = new Rectangle2D.Double(x, y, width, height);
    }

    protected void setHitBoxTexture(double x, double y, double width, double height) {
        hitBoxTexture = new Rectangle2D.Double(x, y, width, height);
    }

    public double getX() {
        return hitBox.x;
    }
    public double getY() {
        return hitBox.y;
    }
    public void setX(double x) {
        if (hitBoxTexture != null) {
            hitBoxTexture.x = hitBoxTexture.x - (hitBox.x - x);
        }
        hitBox.x = x;

    }
    public void setY(double y) {
        if (hitBoxTexture != null) {
            hitBoxTexture.y = hitBoxTexture.y - (hitBox.y - y);
        }
        hitBox.y = y;
    }
}