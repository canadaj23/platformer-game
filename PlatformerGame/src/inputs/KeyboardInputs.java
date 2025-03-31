package inputs;

import gamestates.GameState;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utils.Constants.Directions.*;

/**
 * This class will hold anything keyboard related.
 */
public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    /**
     * Constructor for KeyboardInputs
     * @param gamePanel the gamePanel object to be passed through
     */
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     *
     * @param e the key typed event
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Does an action based on which key was pressed.
     * @param e the key pressed event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU -> {
                gamePanel.getGame().getMenu().keyPressed(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyPressed(e);
            }
        }
    }

    /**
     *
     * @param e the key released event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU -> {
                gamePanel.getGame().getMenu().keyReleased(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyReleased(e);
            }
        }
    }
}
