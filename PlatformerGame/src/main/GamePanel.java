package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.PlayerConstants.*;

/**
 * This class will draw the game elements onto a panel that can then be displayed on a GameWindow object.
 */
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE, playerDirection = -1;
    private boolean moving = false;

    /**
     * Constructor for GamePanel
     */
    public GamePanel() {
        importImage();
        loadAnimations();

        setPanelSize();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Makes an image usable within the code.
     */
    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates an animation using multiple still images.
     */
    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        // Assign each animation's subimage to a [j][i]
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    /**
     * Sets the size of the panel to be displayed.
     */
    private void setPanelSize() {
        setPreferredSize(new Dimension(1280, 800));
    }

    /**
     * Changes the player's direction based on the parameter.
     * @param direction the direction the player will face
     */
    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }

    /**
     * Changes whether the player is moving.
     * @param moving boolean to indicate if the player is moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Updates the index of the image to be displayed within an animation array.
     */
    private void updateAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    /**
     * Determines which animation array to use for a given frame.
     */
    private void setAnimation() {
        playerAction = moving ? RUNNING : IDLE;
    }

    /**
     * Updates the x- and y-coordinates of the player.
     */
    private void updatePosition() {
        if (moving) {
            switch (playerDirection) {
                case UP -> yDelta -= 5;
                case DOWN -> yDelta += 5;
                case LEFT -> xDelta -= 5;
                case RIGHT -> xDelta += 5;
            }
        }
    }

    /**
     * Updates logical aspects of the game.
     */
    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    /**
     * Paints a display to be added to a frame.
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
                animations[playerAction][animationIndex],
                (int) xDelta,
                (int) yDelta,
                256,
                160,
                null);
    }
}
