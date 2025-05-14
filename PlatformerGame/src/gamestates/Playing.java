package gamestates;

import entities.Player;
import levels.LevelHandler;
import main.Game;
import ui.PauseOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * This class holds all the code needed for while in the playing state.
 */
public class Playing extends State implements StateMethods {
    private Player player;
    private LevelHandler levelHandler;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;

    /**
     * Constructor for a Playing object that will store a Game object.
     *
     * @param game the Game object to be stored
     */
    public Playing(Game game) {
        super(game);
        initClasses();
    }

    /**
     * Initializes all the classes used for the playing state of the game.
     * This could be for a player, enemy, handler, etc.
     */
    private void initClasses() {
        levelHandler = new LevelHandler(game);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    /**
     * Updates any game related elements
     */
    @Override
    public void update() {
        if (!paused) {
            levelHandler.updateLevel();
            player.updatePlayer();
        } else {
            pauseOverlay.update();
        }
    }

    /**
     * Renders any game element onto the screen.
     * @param g the Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {
        levelHandler.drawLevel(g);
        player.renderPlayer(g);
        if (paused) {
            pauseOverlay.draw(g);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setPlayerAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_SPACE -> player.setJump(true);
            case KeyEvent.VK_ESCAPE -> paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_SPACE -> player.setJump(false);
        }
    }

    /**
     * Resumes the game.
     */
    public void resumeGame() {
        paused = false;
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
