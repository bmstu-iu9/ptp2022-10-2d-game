package playing.entities.statics;

import playing.PlayingDrawInterface;
import playing.PlayingGame;
import playing.PlayingUpdateInterface;
import playing.entities.player.Player;
import playing.levels.Level;

import java.awt.*;
import java.util.ArrayList;

public class ObjectManager implements PlayingUpdateInterface, PlayingDrawInterface {
    private PlayingGame playingGame;

    private ArrayList<Spike> spikes;

    public ObjectManager(PlayingGame playingGame, Level level) {
        this.playingGame = playingGame;
        loadObjects(level);
    }

    private void loadObjects(Level level) {
        spikes = level.getSpikes();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        drawTraps(g, scale, lvlOffsetX, lvlOffsetY);
    }

    private void drawTraps(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (Spike spike : spikes) {
            spike.draw(g, scale, lvlOffsetX, lvlOffsetY);
        }
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes) {
            if (s.getHitBox().intersects(p.getHitBox())) {
                p.kill();
            }
        }
    }


    public void resetAll() {

    }
}