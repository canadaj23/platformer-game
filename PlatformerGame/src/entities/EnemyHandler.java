package entities;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.Enemy.*;

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
     * Loads enemies from their ArrayLists to be displayed.
     */
    public void loadEnemies(Level level) {
        loadManCrabs(level);
    }

    /**
     * Specifically loads Man-Crabs from their pertinent ArrayList.
     */
    private void loadManCrabs(Level level) {
        manCrabsArrayList = level.getManCrabsArrayList();
        if (!manCrabsArrayList.isEmpty()) {
            System.out.println(
                    "Number of Man-Crabs for Level " +
                            (playing.getLevelHandler().getLevelIndex() + 1) + ": " + manCrabsArrayList.size());
        }
    }

    /**
     * Updates the enemies.
     */
    public void updateEnemies(int[][] levelData, Player player) {
        updateManCrabs(levelData, player);
    }

    /**
     * Specifically updates all Man-Crabs.
     */
    private void updateManCrabs(int[][] levelData, Player player) {
        boolean areAnyActive = false;
        for (ManCrab mc : manCrabsArrayList) {
            if (mc.isActive()) {
                mc.updateManCrab(levelData, player);
                areAnyActive = true;
            }
        }

        if (!areAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    /**
     * Draws the enemies.
     *
     * @param g             the Graphics object for drawing
     * @param xLevelOffset   the offset of the enemy and left/right border
     */
    public void drawEnemies(Graphics g, int xLevelOffset) {
        drawManCrabs(g, xLevelOffset);
    }

    /**
     * Specifically draws the Man-Crabs.
     *
     * @param g             the Graphics object for drawing
     * @param xLevelOffset   the offset of a Man-Crab and the left/right border
     */
    private void drawManCrabs(Graphics g, int xLevelOffset) {
        for (ManCrab mc : manCrabsArrayList) {
            if (mc.isActive()) {
                g.drawImage(
                        manCrabArray[mc.getEntityState()][mc.getEntityAnimationIndex()],
                        (int) mc.getEntityHitbox().x - xLevelOffset - MAN_CRAB_OFFSET_X + mc.flipManCrabX(),
                        (int) (mc.getEntityHitbox().y - MAN_CRAB_OFFSET_Y),
                        MAN_CRAB_WIDTH * mc.flipManCrabW(),
                        MAN_CRAB_HEIGHT,
                        null);
                mc.drawEntityHitbox(g, xLevelOffset);
                mc.drawEntityAttackHitbox(g, xLevelOffset);
            }
        }
    }

    /**
     * Checks if the enemy was hit by the player.
     * @param playerAttackHitbox the player's attack hitbox
     */
    public void checkEnemyHitByPlayer(Rectangle2D.Float playerAttackHitbox) {
        checkManCrabHitByPlayer(playerAttackHitbox);
    }

    /**
     * Specifically checks if a Man-Crab was hit by the player.
     * @param playerAttackHitbox the player's attack hitbox
     */
    private void checkManCrabHitByPlayer(Rectangle2D.Float playerAttackHitbox) {
        for (ManCrab mc : manCrabsArrayList) {
            if (mc.isActive()) {
                if (playerAttackHitbox.intersects(mc.getEntityHitbox())) {
                    mc.takeDamage(10);
                    return;
                }
            }
        }
    }

    /**
     * Resets all enemies' attributes to their default values.
     */
    public void resetAllEnemies() {
        resetManCrabs();
    }

    /**
     * Specifically resets all Man-Crabs' attributes to their default values.
     */
    private void resetManCrabs() {
        for (ManCrab mc : manCrabsArrayList) {
            mc.resetEnemy();
        }
    }
}
