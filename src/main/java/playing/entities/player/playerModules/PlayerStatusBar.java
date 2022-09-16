package playing.entities.player.playerModules;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Player.HEALTH_POWER_BAR;
import static utilz.Constants.TextureConstants.Player.PLAYER_LOCATION_TEXTURES;
import static utilz.Constants.Player.StatusBar.*;

public class PlayerStatusBar extends PlayerModule implements PlayingUpdateInterface, PlayingDrawInterface {

    private BufferedImage healthPowerBarImg;

    private final int maxHealth = 150;
    private int currentHealth = 100;
    private final int maxPower = 100;
    private int currentPower = 75;


    public PlayerStatusBar(PlayerModuleManager playerModuleManager) {
        super(playerModuleManager);
        loadImages();
    }

    private void loadImages() {
        healthPowerBarImg = LoadSave.GetSpriteAtlas(PLAYER_LOCATION_TEXTURES, HEALTH_POWER_BAR);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(healthPowerBarImg,
                (int) (STATUS_BAR_POS_X * scale),
                (int) (STATUS_BAR_POS_Y * scale),
                (int) (STATUS_BAR_WIDTH * scale),
                (int) (STATUS_BAR_HEIGHT * scale),
                null);
        g.setColor(Color.RED);
        g.fillRect((int) (HEALTH_BAR_POS_X * scale),
                (int) (HEALTH_BAR_POS_Y * scale),
                (int) (HEALTH_BAR_WIDTH * ((double) currentHealth / (double) maxHealth) * scale),
                (int) (HEALTH_BAR_HEIGHT * scale));
        g.setColor(Color.YELLOW);
        g.fillRect((int) (POWER_BAR_POS_X * scale),
                (int) (POWER_BAR_POS_Y * scale),
                (int) (POWER_BAR_WIDTH * ((double) currentPower / (double) maxPower) * scale),
                (int) (POWER_BAR_HEIGHT * scale));
    }

    public void increaseHealth(int value) {
        currentHealth += Math.abs(value);
        if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void decreaseHealth(int value) {
        currentHealth -= Math.abs(value);
        if (currentHealth <= 0) {
            currentHealth = 0;
        }
    }

    public void increasePower(int value) {
        currentPower += Math.abs(value);
        if (currentPower >= maxPower) {
            currentPower = maxPower;
        }
    }

    public void decreasePower(int value) {
        currentPower -= Math.abs(value);
        if (currentPower <= 0) {
            currentPower = 0;
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getCurrentPower() {
        return currentPower;
    }


}