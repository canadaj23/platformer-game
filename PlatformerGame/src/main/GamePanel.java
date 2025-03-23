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
    private Game game;

    /**
     * Constructor for GamePanel
     */
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Sets the size of the panel to be displayed.
     */
    private void setPanelSize() {
        setPreferredSize(new Dimension(1280, 800));
    }

    /**
     * Updates logical aspects of the game.
     */
    public void updateGame() {
    }

    /**
     * Paints a display to be added to a frame.
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    /**
     * Returns a Game object to be used in other classes.
     * @return the Game object
     */
    public Game getGame() {
        return game;
    }
}
