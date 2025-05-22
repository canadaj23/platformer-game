package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImages;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;

    /**
     * Constructor for a SoundButton object
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width of a sound button
     * @param height the height of a sound button
     */
    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        loadSoundImages();
    }

    /**
     * Creates the sound images.
     */
    private void loadSoundImages() {
        BufferedImage temp = LoadSave.GetSpriteCollection(LoadSave.SOUND_BUTTONS);
        soundImages = new BufferedImage[2][3];

        for (int j = 0; j < soundImages.length; j++) {
            for (int i = 0; i < soundImages[j].length; i++) {
                soundImages[j][i] = temp.getSubimage(
                        i * DEFAULT_SOUND_B_SIZE,
                        j * DEFAULT_SOUND_B_SIZE,
                        DEFAULT_SOUND_B_SIZE,
                        DEFAULT_SOUND_B_SIZE);
            }
        }
    }

    /**
     * Updates necessary variables for the button.
     */
    public void update() {
        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        colIndex = 0;
        if (mouseOver) {
            colIndex = 1;
        }
        if (mousePressed) {
            colIndex = 2;
        }
    }

    /**
     * Resets some booleans of the button object.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    /**
     * Draws the button onto the screen.
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.drawImage(soundImages[rowIndex][colIndex], x, y, width, height, null);
    }

    /**
     *
     * @return whether the mouse is over the button
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Sets mouseOver to true/false.
     * @param mouseOver true/false
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     *
     * @return whether the mouse was pressed
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Sets mousePressed to true/false.
     * @param mousePressed true/false
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     *
     * @return whether the sound is muted
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Sets muted to true/false.
     * @param muted true/false
     */
    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
