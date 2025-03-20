package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * This class will draw the game elements onto a panel that can then be displayed on a GameWindow object.
 */
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir = 1.5f, yDir = 1.5f;
    private Color color = new Color(150, 20, 90);
    Random random;

    /**
     * Constructor for GamePanel
     */
    public GamePanel() {
        random = new Random();

        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Adds a value to the xDelta (changing x position).
     * @param value changes value of xDelta
     */
    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    /**
     * Adds a value to the yDelta (changing y position).
     * @param value changes value of yDelta
     */
    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    /**
     * Changes the x- and y-coordinates.
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    /**
     * Paints a display to be added to a frame.
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateRectangle();
        g.setColor(color);
        g.fillRect((int) xDelta, (int) yDelta, 200, 50);
    }

    /**
     * "Moves" the rectangle without keyboard or mouse inputs around the frame.
     */
    private void updateRectangle() {
        xDelta += xDir;
        if (xDelta > 400 || xDelta < 0) {
            xDir = -xDir;
            color = getRandomColor();
        }

        yDelta += yDir;
        if (yDelta > 400 || yDelta < 0) {
            yDir = -yDir;
            color = getRandomColor();
        }
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
