package gamestates;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import ui.GameOverScreen;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.Environment.*;

/**
 * This class holds all the code needed for while in the playing state.
 */
public class Playing extends State implements StateMethods {
    private Player player;
    private LevelHandler levelHandler;
    private EnemyHandler enemyHandler;
    private PauseOverlay pauseOverlay;
    private GameOverScreen gameOverScreen;
    private boolean paused = false, gameOver;

    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = levelTilesWide - Game.WIDTH_IN_TILES;
    private int maxLevelOffsetX = maxTilesOffset * Game.SIZE_IN_TILES;

    private BufferedImage playingBackgroundImage, bigCloudImage, smallCloudImage;
    private int[] smallCloudsYPosArray;
    private Random random = new Random();

    /**
     * Constructor for a Playing object that will store a Game object.
     *
     * @param game the Game object to be stored
     */
    public Playing(Game game) {
        super(game);
        initClasses();
        initBackground();
    }

    /**
     * Initializes all the classes used for the playing state of the game.
     * This could be for a player, enemy, handler, etc.
     */
    private void initClasses() {
        loadHandlers();
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        gameOverScreen = new GameOverScreen(this);
    }

    /**
     * Loads the handlers of various classes.
     */
    private void loadHandlers() {
        levelHandler = new LevelHandler(game);
        enemyHandler = new EnemyHandler( this);
    }

    /**
     * Creates the background and pertinent images.
     */
    private void initBackground() {
        playingBackgroundImage = LoadSave.GetSpriteCollection(LoadSave.PLAYING_BACKGROUND);
        createClouds();
    }

    /**
     * Generates small and big clouds for the playing background.
     */
    private void createClouds() {
        bigCloudImage = LoadSave.GetSpriteCollection(LoadSave.BIG_CLOUDS);
        smallCloudImage = LoadSave.GetSpriteCollection(LoadSave.SMALL_CLOUDS);
        generateSmallCloudYPositions();
    }

    /**
     * Generates and stores random y-positions for small clouds.
     */
    private void generateSmallCloudYPositions() {
        smallCloudsYPosArray = new int[8];
        for (int i = 0; i < smallCloudsYPosArray.length; i++) {
            smallCloudsYPosArray[i] = (int) (90 * Game.SCALE) + random.nextInt((int) (100 * Game.SCALE));
        }
    }

    /**
     * Updates any game related elements
     */
    @Override
    public void update() {
        if (!paused && !gameOver) {
            levelHandler.updateLevel();
            player.updatePlayer();
            enemyHandler.update(levelHandler.getCurrentLevel().getLevelData(), player);
            checkBorderProximity();
        } else {
            pauseOverlay.update();
        }
    }

    /**
     * Changes xLevelOffset once the player reaches the left or right borders.
     */
    private void checkBorderProximity() {
        int playerX = (int) player.getEntityHitbox().x;
        int difference = playerX - xLevelOffset;

        if (difference > rightBorder) {
            xLevelOffset += (difference - rightBorder);
        } else if (difference < leftBorder) {
            xLevelOffset += (difference - leftBorder);
        }

        if (xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    /**
     * Renders any game element onto the screen.
     * @param g the Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {
        drawPlayingBackGround(g);

        levelHandler.drawLevel(g, xLevelOffset);
        player.renderPlayer(g, xLevelOffset);
        enemyHandler.drawEnemies(g, xLevelOffset);

        if (paused) {
            drawPauseOverlay(g);
        } else if (gameOver) {
            gameOverScreen.drawGameOverScreen(g);
        }
    }

    /**
     * Draws both the playing background and the clouds.
     * @param g the Graphics object for drawing
     */
    private void drawPlayingBackGround(Graphics g) {
        g.drawImage(playingBackgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g);
    }

    /**
     * Draws the pause overlay and tints the screen.
     * @param g the Graphics object for drawing
     */
    private void drawPauseOverlay(Graphics g) {
        tintWhenPaused(g);
        pauseOverlay.draw(g);
    }

    /**
     * Adds some tinting to the game to signify the game is paused.
     * @param g the Graphics object for drawing
     */
    private void tintWhenPaused(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
    }

    /**
     * Draws both small and big clouds for the background.
     */
    private void drawClouds(Graphics g) {
        drawBigClouds(g);
        drawSmallClouds(g);
    }

    /**
     * Draws a few big clouds.
     * @param g the Graphics object for drawing
     */
    private void drawBigClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(
                    bigCloudImage,
                    i * BIG_CLOUD_WIDTH - (int) (xLevelOffset * 0.3),
                    (int) (204 * Game.SCALE),
                    BIG_CLOUD_WIDTH,
                    BIG_CLOUD_HEIGHT,
                    null);
        }
    }

    /**
     * Draws a few small clouds with a random y-position.
     * @param g the Graphics object for drawing
     */
    private void drawSmallClouds(Graphics g) {
        for (int i = 0; i < smallCloudsYPosArray.length; i++) {
            g.drawImage(
                    smallCloudImage,
                    i * 4 * SMALL_CLOUD_WIDTH - (int) (xLevelOffset * 0.7),
                    smallCloudsYPosArray[i],
                    SMALL_CLOUD_WIDTH,
                    SMALL_CLOUD_HEIGHT,
                    null);
        }
    }

    /**
     * Resumes the game.
     */
    public void resumeGame() {
        paused = false;
    }

    /**
     * Checks if an enemy was hit by the player.
     */
    public void checkEnemyHitByPlayer(Rectangle2D.Float playerAttackHitbox) {
        enemyHandler.checkEnemyHitByPlayer(playerAttackHitbox);
    }

    /**
     * Makes the game unplayable until the player restarts.
     * @param gameOver boolean to determine if the game is over
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Resets everything to its default values when restarting the game or returning to the main menu.
     */
    public void resetAll() {
        // TODO: implement resetting all game-related values
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyHandler.resetAllEnemies();
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseDragged(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setPlayerAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseMoved(e);
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverScreen.keyPressed(e);
        }
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_SPACE -> player.setJump(true);
                case KeyEvent.VK_ESCAPE -> paused = !paused;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(false);
                case KeyEvent.VK_D -> player.setRight(false);
                case KeyEvent.VK_SPACE -> player.setJump(false);
            }
        }
    }

    /**
     * Stop moving the player if the game window is not focused.
     */
    public void windowFocusLost() {
        player.resetPlayerDirBooleans();
    }

    /**
     * Returns the player object to be used in other classes.
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }
}
