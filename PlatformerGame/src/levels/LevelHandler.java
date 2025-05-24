package levels;

import gamestates.GameState;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class will take care of everything related to a level.
 */
public class LevelHandler {
    private Game game;
    private BufferedImage[] levelSprites;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    /**
     * Constructor for a LevelHandler object.
     * @param game the Game object used for various purposes
     */
    public LevelHandler(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        generateAllLevels();
    }

    /**
     * Separates the image into subimages.
     */
    private void importOutsideSprites() {
        BufferedImage image = LoadSave.GetSpriteCollection(LoadSave.LEVEL_ELEMENTS);
        levelSprites = new BufferedImage[48];

        // j represents the row and i represents the column
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;

                levelSprites[index] = image.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    /**
     * Creates all the levels and stores them in an ArrayList.
     */
    private void generateAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();

        for (BufferedImage levelImage : allLevels) {
            levels.add(new Level(levelImage));
        }
    }

    /**
     * Draws the current level of the game.
     *
     * @param g            the Graphics object used for drawing
     * @param levelOffset  the offset of the player and the left/right border
     */
    public void drawLevel(Graphics g, int levelOffset) {
        for (int j = 0; j < Game.HEIGHT_IN_TILES; j++) {
            for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(
                        levelSprites[index],
                        (i * Game.SIZE_IN_TILES) - levelOffset,
                        j * Game.SIZE_IN_TILES,
                        Game.SIZE_IN_TILES,
                        Game.SIZE_IN_TILES,
                        null);
            }
        }
    }

    /**
     * Updates the level based on actions performed.
     */
    public void updateLevel() {

    }

    /**
     * Loads the next level, if there is one.
     */
    public void loadNextLevel() {
        levelIndex++;

        if (levelIndex >= levels.size()) {
            levelIndex = 0;
            System.out.println("Game completed!");
            GameState.state = GameState.MENU;
        }

        generateNextLevel();
    }

    private void generateNextLevel() {
        Level nextLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyHandler().loadEnemies(nextLevel);
        game.getPlaying().getPlayer().loadLevelData(nextLevel.getLevelData());
        game.getPlaying().setMaxLevelOffsetX(nextLevel.getMaxLevelOffsetX());
    }

    /**
     * @return the current level
     */
    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    /**
     * @return the amount of levels
     */
    public int getLevelAmount() {
        return levels.size();
    }

    /**
     * @return what number the current level is
     */
    public int getLevelIndex() {
        return levelIndex;
    }
}
