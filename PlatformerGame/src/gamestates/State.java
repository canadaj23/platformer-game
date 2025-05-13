package gamestates;

import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;

    /**
     * Constructor for a State object that will store a Game object.
     * @param game the Game object to be stored
     */
    public State(Game game) {
        this.game = game;
    }

    /**
     * Determines if the mouse is on the button.
     * @param e the mouse event (hovering over button)
     * @param menuButton the menuButton in question
     * @return whether the mouse is within the button's hitbox
     */
    public boolean isOnButton(MouseEvent e, MenuButton menuButton) {
        return menuButton.getButtonBoundary().contains(e.getX(), e.getY());
    }

    /**
     *
     * @return the Game object used by the game state
     */
    public Game getGame() {
        return game;
    }
}
