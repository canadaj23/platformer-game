package utils;

import main.Game;

/**
 * This class holds any methods useful for determining game logic, etc.
 */
public class HelpMethods {

    /**
     *
     * @param x the x-coordinate of the target tile
     * @param y the y-coordinate of the target tile
     * @param width the width of the target tile
     * @param height the height of the target tile
     * @param levelData the current level's data
     * @return whether the Entity object can move to the target tile
     */
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        return  !IsSolid(x, y, levelData) &&
                !IsSolid(x + width, y + height, levelData) &&
                !IsSolid(x + width, y, levelData) &&
                !IsSolid(x, y + height, levelData);
    }

    /**
     *
     * @param x the x-coordinate of the target tile
     * @param y the y-coordinate of the target tile
     * @param levelData the current level's data
     * @return whether the target tile is solid and/or the tile is within the game window
     * There are 48 total sprites.
     * One row of the sprites has a transparent sprite at index 11.
     */
    private static boolean IsSolid(float x, float y, int[][] levelData) {
        float xIndex = x / Game.TILES_SIZE, yIndex = y / Game.TILES_SIZE;
        int value = levelData[(int) yIndex][(int) xIndex];

        return  (x < 0 || x >= Game.GAME_WIDTH) || // top left and bottom right
                (y < 0 || y >= Game.GAME_HEIGHT) || // top right and bottom left
                (value != 11);
    }
}
