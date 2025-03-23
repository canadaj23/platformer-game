package inputs;

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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D -> gamePanel.setMoving(false);
        }
    }

    /**
     *
     * @param e the key released event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> gamePanel.setDirection(UP);
            case KeyEvent.VK_S -> gamePanel.setDirection(DOWN);
            case KeyEvent.VK_A -> gamePanel.setDirection(LEFT);
            case KeyEvent.VK_D -> gamePanel.setDirection(RIGHT);
        }
    }
}
