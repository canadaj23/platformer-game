package utils;

import main.Game;

public class Constants {

    public static class Environment {
        public static final int DEFAULT_BIG_CLOUD_WIDTH = 448;
        public static final int DEFAULT_BIG_CLOUD_HEIGHT = 101;

        public static final int BIG_CLOUD_WIDTH = (int) (DEFAULT_BIG_CLOUD_WIDTH * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT= (int) (DEFAULT_BIG_CLOUD_HEIGHT * Game.SCALE);

        public static final int DEFAULT_SMALL_CLOUD_WIDTH = 74;
        public static final int DEFAULT_SMALL_CLOUD_HEIGHT = 24;

        public static final int SMALL_CLOUD_WIDTH = (int) (DEFAULT_SMALL_CLOUD_WIDTH * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (DEFAULT_SMALL_CLOUD_HEIGHT * Game.SCALE);
    }

    public static class UI {
        public static class MenuButtons {
            public static final int DEFAULT_MENU_B_WIDTH = 140;
            public static final int DEFAULT_MENU_B_HEIGHT = 56;

            public static final int MENU_B_WIDTH = (int) (DEFAULT_MENU_B_WIDTH * Game.SCALE);
            public static final int MENU_B_HEIGHT = (int) (DEFAULT_MENU_B_HEIGHT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int DEFAULT_SOUND_B_SIZE = 42;

            public static final int SOUND_B_SIZE = (int) (DEFAULT_SOUND_B_SIZE * Game.SCALE);
        }

        public static class OtherPauseButtons {
            public static final int DEFAULT_OTHER_PAUSE_B_SIZE = 56;

            public static final int OTHER_PAUSE_B_SIZE = (int) (DEFAULT_OTHER_PAUSE_B_SIZE * Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int DEFAULT_VOLUME_B_WIDTH = 28;
            public static final int DEFAULT_VOLUME_B_HEIGHT = 44;
            public static final int DEFAULT_SLIDER_B_WIDTH = 215;

            public static final int VOLUME_B_WIDTH = (int) (DEFAULT_VOLUME_B_WIDTH * Game.SCALE);
            public static final int VOLUME_B_HEIGHT = (int) (DEFAULT_VOLUME_B_HEIGHT * Game.SCALE);
            public static final int SLIDER_B_WIDTH = (int) (DEFAULT_SLIDER_B_WIDTH * Game.SCALE);
        }
    }

    /**
     * This class holds everything related to directions.
     */
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    /**
     * This class holds everything related to the player.
     */
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        /**
         * Returns amount of sprites within a BufferedImage array.
         * @param player_action the action being performed
         * @return the amount of sprites within a BufferedImage array.
         */
        public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case RUNNING -> 6;
                case IDLE -> 5;
                case HIT -> 4;
                case JUMPING, ATTACK_1, ATTACK_JUMP_1, ATTACK_JUMP_2 -> 3;
                case GROUND -> 2;
                default -> 1;
            };
        }
    }
}
