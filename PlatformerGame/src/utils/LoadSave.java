package utils;

import entities.ManCrab;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static utils.Constants.Enemy.*;

/**
 * This class will handle everything related to sprites including player, enemy, level, etc.
 */
public class LoadSave {
    public static final String PLAYER_SPRITES = "player_sprites";
    public static final String LEVEL_ELEMENTS = "outside_sprites";
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
    public static final String LEVEL_COMPLETED_OVERLAY = "level_completed_overlay";

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
     * All levels will be added to a BufferedImage array for level switching.
     * @return all the levels of the game in a BufferedImage array
     */
    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] sortedFiles =  sortFiles(files);

        return addImages(sortedFiles);
    }

    /**
     * Sorts the levels in ascending order and returns a sorted array.
     * @param files the unsorted levels
     * @return a sorted array of levels
     */
    private static File[] sortFiles(File[] files) {
        File[] sortedFiles = new File[files.length];

        // Sort the files names in ascending order
        for (int j = 0; j < sortedFiles.length; j++) {
            for (int i = 0; i < files.length; i++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    sortedFiles[i] = files[j];
                }
            }
        }

        return sortedFiles;
    }

    /**
     * Takes the sorted levels and adds them to a BufferedImage array.
     * @param sortedFiles the sorted levels
     * @return an array of sorted level images
     */
    private static BufferedImage[] addImages(File[] sortedFiles) {
        BufferedImage[] levelImages = new BufferedImage[sortedFiles.length];

        for (int i = 0; i < levelImages.length; i++) {
            try {
                levelImages[i] = ImageIO.read(sortedFiles[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return levelImages;
    }
}
