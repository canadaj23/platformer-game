package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.OtherPauseButtons.*;

public class OtherPausedButton extends PauseButton {
    private BufferedImage[] otherPauseButtonImages;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;


    /**
     * Constructor for an OtherPauseButton object
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width of an OtherPauseButton button
     * @param height the height of an OtherPauseButton button
     */
    public OtherPausedButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadOtherPauseButtonImages();
    }

    /**
     * Creates the resume, restart, and home images.
     */
    private void loadOtherPauseButtonImages() {
        BufferedImage temp = LoadSave.GetSpriteCollection(LoadSave.OTHER_PAUSED_BUTTONS);
        otherPauseButtonImages = new BufferedImage[3];

        for (int i = 0; i < otherPauseButtonImages.length; i++) {
            otherPauseButtonImages[i] = temp.getSubimage(
                    i * DEFAULT_OTHER_PAUSE_B_SIZE,
                    rowIndex * DEFAULT_OTHER_PAUSE_B_SIZE,
                    DEFAULT_OTHER_PAUSE_B_SIZE,
                    DEFAULT_OTHER_PAUSE_B_SIZE);
        }
    }

    /**
     * Updates necessary variables for the button.
     */
    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    /**
     * Draws the button onto the screen.
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.drawImage(otherPauseButtonImages[index], x, y, OTHER_PAUSE_B_SIZE, OTHER_PAUSE_B_SIZE, null);
    }

    /**
     * Resets the other paused button's booleans.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    /**
     *
     * @return whether the mouse is on the button
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
     * @return whether the mouse presses the button
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
}
