package playing.entities.dynamics;

import gamestates.GamePanelInterface;
import playing.PlayingDrawInterface;
import playing.PlayingGame;
import playing.PlayingUpdateInterface;
import playing.levels.Level;

import java.awt.*;

public class EnemyManager implements PlayingUpdateInterface, PlayingDrawInterface {
    private PlayingGame playingGame;

    public EnemyManager(PlayingGame playingGame, Level level) {
        this.playingGame = playingGame;
        loadObjects(level);
    }

    private void loadObjects(Level level) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {

    }

    public void resetAll() {

    }
}