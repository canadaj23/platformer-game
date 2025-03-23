package main;

import javax.swing.*;

/**
 * This class draws a window for the game to be displayed on.
 */
public class GameWindow extends JFrame {
    private JFrame jFrame;

    /**
     * Constructor for GameWindow
     * @param gamePanel the gamePanel object to be passed through
     */
    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
