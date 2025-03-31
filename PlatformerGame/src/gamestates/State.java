package gamestates;

import main.Game;

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
     *
     * @return the Game object used by the game state
     */
    public Game getGame() {
        return game;
    }
}
