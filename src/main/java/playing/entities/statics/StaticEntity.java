package playing.entities.statics;

import playing.entities.Entity;

import java.awt.geom.Rectangle2D;

public abstract class StaticEntity extends Entity {
    protected Rectangle2D.Double hitBox;

    public StaticEntity(double x, double y) {
        super(x, y);
    }
}