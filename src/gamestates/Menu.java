package gamestates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * This class holds all the code needed for while in the menu state.
 */
public class Menu extends State implements StateMethods {
    private MenuButton[] menuButtons = new MenuButton[3];
    private BufferedImage menuOverlayImage, menuBackgroundImage;
    private int menuX, menuY, menuWidth, menuHeight;

    /**
     * Constructor for a Menu object that will store a Game object.
     *
     * @param game the Game object to be stored
     */
    public Menu(Game game) {
        super(game);

        loadMenuButtons();
        loadMenuBackground();

        menuBackgroundImage = LoadSave.GetSpriteCollection(LoadSave.MENU_BACKGROUND);
    }

    /**
     * Loads all menu buttons to be interacted with.
     */
    private void loadMenuButtons() {
        // play button
        menuButtons[0] = new MenuButton(
                Game.GAME_WIDTH / 2,
                (int) (150 * Game.SCALE),
                0,
                GameState.PLAYING);

        // options button
        menuButtons[1] = new MenuButton(
                Game.GAME_WIDTH / 2,
                (int) (220 * Game.SCALE),
                1,
                GameState.OPTIONS);

        // quit button
        menuButtons[2] = new MenuButton(
                Game.GAME_WIDTH / 2,
                (int) (290 * Game.SCALE),
                2,
                GameState.QUIT);
    }

    /**
     * Loads a menu background so it's not surrounded by just white.
     */
    private void loadMenuBackground() {
        menuOverlayImage = LoadSave.GetSpriteCollection(LoadSave.MENU_OVERLAY);
        menuWidth = (int) (menuOverlayImage.getWidth() * Game.SCALE);
        menuHeight = (int) (menuOverlayImage.getHeight() * Game.SCALE);
        menuX = (Game.GAME_WIDTH / 2) - (menuWidth / 2);
        menuY = (int) (45 * Game.SCALE);
    }

    @Override
    public void update() {
        for (MenuButton menuButton : menuButtons) {
            menuButton.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(menuBackgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(menuOverlayImage, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton menuButton : menuButtons) {
            menuButton.draw(g);
        }
    }

    /**
     * Resets each menu button.
     */
    private void resetButtons() {
        for (MenuButton menuButton : menuButtons) {
            menuButton.resetBools();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton menuButton : menuButtons) {
            if (isOnButton(e, menuButton)) {
                menuButton.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton menuButton : menuButtons) {
            if (isOnButton(e, menuButton)) {
                if (menuButton.isMousePressed()) {
                    menuButton.changeGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton menuButton : menuButtons) {
            menuButton.setMouseOver(false);
        }

        for (MenuButton menuButton : menuButtons) {
            if (isOnButton(e, menuButton)) {
                menuButton.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
