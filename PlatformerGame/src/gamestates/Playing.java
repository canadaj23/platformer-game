package gamestates;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import ui.GameOverScreen;
import ui.LevelCompletedOverlay;
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
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false, gameOver, levelCompleted;
    private int numberLevelsCompleted = 0;

    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLevelOffsetX;

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
        calculateLevelOffset();
        loadLevelStart();
    }

    /**
     * Initializes all the classes used for the playing state of the game.
     * This could be for a player, enemy, handler, etc.
     */
    private void initClasses() {
        initHandlers();
        initPlayer();
        initOverlays();
    }

    /**
     * Initializes the handlers of various classes.
     */
    private void initHandlers() {
        levelHandler = new LevelHandler(game);
        enemyHandler = new EnemyHandler( this);
    }

    /**
     * Initializes the player.
     */
    private void initPlayer() {
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
        player.setPlayerSpawnPoint(levelHandler.getCurrentLevel().getPlayerSpawnPoint());
    }

    /**
     * Initializes the many overlays for UI.
     */
    private void initOverlays() {
        pauseOverlay = new PauseOverlay(this);
        gameOverScreen = new GameOverScreen(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
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
     * Calculates the current level's x-offset.
     */
    private void calculateLevelOffset() {
        maxLevelOffsetX = levelHandler.getCurrentLevel().getMaxLevelOffsetX();
    }

    /**
     * Load certain aspects at the start of the level.
     */
    private void loadLevelStart() {
        enemyHandler.loadEnemies(levelHandler.getCurrentLevel());
    }

    /**
     * Takes the player to the next level.
     */
    public void loadNextLevel() {
        resetAll();
        levelHandler.loadNextLevel();
        player.setPlayerSpawnPoint(levelHandler.getCurrentLevel().getPlayerSpawnPoint());
    }

    /**
     * Updates any game related elements
     */
    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (levelCompleted) {
            levelCompletedOverlay.updateLevelCompletedButtons();
        } else if (!gameOver){
            updateGame();
        }
    }

    /**
     * Update the game when not over or level is completed.
     */
    private void updateGame() {
        levelHandler.updateLevel();
        updateEntities();
        checkBorderProximity();
    }

    /**
     * Updates the player and enemies.
     */
    private void updateEntities() {
        player.updatePlayer();
        enemyHandler.updateEnemies(levelHandler.getCurrentLevel().getLevelData(), player);
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

        if (xLevelOffset >= maxLevelOffsetX) {
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
        drawGameElements(g);

        if (paused) {
            drawPauseOverlay(g);
        } else if (gameOver) {
            gameOverScreen.drawGameOverScreen(g);
        } else if (levelCompleted) {
            drawLevelCompletedOverlay(g);
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
     * Draws the level, player, enemies, etc.
     * @param g the Graphics object for drawing
     */
    private void drawGameElements(Graphics g) {
        levelHandler.drawLevel(g, xLevelOffset);
        player.renderPlayer(g, xLevelOffset);
        enemyHandler.drawEnemies(g, xLevelOffset);
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
     * Draws the level completed screen when level finishes.
     */
    private void drawLevelCompletedOverlay(Graphics g) {
        levelCompletedOverlay.drawLevelCompletedButtons(g);
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
        gameOver = false;
        paused = false;
        levelCompleted = false;
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
            } else if (levelCompleted) {
                levelCompletedOverlay.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseReleased(e);
            } else if (levelCompleted) {
                levelCompletedOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseMoved(e);
            } else if (levelCompleted) {
                levelCompletedOverlay.mouseMoved(e);
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

    /**
     * @return the enemy handler
     */
    public EnemyHandler getEnemyHandler() {
        return enemyHandler;
    }

    /**
     * @return the level handler
     */
    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    /**
     * Sets maxLevelOffsetX to a new value.
     * @param xLevelOffset the new value for maxLevelOffsetX
     */
    public void setMaxLevelOffsetX(int xLevelOffset) {
        this.maxLevelOffsetX = xLevelOffset;
    }

    /**
     * Sets the level to be/not to be completed.
     * @param levelCompleted true/false
     */
    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
    }
}
