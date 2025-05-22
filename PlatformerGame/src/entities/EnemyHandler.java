package entities;

import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

/**
 * This class will hold all the enemy-related code.
 */
public class EnemyHandler {
    private Playing playing;
    private BufferedImage[][] manCrabArray;
    private ArrayList<ManCrab> manCrabsArrayList = new ArrayList<>();

    public EnemyHandler(Playing playing) {
        this.playing = playing;
        
        loadEnemyImages();
        addEnemies();
    }

    private void loadEnemyImages() {
        generateManCrabImages();
    }

    private void generateManCrabImages() {
        manCrabArray = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteCollection(LoadSave.MAN_CRAB_SPRITES);

        for (int j = 0; j < manCrabArray.length; j++) {
            for (int i = 0; i < manCrabArray[j].length; i++) {
                manCrabArray[j][i] = temp.getSubimage(
                        i * DEFAULT_MAN_CRAB_WIDTH,
                        j * DEFAULT_MAN_CRAB_HEIGHT,
                        DEFAULT_MAN_CRAB_WIDTH,
                        DEFAULT_MAN_CRAB_HEIGHT);
            }
        }
    }

    /**
     * Adds enemies to their Array Lists to be displayed.
     */
    private void addEnemies() {
        addManCrabs();
    }

    /**
     * Specifically adds Man-Crabs to their pertinent Array List.
     */
    private void addManCrabs() {
        manCrabsArrayList = LoadSave.GetManCrabs();
        System.out.println("Size of Man-Crab Array List: " + manCrabsArrayList.size());
    }

    /**
     * Updates the enemies.
     */
    public void update(int[][] levelData, Player player) {
        updateManCrabs(levelData, player);
    }

    /**
     * Specifically updates all Man-Crabs.
     */
    private void updateManCrabs(int[][] levelData, Player player) {
        for (ManCrab mc : manCrabsArrayList) {
            mc.update(levelData, player);
        }
    }

    /**
     * Draws the enemies.
     *
     * @param g             the Graphics object for drawing
     * @param levelOffset   the offset of the enemy and left/right border
     */
    public void draw(Graphics g, int levelOffset) {
        drawManCrabs(g, levelOffset);
    }

    /**
     * Specifically draws the Man-Crabs.
     *
     * @param g             the Graphics object for drawing
     * @param levelOffset   the offset of a Man-Crab and the left/right border
     */
    private void drawManCrabs(Graphics g, int levelOffset) {
        for (ManCrab mc : manCrabsArrayList) {
            g.drawImage(
                    manCrabArray[mc.getEnemyState()][mc.getAnimationIndex()],
                    ((int) mc.getHitbox().x - MAN_CRAB_OFFSET_X) - levelOffset,
                    (int) (mc.getHitbox().y - MAN_CRAB_OFFSET_Y),
                    MAN_CRAB_WIDTH,
                    MAN_CRAB_HEIGHT,
                    null);
            mc.drawHitbox(g, levelOffset);
        }
    }
}
