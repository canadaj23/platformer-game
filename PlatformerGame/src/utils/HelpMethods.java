package utils;

import main.Game;

import java.awt.geom.Rectangle2D;

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
        // Actual width of the level
        int maxWidth = levelData[0].length * Game.SIZE_IN_TILES;

        // Top left and bottom right of tile
        if ((x < 0 || x >= maxWidth) || (y < 0 || y >= Game.GAME_HEIGHT)) {
            return true;
        }

        float xIndex = x / Game.SIZE_IN_TILES, yIndex = y / Game.SIZE_IN_TILES;
        int value = levelData[(int) yIndex][(int) xIndex];

        return value != 11;
    }

    /**
     * Returns the distance from an Entity object's hitbox to a left or right wall.
     * @param hitbox Entity object's hitbox
     * @param xSpeed Entity object's speed in +x or -x direction
     * @return x distance from Entity object's hitbox to a wall
     */
    public static float GetXDistanceToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.SIZE_IN_TILES);

        if (xSpeed > 0) { // moving right
            int tileXPosition = currentTile * Game.SIZE_IN_TILES;
            int xOffset = (int) (Game.SIZE_IN_TILES - hitbox.width);

            return tileXPosition + xOffset - 1;

        } else { // moving left
            return currentTile * Game.SIZE_IN_TILES;
        }
    }

    /**
     * Returns the distance from an Entity object's hitbox to a roof/floor.
     * @param hitbox Entity object's hitbox
     * @param airSpeed Entity object's speed in the air
     * @return distance from an Entity object's hitbox to a roof/floor
     */
    public static float GetYDistanceToCeiling(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.SIZE_IN_TILES);

        if (airSpeed > 0) { // falling (touching a floor)
            int tileYPosition = currentTile * Game.SIZE_IN_TILES;
            int yOffset = (int) (Game.SIZE_IN_TILES - hitbox.height);

            return tileYPosition + yOffset - 1;
        } else { // jumping (touching a roof)
            return currentTile * Game.SIZE_IN_TILES;
        }
    }

    /**
     * Determines if an Entity object is on a floor.
     * @param hitbox the hitbox of an Entity object
     * @param levelData the current level's data
     * @return whether an Entity object is on a floor
     */
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        // Check the pixel below bottom left and bottom right corners
        return  IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData) &&
                IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData);
    }
}
