package main;

import javax.swing.*;
import java.awt.*;

/**
 * This class will draw the game elements onto a panel that can then be displayed on a GameWindow object.
 */
public class GamePanel extends JPanel {

    public GamePanel() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(100, 100, 200, 50);
    }
}
