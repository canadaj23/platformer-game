package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class will handle everything related to sprites including player, enemy, level, etc.
 */
public class LoadSave {
    public static final String PLAYER_SPRITES = "player_sprites";
    public static final String LEVEL_ELEMENTS = "outside_sprites";
    public static final String LEVEL_ONE_DATA = "level_one_data";

    /**
     * @return a BufferedImage object of all the sprites of a given collection.
     * This could be for the player, an enemy, etc.
     */
    public static BufferedImage GetSpriteCollection(String file_name) {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + file_name + ".png");

        try {
            image = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return image;
    }

    /**
     * @return the level data for a given level
     */
    public static int[][] GetLevelData() {
        int[][] levelData = new int[Game.TILES_HEIGHT][Game.TILES_WIDTH];
        BufferedImage image = GetSpriteCollection(LEVEL_ONE_DATA);

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                levelData[j][i] = value;
            }
        }

        return levelData;
    }
}
