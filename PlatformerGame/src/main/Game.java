package main;

import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.*;

/**
 * This class will take care of most, if not all, of the gameplay.
 */
public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameLoopThread;
    private final int FPS_TARGET = 120, UPS_TARGET = 200;

    private Menu menu;
    private Playing playing;

    public final static float SCALE = 2f;
    public final static int DEFAULT_TILES_SIZE = 32;
    public final static int TILES_WIDTH = 26;
    public final static int TILES_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (DEFAULT_TILES_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;

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
        menu = new Menu(this);
        playing = new Playing(this);
    }

    /**
     * Updates any game related elements
     */
    private void update() {
        switch (GameState.state) {
            case MENU -> {
                menu.update();
            }
            case PLAYING -> {
                playing.update();
            }
        }
    }

    /**
     * Renders any game element onto the screen.
     * @param g the Graphics object used for drawing
     */
    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU -> {
                menu.draw(g);
            }
            case PLAYING -> {
                playing.draw(g);
            }
        }
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
     * Do something if the game window is not focused.
     */
    public void windowFocusLost() {
        if (GameState.state == GameState.PLAYING) {
            playing.getPlayer().resetPlayerDirBooleans();
        }
    }

    /**
     *
     * @return the Menu object
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     *
     * @return the Playing object
     */
    public Playing getPlaying() {
        return playing;
    }
}
