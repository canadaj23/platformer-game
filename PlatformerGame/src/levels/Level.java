package levels;

import entities.ManCrab;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.*;

public class Level {
    private BufferedImage levelImage;
    private int[][] levelData;
    private ArrayList<ManCrab> manCrabsArrayList;
    private int levelTilesWide, maxTilesOffset, maxLevelOffsetX;
    private Point playerSpawnPoint;

    /**
     * Constructor for a Level object.
     * @param levelImage the level's image
     */
    public Level(BufferedImage levelImage) {
        this.levelImage = levelImage;
        generateLevelData();
        createEnemies();
        calculateLevelOffsets();
        calculatePlayerSpawn();
    }

    /**
     * Generates the level data of a certain level.
     */
    private void generateLevelData() {
        levelData = GetLevelData(levelImage);
    }

    /**
     * Generates the enemies for a certain level.
     */
    private void createEnemies() {
        manCrabsArrayList = GetManCrabs(levelImage);
    }

    /**
     * Determines the offsets of a certain level.
     */
    private void calculateLevelOffsets() {
        levelTilesWide = levelImage.getWidth();
        maxTilesOffset = levelTilesWide - Game.WIDTH_IN_TILES;
        maxLevelOffsetX = Game.SIZE_IN_TILES * maxTilesOffset;
    }

    private void calculatePlayerSpawn() {
        playerSpawnPoint = GetPlayerSpawnPoint(levelImage);
    }

    /**
     * @param x which column of the level sprites
     * @param y which row of the level sprites
     * @return the index of a level element
     */
    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    /**
     *
     * @return the current level data
     */
    public int[][] getLevelData() {
        return levelData;
    }

    /**
     *
     * @return the max x-offset of a certain level
     */
    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    /**
     * @return the ArrayList of Man-Crabs of a certain level
     */
    public ArrayList<ManCrab> getManCrabsArrayList() {
        return manCrabsArrayList;
    }

    /**
     * @return the player's spawn point for the current level
     */
    public Point getPlayerSpawnPoint() {
        return playerSpawnPoint;
    }
}
