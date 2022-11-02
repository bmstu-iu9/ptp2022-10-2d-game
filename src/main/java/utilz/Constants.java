package utilz;

import static utilz.Constants.GameWindowConstants.TILE_SIZE_DEFAULT;

public class Constants {

    public static final class GameWindowConstants {
        public final static int FPS_SET = 120;
        public final static int UPS_SET = 200;

        public final static int TILE_SIZE_DEFAULT = 32;
        public final static int TILES_IN_WIDTH = 26;
        public final static int TILES_IN_HEIGHT = 14;
        public final static int GAME_WIDTH_DEFAULT = TILE_SIZE_DEFAULT * TILES_IN_WIDTH;
        public final static int GAME_HEIGHT_DEFAULT = TILE_SIZE_DEFAULT * TILES_IN_HEIGHT;

        public final static int LEFT_BORDER = (int) (0.3 * GAME_WIDTH_DEFAULT);
        public final static int RIGHT_BORDER = (int) (0.7 * GAME_WIDTH_DEFAULT);
        public final static int TOP_BORDER = (int) (0.3 * GAME_HEIGHT_DEFAULT);
        public final static int DOWN_BORDER = (int) (0.7 * GAME_HEIGHT_DEFAULT);
    }

    public static class GameConstants {
        public final static float GRAVITY = 0.035f;
        public static final float ANI_SPEED_ENEMY = 25;

        public static final float ANI_SPEED_OBJECT = 50;

    }

    public static class UI {
        public static class Button {
            public static final int ON = 0;
            public static final int OVER = 1;
            public static final int PRESSED = 2;
        }

        public static class MenuButtons {
            public static final int COUNT_BUTTONS = 1;

            public static final int PLAY = 0;
            public static final int OPTIONS = 1;
            public static final int QUIT = 2;

            public static final int BUTTON_WIDTH_DEFAULT = 140;
            public static final int BUTTON_HEIGHT_DEFAULT = 56;
        }

        public static class SoundButtons {
            public static final int MUTED_OFF = 0;
            public static final int MUTED_ON = 1;

            public static final int SOUND_SIZE_DEFAULT = 42;
        }

        public static class URMButtons {
            public static final int URM_PLAY = 0;
            public static final int URM_REPLAY = 1;
            public static final int URM_MENU = 2;

            public static final int URM_DEFAULT_SIZE = 56;
        }

        public static class VolumeButtons {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;
        }

        public static class Overlay {
            public static class Pause {
                public static final int PAUSE_SOUND_POS_X = 450;
                public static final int PAUSE_SOUND_MUSIC_POS_Y = 140;
                public static final int PAUSE_SOUND_SFX_POS_Y = 186;

                public static final int PAUSE_URM_PLAY_POS_X = 462;
                public static final int PAUSE_URM_REPLAY_POS_X = 387;
                public static final int PAUSE_URM_MENU_POS_X = 313;
                public static final int PAUSE_URM_POS_Y = 325;

                public static final int PAUSE_VOLUME_POS_X = 309;
                public static final int PAUSE_VOLUME_POS_Y = 278;
            }

            public static class GameOver {
                public static final int GAME_OVER_URM_PLAY_POS_X = 440;
                public static final int GAME_OVER_URM_MENU_POS_X = 335;
                public static final int GAME_OVER_URM_POS_Y = 205;
            }

            public static class LevelCompleted {
                public static final int LEVEL_COMPLETED_URM_NEXT_POS_X = 445;
                public static final int LEVEL_COMPLETED_URM_MENU_POS_X = 330;
                public static final int LEVEL_COMPLETED_URM_POS_Y = 245;
            }
        }

    }

    public static final class LvlConstants {

        public static final class Entity {

            public static final class Object {
                public static final int VOID = 0;
                public static final int OBJECT_INDEX_SPIKE_DOWN = 1;
                public static final int OBJECT_INDEX_SPIKE_UP = 2;
                public static final int OBJECT_INDEX_SPIKE_LEFT = 3;
                public static final int OBJECT_INDEX_SPIKE_RIGHT = 4;
                public static final int OBJECT_INDEX_COIN = 65;
                public static final int OBJECT_INDEX_COIN_5 = 66;
                public static final int OBJECT_INDEX_PORTAL = 108;
            }

            public static final class Enemy {
                public static final int VOID = 0;
                public static final int ENEMY_INDEX_CRABBY = 1;
            }

            public static final class Spike {
                public static final int SPIKE_WIDTH_TEXTURE = 32;
                public static final int SPIKE_HEIGHT_TEXTURE = 32;
                public static final int SPIKE_WIDTH_DEFAULT_UD = 32;
                public static final int SPIKE_HEIGHT_DEFAULT_UD = 16;
                public static final int SPIKE_WIDTH_DEFAULT_LR = 16;
                public static final int SPIKE_HEIGHT_DEFAULT_LR = 32;
            }
            public static final class Portal {
                public static final int PORTAL_WIDTH_DEFAULT = 32;
                public static final int PORTAL_HEIGHT_DEFAULT = 64;
            }

