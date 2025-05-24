package ui;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.OtherPauseButtons.*;

public class LevelCompletedOverlay {
    private Playing playing;
    private OtherPausedButton menuButton, nextButton;
    private BufferedImage levelCompletedOverlayImage;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;

    /**
     * Constructor for a LevelCompletedOverlay object.
     * @param playing the Playing object used for continuing
     * or going to the main menu
     */
    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initLevelCompletedOverlayImage();
        initLevelCompletedButtons();
    }

    /**
     * Initializes the level completed overlay image to be displayed.
     */
    private void initLevelCompletedOverlayImage() {
        levelCompletedOverlayImage = LoadSave.GetSpriteCollection(LoadSave.LEVEL_COMPLETED_OVERLAY);
        backgroundWidth = (int) (levelCompletedOverlayImage.getWidth() * Game.SCALE);
        backgroundHeight = (int) (levelCompletedOverlayImage.getHeight() * Game.SCALE);
        backgroundX = (Game.GAME_WIDTH / 2) - (backgroundWidth / 2);
        backgroundY = (int) (75 * Game.SCALE);
    }

    /**
     * Initializes the return to menu and next buttons
     * to be placed on the level completed overlay.
     */
    private void initLevelCompletedButtons() {
        int menuButtonX = (int) (330 * Game.SCALE);
        int nextButtonX = (int) (445 * Game.SCALE);
        int buttonY = (int) (195 * Game.SCALE);
        nextButton = new OtherPausedButton(nextButtonX, buttonY, OTHER_PAUSE_B_SIZE, OTHER_PAUSE_B_SIZE, 0);
        menuButton = new OtherPausedButton(menuButtonX, buttonY, OTHER_PAUSE_B_SIZE, OTHER_PAUSE_B_SIZE, 2);
    }

    /**
     * Draws the menu and next buttons onto the level completed overlay.
     * @param g the Graphics object for drawing
     */
    public void drawLevelCompletedButtons(Graphics g) {
        g.drawImage(
                levelCompletedOverlayImage,
                backgroundX,
                backgroundY,
                backgroundWidth,
                backgroundHeight,
                null);
        nextButton.draw(g);
        menuButton.draw(g);
    }

    /**
     * Updates the menu and next buttons when interacted with.
     */
    public void updateLevelCompletedButtons() {
        nextButton.update();
        menuButton.update();
    }

    private boolean isOnButton(OtherPausedButton otherPausedButton, MouseEvent e) {
        return otherPausedButton.getButtonBoundary().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        nextButton.setMouseOver(false);
        menuButton.setMouseOver(false);

        if (isOnButton(menuButton, e)) {
            menuButton.setMouseOver(true);
        } else if (isOnButton(nextButton, e)) {
            nextButton.setMouseOver(true);
        }
    }
    public void mouseReleased(MouseEvent e) {
        if (isOnButton(menuButton, e)) {
            if (menuButton.isMousePressed()) {
                playing.resetAll();
                GameState.state = GameState.MENU;
            }
        } else if (isOnButton(nextButton, e)) {
            if (nextButton.isMousePressed()) {
                playing.loadNextLevel();
            }
        }
        menuButton.resetBools();
        nextButton.resetBools();
    }
    public void mousePressed(MouseEvent e) {
        if (isOnButton(menuButton, e)) {
            menuButton.setMousePressed(true);
        } else if (isOnButton(nextButton, e)) {
            nextButton.setMousePressed(true);
        }
    }
}
