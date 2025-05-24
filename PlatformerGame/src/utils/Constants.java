package utils;

import main.Game;

public class Constants {

    public static class Entity {
        public static final float GRAVITY = 0.04f * Game.SCALE;
        public static final int ANIMATION_SPEED = 25;
    }

    /**
     * This class holds everything enemy-related.
     */
    public static class Enemy {
        // Enemy type
        public static final int MAN_CRAB = 0;

        // Enemy state
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        // MAN-CRAB dimensions
        public static final int DEFAULT_MAN_CRAB_WIDTH = 72;
        public static final int DEFAULT_MAN_CRAB_HEIGHT = 32;

        public static final int MAN_CRAB_WIDTH = (int) (DEFAULT_MAN_CRAB_WIDTH * Game.SCALE);
        public static final int MAN_CRAB_HEIGHT = (int) (DEFAULT_MAN_CRAB_HEIGHT * Game.SCALE);

        public static final int MAN_CRAB_OFFSET_X = (int) (26 * Game.SCALE);
        public static final int MAN_CRAB_OFFSET_Y = (int) (9 * Game.SCALE);

        /**
         * Returns the amount of sprites for a given enemy.
         * @param enemy_type the type of enemy (e.g., Man-Crab)
         * @param enemy_state the state the enemy is in (e.g., RUNNING)
         * @return how many sprites a given enemy has
         */
        public static int GetEnemySpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case MAN_CRAB -> {
                    switch (enemy_state) {
                        case IDLE -> {return 9;}
                        case RUNNING -> {return 6;}
                        case ATTACK -> {return 7;}
                        case HIT -> {return 4;}
                        case DEAD -> {return 5;}
                    }
                }
            }

            return 0;
        }

        /**
         * Returns the max health of an enemy based on its type.
         * @param enemy_type the type of enemy
         * @return how much health an enemy of a certain type has
         */
        public static int GetEnemyMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case MAN_CRAB -> {return 10;}
                default -> {return 1;}
            }
        }

        /**
         * Returns the amount of damage an enemy deals based on its type.
         * @param enemy_type the enemy type
         * @return how much damage an enemy of a certain type deals
         */
        public static int GetEnemyDamage(int enemy_type) {
            switch (enemy_type) {
                case MAN_CRAB -> {return 15;}
                default -> {return 0;}
            }
        }
    }

    /**
     * This class holds everything environment-related.
     */
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

    /**
     * This class holds everything UI-related.
     */
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
     * This class holds everything direction-related.
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
        public static final int ATTACK = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        /**
         * Returns amount of sprites within a BufferedImage array.
         * @param player_action the action being performed
         * @return the amount of sprites within a BufferedImage array.
         */
        public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case DEAD -> 8;
                case RUNNING -> 6;
                case IDLE -> 5;
                case HIT -> 4;
                case JUMPING, ATTACK -> 3;
                default -> 1;
            };
        }
    }
}
