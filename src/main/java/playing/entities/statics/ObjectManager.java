package playing.entities.statics;

import gamestates.playingstates.EnumPlayState;
import playing.PlayingDrawInterface;
import playing.PlayingGame;
import playing.PlayingUpdateInterface;
import playing.entities.EntityLevelManager;
import playing.entities.player.Player;
import playing.entities.player.playerModules.PlayerAnimation;
import playing.levels.Level;

import javax.sound.sampled.Port;
import java.awt.*;
import java.util.ArrayList;

public class ObjectManager implements PlayingUpdateInterface, PlayingDrawInterface {
    private EntityLevelManager entityLevelManager;

    private ArrayList<Spike> spikes;
    private ArrayList<Portal> portals;
    private ArrayList<Coin> coins;
    private ArrayList<Pistol> pistols;

    private ArrayList<Heart> hearts;

    public ObjectManager(EntityLevelManager entityLevelManager, Level level) {
        this.entityLevelManager = entityLevelManager;
        loadObjects(level);
    }

    private void loadObjects(Level level) {
        spikes = level.getSpikes();
        portals = level.getPortals();
        coins = level.getCoins();
        pistols = level.getPistols();
        hearts = level.getHearts();
    }

    @Override
    public void update() {
        updatePortals();
    }

    private void updatePortals() {
        for (Portal portal : portals) {
            portal.update();
        }
        for (Coin coin : coins) {
            if (coin.isActive()) {
                coin.update();
            }
        }
        for (Pistol pistol : pistols) {
            if (pistol.isActive()) {
                pistol.update();
            }
        }
        for (Heart heart : hearts) {
            if (heart.isActive()) {
                heart.update();
            }
        }
    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        drawTraps(g, scale, lvlOffsetX, lvlOffsetY);
        drawPortals(g, scale, lvlOffsetX, lvlOffsetY);
        drawCoins(g, scale, lvlOffsetX, lvlOffsetY);
        drawPistol(g, scale, lvlOffsetX, lvlOffsetY);
        drawHearts(g, scale, lvlOffsetX, lvlOffsetY);
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

    private void drawCoins(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (Coin coin : coins) {
            if (coin.isActive()) {
                coin.draw(g, scale, lvlOffsetX, lvlOffsetY);
            }
        }
    }
    private void drawPistol(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (Pistol pistol : pistols) {
            if (pistol.isActive()) {
                pistol.draw(g, scale, lvlOffsetX, lvlOffsetY);
            }
        }
    }

    private void drawHearts(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (Heart heart : hearts) {
            if (heart.isActive()) {
                heart.draw(g, scale, lvlOffsetX, lvlOffsetY);
            }
        }
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes) {
            if (s.getHitBox().intersects(p.getHitBox())) {
                p.kill();
            }
        }
    }

    public void checkCoinsTouched(Player p) {
        for (Coin coin : coins) {
            if (coin.isActive()) {
                if (coin.getHitBox().intersects(p.getHitBox())) {
                    p.addCoin();
                    coin.setActive(false);
                }
            }
        }
    }

    public void checkPistolsTouched(Player p) {
        for (Pistol pistol : pistols) {
            if (pistol.isActive()) {
                if (pistol.getHitBox().intersects(p.getHitBox())) {
                    pistol.setActive(false);
                    p.setAnimationType(PlayerAnimation.AnimationType.PISTOL);
                }
            }
        }
    }
    public void checkHeartsTouched(Player p) {
        for (Heart heart : hearts) {
            if (heart.isActive()) {
                if (heart.getHitBox().intersects(p.getHitBox())) {
                    p.heal();
                    heart.setActive(false);
                }
            }
        }
    }

    public void checkPortalTouched(Player p) {
        for (Portal portal : portals) {
            if (portal.getHitBox().intersects(p.getHitBox())) {
                if (p.getCoins() == coins.size()) {
                    EnumPlayState.state = EnumPlayState.LVL_COMPLETED;
                }
            }
        }
    }

    public void resetAll() {

    }
}
