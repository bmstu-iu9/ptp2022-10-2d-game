package playing.entities.player.playerModules;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.player.PlayerModuleManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Entity.COIN_ATLAS_PNG;
import static utilz.Constants.TextureConstants.Entity.ENTITY_LOCATION_TEXTURES;
import static utilz.Constants.TextureConstants.Number.*;
import static utilz.Constants.TextureConstants.Player.HEALTH_POWER_BAR;
import static utilz.Constants.TextureConstants.Player.PLAYER_LOCATION_TEXTURES;
import static utilz.Constants.Player.StatusBar.*;

public class PlayerStatusBar extends PlayerModule implements PlayingUpdateInterface, PlayingDrawInterface {

    private BufferedImage healthPowerBarImg;
    private BufferedImage[] numberImages;

    private BufferedImage coinImg;

    private final int maxHealth = 150;
    private int currentHealth = 100;
    private final int maxPower = 100;
    private int currentPower = 75;

    private int coins = 0;


    public PlayerStatusBar(PlayerModuleManager playerModuleManager) {
        super(playerModuleManager);
        loadImages();
    }

    private void loadImages() {
        healthPowerBarImg = LoadSave.GetSpriteAtlas(PLAYER_LOCATION_TEXTURES, HEALTH_POWER_BAR);
        numberImages = new BufferedImage[10];
        numberImages[0] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_0_PNG);
        numberImages[1] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_1_PNG);
        numberImages[2] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_2_PNG);
        numberImages[3] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_3_PNG);
        numberImages[4] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_4_PNG);
        numberImages[5] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_5_PNG);
        numberImages[6] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_6_PNG);
        numberImages[7] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_7_PNG);
        numberImages[8] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_8_PNG);
        numberImages[9] = LoadSave.GetSpriteAtlas(NUMBER_LOCATION_TEXTURES, NUMBER_9_PNG);

        BufferedImage img = LoadSave.GetSpriteAtlas(ENTITY_LOCATION_TEXTURES, COIN_ATLAS_PNG);
        coinImg = img.getSubimage(0,0, 16, 16);
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

        drawCountCoin(g, scale, lvlOffsetX, lvlOffsetY);
    }

    private void drawCountCoin(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        String coinStr = "" + coins;
        int[] numbers = new int[coinStr.length()];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt("" +coinStr.charAt(i));
        }
        g.drawImage(coinImg,
                (int) ((STATUS_BAR_POS_X + STATUS_BAR_WIDTH + 10) * scale),
                (int) (STATUS_BAR_POS_Y * scale),
                (int) (32 * scale),
                (int) (32 * scale),
                null);
        for (int i = 0; i < numbers.length; i++) {
            g.drawImage(numberImages[numbers[i]],
                    (int) ((STATUS_BAR_POS_X + STATUS_BAR_WIDTH + 10 + 32) * scale),
                    (int) ((STATUS_BAR_POS_Y + 10) * scale),
                    (int) (16 * scale),
                    (int) (16 * scale),
                    null);
        }
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
            playerModuleManager.kill();
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

    public void addCoin() {
        coins++;
    }

    public int getCoins() {
        return coins;
    }
}