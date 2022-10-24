package playing.levels.clouds;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.Entity;

import java.awt.image.BufferedImage;

public abstract class Cloud extends Entity implements PlayingUpdateInterface, PlayingDrawInterface {
    protected BufferedImage cloud;
    protected double cloudSpeed;

    public Cloud(double x, double y) {
        super(x, y);
    }
}
