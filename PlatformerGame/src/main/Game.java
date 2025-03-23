package main;

import entities.Player;

import java.awt.*;

/**
 * This class will take care of most, if not all, of the gameplay.
 */
public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameLoopThread;
    private final int FPS_TARGET = 120, UPS_TARGET = 200;
    private Player player;

    /**
     * Constructor for a Game object
     */
    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    /**
     * Initializes then starts a game loop thread.
     */
    private void startGameLoop() {
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    /**
     * Initializes all the classes used for the game.
     * This could be for a player, enemy, handler, etc.
     */
    private void initClasses() {
        player = new Player(200, 200);
    }

    /**
     * Updates any game related elements
     */
    private void update() {
        player.updatePlayer();
    }

    /**
     * Renders any game element onto the screen.
     * @param g the Graphics object used for drawing
     */
    public void render(Graphics g) {
        player.renderPlayer(g);
    }

    /**
     * Creates a game loop with an FPS counter.
     */
    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_TARGET;
        double timePerUpdate = 1_000_000_000.0 / UPS_TARGET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0, deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            // Increment number of updates when necessary
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // Increment number of frames when necessary
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Determine FPS
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("=======");
                System.out.println(frames + " FPS");
                System.out.println(updates + " UPS");
                System.out.println("=======");
                frames = 0;
                updates = 0;
            }
        }
    }

    /**
     * Stop moving the player if the game window is not focused.
     */
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    /**
     * Returns the player object to be used in other classes.
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }
}
