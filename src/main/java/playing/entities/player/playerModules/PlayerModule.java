package playing.entities.player.playerModules;

import playing.entities.player.PlayerModuleManager;
import playing.entities.player.Player;

public abstract class PlayerModule {
    protected PlayerModuleManager playerModuleManager;

    public PlayerModule(PlayerModuleManager playerModuleManager) {
        this.playerModuleManager = playerModuleManager;
    }
}