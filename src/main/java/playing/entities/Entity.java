package playing.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected Rectangle2D.Double hitBox;
    public Entity(double x, double y) {
        hitBox = new Rectangle2D.Double(x, y, 0 ,0);
    }

    public Entity(double x, double y, double width, double height) {
        this.hitBox = new Rectangle2D.Double(x, y, width, height);
    }
    public void drawHitBox(Graphics g, float scale, int LvlOffsetX, int LvlOffsetY) {
        g.drawRect((int) ((hitBox.x - LvlOffsetX) * scale),
                (int) ((hitBox.y - LvlOffsetY) * scale),
                (int) (hitBox.width * scale),
                (int) (hitBox.height * scale));
    }

    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public double getX() {
        return hitBox.x;
    }
    public double getY() {
        return hitBox.y;
    }
    public void setX(double x) {
        hitBox.x = x;
    }
    public void setY(double y) {
        hitBox.y = y;
    }
}