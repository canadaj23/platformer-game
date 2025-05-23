package ui;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverScreen {
    private Playing playing;

    /**
     * Constructor for the GameOverScreen object.
     * @param playing the game state to switch from
     */
    public GameOverScreen(Playing playing) {
        this.playing = playing;
    }

    /**
     * Draws the Game Over screen after the player wins/loses.
     * @param g the Graphics object for drawing
     */
    public void drawGameOverScreen(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString("Game Over!", Game.GAME_WIDTH / 2, 150);
        g.drawString("Press ESC to return to the Main Menu.", Game.GAME_WIDTH / 2, 300);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
