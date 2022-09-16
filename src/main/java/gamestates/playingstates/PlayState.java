package gamestates.playingstates;

import gamestates.GamePanelInterface;
import gamestates.GamePanelListenerInterface;
import gamestates.GameState;
import gamestates.Playing;

public abstract class PlayState extends GameState implements GamePanelInterface, GamePanelListenerInterface {

    protected final Playing playing;

    public PlayState(Playing playing) {
        this.playing = playing;
    }

    abstract void loadImages();

    abstract void calcBorder();

    abstract void createButtons();

}
