package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class will draw the game elements onto a panel that can then be displayed on a GameWindow object.
 */
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image, subImage;

    /**
     * Constructor for GamePanel
     */
    public GamePanel() {
        importImage();

        setPanelSize();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension(1280, 800));
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
        xDelta = x;
        yDelta = y;
    }

    /**
     * Paints a display to be added to a frame.
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        subImage = image.getSubimage(1 * 64, 8 * 40, 64, 40);
        g.drawImage(subImage, (int) xDelta, (int) yDelta, 128, 80, null);
    }
}
