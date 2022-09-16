package playing.entities.statics;

import playing.entities.Entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class ObjectEntity extends Entity {
    protected Rectangle2D.Double hitBoxTexture;

    public ObjectEntity(double x, double y) {
        super(x, y);
    }

    public ObjectEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    protected void setHitBoxTexture(double x, double y, double width, double height) {
        hitBoxTexture = new Rectangle2D.Double(x, y, width, height);
    }

    protected void drawHitBoxTexture(Graphics g, float scale, int LvlOffsetX, int LvlOffsetY) {
        g.setColor(Color.YELLOW);
        g.drawRect((int) ((hitBoxTexture.x - LvlOffsetX) * scale),
                (int) ((hitBoxTexture.y - LvlOffsetY) * scale),
                (int) (hitBoxTexture.width * scale),
                (int) (hitBoxTexture.height * scale));
    }
}