            public static final class Coin {
                public static final int COIN_WIDTH_DEFAULT = 32;
                public static final int COIN_HEIGHT_DEFAULT = 32;
                public static final int COIN_5_WIDTH_DEFAULT = 40;
                public static final int COIN_5_HEIGHT_DEFAULT = 40;
                public static final double DIFF_SIZE_WIDTH = COIN_5_WIDTH_DEFAULT - COIN_WIDTH_DEFAULT;
                public static final double DIFF_SIZE_HEIGHT = COIN_5_HEIGHT_DEFAULT - COIN_HEIGHT_DEFAULT;
            }

            public static final class CRABBY {
                public static final int CRABBY_WIDTH_DEFAULT = 22;
                public static final int CRABBY_HEIGHT_DEFAULT = 19;

                public static final int CRABBY_VIEW_RANGE = TILE_SIZE_DEFAULT * 5;
                public static final int CRABBY_ATTACK_RANGE = TILE_SIZE_DEFAULT;
            }
        }

    }

    public static class Player {
        public static class StatusBar {
            public static final int STATUS_BAR_POS_X = 10;
            public static final int STATUS_BAR_POS_Y = 10;
            public static final int STATUS_BAR_WIDTH = 192;
            public static final int STATUS_BAR_HEIGHT = 58;

            public static final int HEALTH_BAR_POS_X = 45;
            public static final int HEALTH_BAR_POS_Y = 24;
            public static final int HEALTH_BAR_WIDTH = 150;
            public static final int HEALTH_BAR_HEIGHT = 4;

            public static final int POWER_BAR_POS_X = 56;
            public static final int POWER_BAR_POS_Y = 44;
            public static final int POWER_BAR_WIDTH = 100;
            public static final int POWER_BAR_HEIGHT = 4;
        }
    }


    public static final class TextureConstants {
        public static final class Menu {
            public static final String MENU_LOCATION_TEXTURES = "menu";

            public static final String MENU_ATLAS_PNG = "menu_atlas.png";
            public static final String MENU_BACKGROUND_PNG = "menu_background.png";
            public static final String MENU_BUTTONS_PNG = "menu_buttons.png";
        }

        public static final class Options {

        }

        public static final class Overlay {
            public static final String OVERLAY_LOCATION_TEXTURES = "overlay";

            public static final String PAUSE_ATLAS_PNG = "pause_atlas.png";
            public static final String GAME_OVER_ATLAS_PNG = "game_over_atlas.png";
            public static final String COMPLETED_ATLAS_PNG = "completed_atlas.png";
            public static final String OPTIONS_ATLAS_PNG = "options_atlas.png";

            public static final String OVERLAY_SOUND_BUTTONS_PNG = "overlay_sound_buttons.png";
            public static final String OVERLAY_URM_BUTTONS_PNG = "overlay_urm_buttons.png";
            public static final String OVERLAY_VOLUME_BUTTONS_PNG = "overlay_volume_buttons.png";


        }

        public static final class Level {
            public static final String LEVEL_LOCATION_TEXTURES = "level";
            public static final String LVL_TEXTURES_PNG = "lvl_textures.png";
            public static final String LVL_BACKGROUND_PNG = "lvl_background.png";
            public static final String LVL_CLOUDS_BIG_PNG = "lvl_clouds_big.png";
            public static final String LVL_CLOUDS_SMALL_PNG = "lvl_clouds_small.png";

        }

        public static final class Player {
            public static final String PLAYER_LOCATION_TEXTURES = "player";

            public static final String PLAYER_SPRITES_PNG = "player_sprites.png";
            public static final String HEALTH_POWER_BAR = "health_power_bar.png";

        }

        public static final class Entity {
            public static final String ENTITY_LOCATION_TEXTURES = "entity";

            public static final String COIN_ATLAS_PNG = "coin_atlas.png";
            public static final String COIN_5_PNG = "coin_5.png";
            public static final String TRAP_ATLAS_PNG = "trap_atlas.png";
            public static final String PORTAL_ATLAS_PNG = "portal_atlas.png";
            public static final String CRABBY_SPRITE_PNG = "crabby_sprite.png";
        }

        public static final class Number {
            public static final String NUMBER_LOCATION_TEXTURES = "number";

            public static final String NUMBER_0_PNG = "number_0.png";
            public static final String NUMBER_1_PNG = "number_1.png";
            public static final String NUMBER_2_PNG = "number_2.png";
            public static final String NUMBER_3_PNG = "number_3.png";
            public static final String NUMBER_4_PNG = "number_4.png";
            public static final String NUMBER_5_PNG = "number_5.png";
            public static final String NUMBER_6_PNG = "number_6.png";
            public static final String NUMBER_7_PNG = "number_7.png";
            public static final String NUMBER_8_PNG = "number_8.png";
            public static final String NUMBER_9_PNG = "number_9.png";

        }

    }

}
