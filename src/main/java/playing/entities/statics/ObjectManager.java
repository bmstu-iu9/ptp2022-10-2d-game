package playing.entities.statics;

import gamestates.playingstates.EnumPlayState;
import playing.PlayingDrawInterface;
import playing.PlayingGame;
import playing.PlayingUpdateInterface;
import playing.entities.player.Player;
import playing.levels.Level;

import javax.sound.sampled.Port;
import java.awt.*;
import java.util.ArrayList;

public class ObjectManager implements PlayingUpdateInterface, PlayingDrawInterface {
    private PlayingGame playingGame;

    private ArrayList<Spike> spikes;
    private ArrayList<Portal> portals;

    public ObjectManager(PlayingGame playingGame, Level level) {
        this.playingGame = playingGame;
        loadObjects(level);
    }

    private void loadObjects(Level level) {
        spikes = level.getSpikes();
        portals = level.getPortals();
    }

    @Override
    public void update() {
        updatePortals();
    }

    private void updatePortals() {
        for (Portal portal : portals) {
            portal.update();
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        drawTraps(g, scale, lvlOffsetX, lvlOffsetY);
        drawPortals(g, scale, lvlOffsetX, lvlOffsetY);
    }

    private void drawTraps(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (Spike spike : spikes) {
            spike.draw(g, scale, lvlOffsetX, lvlOffsetY);
        }
    }

    private void drawPortals(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (Portal portal : portals) {
            portal.draw(g, scale, lvlOffsetX, lvlOffsetY);
        }
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes) {
            if (s.getHitBox().intersects(p.getHitBox())) {
                p.kill();
            }
        }
    }

    public void checkPortalTouched(Player p) {
        for (Portal portal : portals) {
            if (portal.getHitBox().intersects(p.getHitBox())) {
                EnumPlayState.state = EnumPlayState.LVL_COMPLETED;
            }
        }
    }



    public void resetAll() {

    }
}