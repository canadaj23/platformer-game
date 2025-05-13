package ui;

import gamestates.GameState;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.MenuButtons.*;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = MENU_B_WIDTH / 2;
    private GameState state;
    private BufferedImage[] images;
    private boolean mouseOver, mousePressed;
    private Rectangle buttonBoundary;

    /**
     * Constructor for a MenuButton object
     * @param xPos the x position
     * @param yPos the y position
     * @param rowIndex the row index of an image
     * @param state the current game state
     */
    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;

        loadImages();
        initBoundary();
    }

    /**
     * Loads the menu button images to be interacted with.
     */
    private void loadImages() {
        images = new BufferedImage[3];

        BufferedImage temp = LoadSave.GetSpriteCollection(LoadSave.MENU_BUTTONS);

        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(
                    i * DEFAULT_MENU_B_WIDTH,
                    rowIndex * DEFAULT_MENU_B_HEIGHT,
                    DEFAULT_MENU_B_WIDTH,
                    DEFAULT_MENU_B_HEIGHT);
        }
    }

    /**
     * Creates a hitbox for a menu button.
     */
    private void initBoundary() {
        buttonBoundary = new Rectangle(xPos - xOffsetCenter, yPos, MENU_B_WIDTH, MENU_B_HEIGHT);
    }

    /**
     * Draws the button images on the screen.
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, MENU_B_WIDTH, MENU_B_HEIGHT, null);
    }

    /**
     * Updates the index based on the mouse event.
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
     * Checks if the mouse is over a button.
     * @return whether mouse is over a button
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Sets mouseOver to true/false
     * @param mouseOver
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Checks if the mouse pressed a button
     * @return whether a button was pressed
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Sets mousePressed to true/false
     * @param mousePressed
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     *
     * @return the boundary of the button
     */
    public Rectangle getButtonBoundary() {
        return buttonBoundary;
    }

    public void changeGameState() {
        GameState.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
