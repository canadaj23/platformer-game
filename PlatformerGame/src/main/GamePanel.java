package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

/**
 * This class will draw the game elements onto a panel that can then be displayed on a GameWindow object.
 */
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;

    /**
     * Constructor for GamePanel
     * @param game the Game object used for various purposes
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
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        // For debugging
//        displayGameDimensions();
    }

    /**
     * Displays the width and height of the game.
     */
    private void displayGameDimensions() {
        System.out.println("Panel Size: " + GAME_WIDTH + " x " + GAME_HEIGHT);
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
