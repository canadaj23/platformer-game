package ui;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.OtherPauseButtons.*;
import static utils.Constants.UI.PauseButtons.*;
import static utils.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
    private Playing playing;
    private BufferedImage pauseBackgroundImage;
    private int pauseX, pauseY, pauseWidth, pauseHeight;
    private SoundButton musicButton, sfxButton;
    private OtherPausedButton homeButton, restartButton, resumeButton;
    private VolumeButton volumeButton;

    /**
     * Constructor for a PauseOverlay object
     */
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadPauseBackground();
        createSoundsButtons();
        createOtherPauseButtons();
        createVolumeButton();
    }

    /**
     * Performs any necessary updates to the pause overlay.
     */
    public void update() {
        musicButton.update();
        sfxButton.update();
        homeButton.update();
        restartButton.update();
        resumeButton.update();
        volumeButton.update();
    }

    /**
     * Generates the background that surrounds the pause screen buttons.
     */
    private void loadPauseBackground() {
        pauseBackgroundImage = LoadSave.GetSpriteCollection(LoadSave.PAUSE_OVERLAY);
        pauseWidth = (int) (pauseBackgroundImage.getWidth() * Game.SCALE);
        pauseHeight = (int) (pauseBackgroundImage.getHeight() * Game.SCALE);
        pauseX = (Game.GAME_WIDTH / 2) - (pauseWidth / 2);
        pauseY = (int) (25 * Game.SCALE);
    }

    /**
     * Generates the sound and sfx buttons for the pause screen.
     */
    private void createSoundsButtons() {
        int     soundX = (int) (450 * Game.SCALE),
                musicY = (int) (140 * Game.SCALE),
                sfxY = (int) (186 * Game.SCALE);

        musicButton = new SoundButton(soundX, musicY, SOUND_B_SIZE, SOUND_B_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_B_SIZE, SOUND_B_SIZE);
    }

    /**
     * Generates the home, restart, and resume buttons.
     */
    private void createOtherPauseButtons() {
        int     homeX = (int) (313 * Game.SCALE),
                restartX = (int) (387 * Game.SCALE),
                resumeX = (int) (462 * Game.SCALE),
                buttonY = (int) (325 * Game.SCALE);

        homeButton = new OtherPausedButton(
                homeX,
                buttonY,
                OTHER_PAUSE_B_SIZE,
                OTHER_PAUSE_B_SIZE,
                2);
        restartButton = new OtherPausedButton(
                restartX,
                buttonY,
                OTHER_PAUSE_B_SIZE,
                OTHER_PAUSE_B_SIZE,
                1);
        resumeButton = new OtherPausedButton(
                resumeX,
                buttonY,
                OTHER_PAUSE_B_SIZE,
                OTHER_PAUSE_B_SIZE,
                0);
    }

    /**
     * Generates the volume button image.
     */
    private void createVolumeButton() {
        int volumeX = (int) (309 * Game.SCALE), volumeY = (int) (278 * Game.SCALE);

        volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_B_WIDTH, VOLUME_B_HEIGHT);
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

        // Home, restart, and resume buttons
        homeButton.draw(g);
        restartButton.draw(g);
        resumeButton.draw(g);

        // Volume button
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isOnButton(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isOnButton(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isOnButton(e, homeButton)) {
            homeButton.setMousePressed(true);
        } else if (isOnButton(e, restartButton)) {
            restartButton.setMousePressed(true);
        } else if (isOnButton(e, resumeButton)) {
            resumeButton.setMousePressed(true);
        } else if (isOnButton(e, volumeButton)) {
            volumeButton.setMousePressed(true);
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
        } else if (isOnButton(e, homeButton)) {
            GameState.state = GameState.MENU;
            playing.resumeGame();
        } else if (isOnButton(e, restartButton)) {
            playing.resetAll();
            playing.resumeGame();
        } else if (isOnButton(e, resumeButton)) {
            playing.resumeGame();
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        homeButton.resetBools();
        restartButton.resetBools();
        resumeButton.resetBools();
        volumeButton.resetBools();
    }

    /**
     * Reset sound button booleans when the mouse moves off them.
     * @param e the event of moving off a sound button
     */
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        homeButton.setMouseOver(false);
        restartButton.setMouseOver(false);
        resumeButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isOnButton(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isOnButton(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isOnButton(e, homeButton)) {
            homeButton.setMouseOver(true);
        } else if (isOnButton(e, restartButton)) {
            restartButton.setMouseOver(true);
        } else if (isOnButton(e, resumeButton)) {
            resumeButton.setMouseOver(true);
        } else if (isOnButton(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    /**
     * Determines whether the mouse is on a button on the pause screen.
     * @param e the event of moving on a button
     * @param pauseButton the button object
     * @return true/false whether the mouse is on a button
     */
    private boolean isOnButton(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getButtonBoundary().contains(e.getX(), e.getY());
    }
}
