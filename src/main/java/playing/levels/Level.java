package playing.levels;

import playing.PlayingDrawInterface;
import playing.PlayingUpdateInterface;
import playing.entities.dynamics.crabby.Crabby;
import playing.entities.statics.Coin;
import playing.entities.statics.Heart;
import playing.entities.statics.Portal;
import playing.entities.statics.Spike;
import playing.entities.statics.Pistol;
import playing.levels.clouds.CloudManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.GameWindowConstants.*;
import static utilz.Constants.LvlConstants.Entity.Enemy.ENEMY_INDEX_CRABBY;
import static utilz.Constants.LvlConstants.Entity.Object.*;
import static utilz.Constants.TextureConstants.Level.*;

public class Level implements PlayingUpdateInterface, PlayingDrawInterface {

    private final BufferedImage levelImg;
    private BufferedImage[] levelSprite;
    private int[][] lvlData;

    private BufferedImage backgroundImg;

    private BufferedImage backgroundNightImg;

    private BufferedImage moonImg;

    private CloudManager cloudManager;

    private int maxLvlOffsetX, maxLvlOffsetY;

    private boolean isNight;

    public Level(BufferedImage levelImg) {
        this.levelImg = levelImg;
        GetLevelData();
        loadBackgroundImages();
        calcLvlOffset();
        importOutsideSprites();
        cloudManager = new CloudManager();
    }

    private void GetLevelData() {
        int[][] lvlData = new int[levelImg.getHeight()][levelImg.getWidth()];

        for (int j = 0; j < levelImg.getHeight(); j++)
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }

        this.lvlData = lvlData;
    }

    private void loadBackgroundImages() {
        backgroundImg = LoadSave.GetSpriteAtlas(LEVEL_LOCATION_TEXTURES, LVL_BACKGROUND_PNG);
        backgroundNightImg = LoadSave.GetSpriteAtlas(LEVEL_LOCATION_TEXTURES, LVL_BACKGROUND_NIGHT_PNG);
        moonImg = LoadSave.GetSpriteAtlas(LEVEL_LOCATION_TEXTURES, MOON_PNG);
    }

    private void calcLvlOffset() {
        int lvlTilesWideX = levelImg.getWidth();
        int maxTilesOffsetX = lvlTilesWideX - TILES_IN_WIDTH;
        maxLvlOffsetX = TILE_SIZE_DEFAULT * maxTilesOffsetX;

        int lvlTilesWideY = levelImg.getHeight();
        int maxTilesOffsetY = lvlTilesWideY - TILES_IN_HEIGHT;
        maxLvlOffsetY = TILE_SIZE_DEFAULT * maxTilesOffsetY;
    }


    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LEVEL_LOCATION_TEXTURES, LVL_TEXTURES_PNG);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    @Override
    public void update() {
        cloudManager.update();
    }


    @Override
    public void draw(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        if (isNight) {
            g.drawImage(backgroundNightImg, 0, 0,
                    (int) (GAME_WIDTH_DEFAULT * scale),
                    (int) (GAME_HEIGHT_DEFAULT * scale),
                    null);
            g.drawImage(moonImg, (int) (175* scale), (int) (125 * scale),
                    (int) (TILE_SIZE_DEFAULT * 2 * scale), (int) (TILE_SIZE_DEFAULT * 2 * scale), null);
            g.drawImage(moonImg, (int) (200 * scale), (int) (150 * scale),
                    (int) (TILE_SIZE_DEFAULT * 3 * scale), (int) (TILE_SIZE_DEFAULT * 3 * scale), null);

        } else {
            drawBackground(g, scale, lvlOffsetX, lvlOffsetY);
        }
        cloudManager.draw(g, scale, lvlOffsetX, lvlOffsetY);
        drawLvlSprite(g, scale, lvlOffsetX, lvlOffsetY);

        if (isNight) {
            drawNight(g, scale);
        }

    }

    private void drawNight(Graphics g, float scale) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0,
                (int) (GAME_WIDTH_DEFAULT * scale),
                (int) (GAME_HEIGHT_DEFAULT * scale));

    }


    private void drawLvlSprite(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        for (int j = 0; j < lvlData.length; j++) {
            for (int i = 0; i < lvlData[0].length; i++) {
                int index = lvlData[j][i];
                g.drawImage(levelSprite[index],
                        (int) ((TILE_SIZE_DEFAULT * i - lvlOffsetX) * scale),
                        (int) ((TILE_SIZE_DEFAULT * j - lvlOffsetY) * scale),
                        (int) (TILE_SIZE_DEFAULT * scale), (int) (TILE_SIZE_DEFAULT * scale), null);
            }
        }
    }

    private void drawBackground(Graphics g, float scale, int lvlOffsetX, int lvlOffsetY) {
        g.drawImage(backgroundImg, 0, 0,
                (int) (GAME_WIDTH_DEFAULT * scale),
                (int) (GAME_HEIGHT_DEFAULT * scale),
                null);
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int getMaxLvlOffsetX() {
        return maxLvlOffsetX;
    }

    public int getMaxLvlOffsetY() {
        return maxLvlOffsetY;
    }

    public ArrayList<Spike> getSpikes() {
        ArrayList<Spike> list = new ArrayList<>();

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getBlue();
                if (value == OBJECT_INDEX_SPIKE_DOWN) {
                    list.add(new Spike(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT, Spike.SpikeState.DOWN));
                } else if (value == OBJECT_INDEX_SPIKE_UP) {
                    list.add(new Spike(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT, Spike.SpikeState.UP));
                } else if (value == OBJECT_INDEX_SPIKE_LEFT) {
                    list.add(new Spike(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT, Spike.SpikeState.LEFT));
                } else if (value == OBJECT_INDEX_SPIKE_RIGHT) {
                    list.add(new Spike(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT, Spike.SpikeState.RIGHT));
                }
            }
        }

        return list;
    }

    public ArrayList<Portal> getPortals() {
        ArrayList<Portal> list = new ArrayList<>();

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getBlue();
                if (value == OBJECT_INDEX_PORTAL) {
                    list.add(new Portal(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT));
                }
            }
        }

        return list;
    }

    public ArrayList<Coin> getCoins() {
        ArrayList<Coin> list = new ArrayList<>();

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getBlue();
                if (value == OBJECT_INDEX_COIN) {
                    list.add(new Coin(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT));
                }
            }
        }

        return list;
    }

    public ArrayList<Heart> getHearts() {
        ArrayList<Heart> list = new ArrayList<>();

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getBlue();
                if (value == OBJECT_INDEX_HEART) {
                    list.add(new Heart(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT));
                }
            }
        }

        return list;
    }

    public ArrayList<Crabby> getCrabbies() {
        ArrayList<Crabby> list = new ArrayList<>();

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getGreen();
                if (value == ENEMY_INDEX_CRABBY) {
                    list.add(new Crabby(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT));
                }
            }
        }

        return list;
    }

    public ArrayList<Pistol> getPistols() {
        ArrayList<Pistol> list = new ArrayList<>();

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getBlue();
                if (value == OBJECT_INDEX_PISTOL) {
                    list.add(new Pistol(i * TILE_SIZE_DEFAULT, j * TILE_SIZE_DEFAULT));
                }
            }
        }

        return list;
    }

    public void setNightOrDay() {
        isNight = !isNight;
    }
}
