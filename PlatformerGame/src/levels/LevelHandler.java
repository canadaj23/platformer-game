package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class will take care of everything related to a level.
 */
public class LevelHandler {
    private Game game;
    private BufferedImage[] levelSprites;
    private Level levelOne;

    /**
     * Constructor for a LevelHandler object.
     * @param game the Game object used for various purposes
     */
    public LevelHandler(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
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
     * Draws the current level of the game.
     * @param g the Graphics object used for drawing
     */
    public void drawLevel(Graphics g) {
        for (int j = 0; j < Game.TILES_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_WIDTH; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(
                        levelSprites[index],
                        i * Game.TILES_SIZE,
                        j * Game.TILES_SIZE,
                        Game.TILES_SIZE,
                        Game.TILES_SIZE,
                        null);
            }
        }
    }

    /**
     * Updates the level based on actions performed.
     */
    public void updateLevel() {

    }
}
