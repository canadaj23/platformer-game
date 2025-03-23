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
            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUp(true);
            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDown(true);
            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeft(true);
            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRight(true);
        }
    }

    /**
     *
     * @param e the key released event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUp(false);
            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDown(false);
            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeft(false);
            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRight(false);
        }
    }
}
