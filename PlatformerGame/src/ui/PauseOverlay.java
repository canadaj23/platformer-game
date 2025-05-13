package ui;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.PauseButtons.*;

public class PauseOverlay {
    private BufferedImage pauseBackgroundImage;
    private int pauseX, pauseY, pauseWidth, pauseHeight;
    private SoundButton musicButton, sfxButton;

    /**
     * Constructor for a PauseOverlay object
     */
    public PauseOverlay() {
        loadPauseBackground();
        createSoundsButtons();
    }

    /**
     * Performs any necessary updates to the pause overlay.
     */
    public void update() {
        musicButton.update();
        sfxButton.update();
    }

    /**
     * Generates the background that surrounds the pause screen buttons.
     */
    private void loadPauseBackground() {
        pauseBackgroundImage = LoadSave.GetSpriteCollection(LoadSave.PAUSE_BACKGROUND);
        pauseWidth = (int) (pauseBackgroundImage.getWidth() * Game.SCALE);
        pauseHeight = (int) (pauseBackgroundImage.getHeight() * Game.SCALE);
        pauseX = (Game.GAME_WIDTH / 2) - (pauseWidth / 2);
        pauseY = (int) (25 * Game.SCALE);
    }

    /**
     * Generates the buttons for the pause screen.
     */
    private void createSoundsButtons() {
        int soundX = (int) (450 * Game.SCALE), musicY = (int) (140 * Game.SCALE), sfxY = (int) (186 * Game.SCALE);

        musicButton = new SoundButton(soundX, musicY, SOUND_B_SIZE, SOUND_B_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_B_SIZE, SOUND_B_SIZE);
    }

    /**
     * Draws the pause overlay that surrounds the pause screen buttons.
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        // Pause background
        g.drawImage(pauseBackgroundImage, pauseX, pauseY, pauseWidth, pauseHeight, null);

        // Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (isOnButton(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isOnButton(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isOnButton(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isOnButton(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isOnButton(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isOnButton(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        }
    }

    private boolean isOnButton(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getButtonBoundary().contains(e.getX(), e.getY());
    }
}
