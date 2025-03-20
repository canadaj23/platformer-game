package main;

/**
 * This class will take care of most, if not all, of the gameplay.
 */
public class Game {
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
    }
}
