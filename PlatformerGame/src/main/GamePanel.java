package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

/**
 * This class will draw the game elements onto a panel that can then be displayed on a GameWindow object.
 */
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int xDelta = 100, yDelta = 100;

    public GamePanel() {
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
        repaint();
    }

    /**
     * Adds a value to the yDelta (changing y position).
     * @param value changes value of yDelta
     */
    public void changeYDelta(int value) {
        this.yDelta += value;
        repaint();
    }

    /**
     * Changes the x- and y-coordinates.
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }

    /**
     * Paints a display to be added to a frame.
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(xDelta, yDelta, 200, 50);
    }
}
