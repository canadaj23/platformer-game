package utils;

import entities.ManCrab;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utils.Constants.Enemy.*;

/**
 * This class will handle everything related to sprites including player, enemy, level, etc.
 */
public class LoadSave {
    public static final String PLAYER_SPRITES = "player_sprites";
    public static final String LEVEL_ELEMENTS = "outside_sprites";
    public static final String LEVEL_ONE_DATA = "level_one_data";
    public static final String LEVEL_ONE_DATA_LONG = "level_one_data_long";
    public static final String LEVEL_ONE_DATA_LONG_WITH_ENEMY = "level_one_data_long_with_enemy";
    public static final String MENU_BUTTONS = "menu_buttons";
    public static final String MENU_OVERLAY = "menu_overlay";
    public static final String PAUSE_OVERLAY = "pause_overlay";
    public static final String SOUND_BUTTONS = "sound_buttons";
    public static final String OTHER_PAUSED_BUTTONS = "other_paused_buttons";
    public static final String VOLUME_BUTTONS = "volume_buttons";
    public static final String MENU_BACKGROUND = "menu_background";
    public static final String PLAYING_BACKGROUND = "playing_background";
    public static final String BIG_CLOUDS = "big_clouds";
    public static final String SMALL_CLOUDS = "small_clouds";
    public static final String MAN_CRAB_SPRITES = "man_crab_sprites";
    public static final String PLAYER_STATUS_OVERLAY = "player_status_overlay";

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

    public static ArrayList<ManCrab> GetManCrabs() {
        BufferedImage image = GetSpriteCollection(LEVEL_ONE_DATA_LONG_WITH_ENEMY);
        ArrayList<ManCrab> manCrabArrayList = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == MAN_CRAB) {
                    manCrabArrayList.add(new ManCrab(i * Game.SIZE_IN_TILES, j * Game.SIZE_IN_TILES));
                }
            }
        }

        return manCrabArrayList;
    }

    /**
     * @return the level data for a given level
     */
    public static int[][] GetLevelData() {
        // Level data will change (e.g., LEVEL_ONE_DATA vs. LEVEL_ONE_DATA_LONG)
        BufferedImage image = GetSpriteCollection(LEVEL_ONE_DATA_LONG_WITH_ENEMY);
        // getHeight and getWidth are for visible size, not necessarily the level size
        int[][] levelData = new int[image.getHeight()][image.getWidth()];

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
