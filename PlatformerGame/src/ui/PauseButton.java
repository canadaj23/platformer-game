package ui;

import java.awt.*;

public class PauseButton {
    protected int x, y, width, height;
    protected Rectangle buttonBoundary;

    /**
     * Constructor for a PauseButton object
     *
     * @param x      the x position
     * @param y      the y position
     * @param width  the width of a pause button
     * @param height the height of a pause button
     */
    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBoundary();
    }

    /**
     * Creates a hitbox for a button in the pause state.
     */
    private void createBoundary() {
        buttonBoundary = new Rectangle(x, y, width, height);
    }

    /**
     *
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x position.
     * @param x the new x position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y position.
     * @param y the new y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return the button width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the button width.
     * @param width the new button width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return the button height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the button height.
     * @param height the new button height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @return the button's hitbox
     */
    public Rectangle getButtonBoundary() {
        return buttonBoundary;
    }
}
