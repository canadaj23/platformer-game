package levels;

public class Level {
    private int[][] levelData;

    /**
     * Constructor for a Level object.
     * @param levelData data pertaining to a certain level
     */
    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    /**
     * @param x which column of the level sprites
     * @param y which row of the level sprites
     * @return the index of a level element
     */
    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }
}
