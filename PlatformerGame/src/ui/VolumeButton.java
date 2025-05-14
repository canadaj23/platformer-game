package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton {
    private BufferedImage[] volumeButtonImages;
    private BufferedImage sliderImage;
    private int index = 0;
    boolean mouseOver, mousePressed;
    private int volumeButtonX, minimumX, maximumX;

    /**
     * Constructor for a VolumeButton object
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width of a volume button
     * @param height the height of a volume button
     */
    public VolumeButton(int x, int y, int width, int height) {
        super(x + (width / 2), y, VOLUME_B_WIDTH, height);
        buttonBoundary.x -= (VOLUME_B_WIDTH / 2);
        volumeButtonX = x + (width / 2);
        this.x = x;
        this.width = width;
        minimumX = x + (VOLUME_B_WIDTH / 2);
        maximumX = x + width - (VOLUME_B_WIDTH / 2);
        loadVolumeImages();
    }

    /**
     * Creates the resume, restart, and home images.
     */
    private void loadVolumeImages() {
        BufferedImage temp = LoadSave.GetSpriteCollection(LoadSave.VOLUME_BUTTONS);
        volumeButtonImages = new BufferedImage[3];

        for (int i = 0; i < volumeButtonImages.length; i++) {
            volumeButtonImages[i] = temp.getSubimage(
                    i * DEFAULT_VOLUME_B_WIDTH,
                    0,
                    DEFAULT_VOLUME_B_WIDTH,
                    DEFAULT_VOLUME_B_HEIGHT);
        }

        sliderImage = temp.getSubimage(
                3 * DEFAULT_VOLUME_B_WIDTH,
                0,
                DEFAULT_SLIDER_B_WIDTH,
                DEFAULT_VOLUME_B_HEIGHT);
    }

    /**
     * Updates necessary variables for the button.
     */
    public void update() {
        index = 0;
        if (isMouseOver()) {
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
        g.drawImage(sliderImage, x, y, width, height, null);
        g.drawImage(
                volumeButtonImages[index],
                volumeButtonX - (VOLUME_B_WIDTH / 2),
                y,
                VOLUME_B_WIDTH,
                height,
                null);
    }


    public void changeX(int x) {
        volumeButtonX = x < minimumX ? minimumX : Math.min(x, maximumX);

        buttonBoundary.x = volumeButtonX - (VOLUME_B_WIDTH / 2);
    }

    /**
     * Resets the volume button's booleans.
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
