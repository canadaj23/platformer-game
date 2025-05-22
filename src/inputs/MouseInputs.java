package inputs;

import gamestates.GameState;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class will hold anything mouse related.
 */
public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    /**
     * Constructor for MouseInputs
     * @param gamePanel the gamePanel object to be passed through
     */
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mouseClicked(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> {
                gamePanel.getGame().getMenu().mousePressed(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> {
                gamePanel.getGame().getMenu().mouseReleased(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mouseDragged(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> {
                gamePanel.getGame().getMenu().mouseMoved(e);
            }
            case PLAYING -> {
                gamePanel.getGame().getPlaying().mouseMoved(e);
            }
        }
    }
}
