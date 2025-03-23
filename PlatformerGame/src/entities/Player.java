package entities;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

/**
 * This class is for everything related to the player.
 */
public class Player extends Entity {
    private int width, height;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean up, down, left, right;
    private float playerSpeed = 2.0f;

    /**
     * Constructor for creating a Player object.
     *
     * @param x  the Player object's x-coordinate
     * @param y  the Player object's y-coordinate
     * @param width
     * @param height
     */
    public Player(float x, float y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        loadPlayerAnimations();
    }

    /**
     * Updates the player's attributes.
     */
    public void updatePlayer() {
        updatePlayerPosition();
        updatePlayerAnimationTick();
        setPlayerAnimation();
    }

    /**
     * Renders the player to be seen and interacted with.
     */
    public void renderPlayer(Graphics g) {
        g.drawImage(
                animations[playerAction][animationIndex],
                (int) x,
                (int) y,
                width,
                height,
                null);
    }

    /**
     * Updates the index of the image to be displayed within an animation array.
     */
    private void updatePlayerAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    /**
     * Determines which animation array to use for a given frame.
     */
    private void setPlayerAnimation() {
        int startAnimation = playerAction;

        playerAction = moving ? RUNNING : IDLE;

        if (attacking) {
            playerAction = ATTACK_1;
        }

        if (startAnimation != playerAction) {
            resetPlayerAnimationTick();
        }
    }

    /**
     * Resets the animation tick and index for a given sequence.
     */
    private void resetPlayerAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    /**
     * Updates the x- and y-coordinates of the player.
     */
    private void updatePlayerPosition() {
        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    /**
     * Creates an animation using multiple still images.
     */
    private void loadPlayerAnimations() {
        BufferedImage image = LoadSave.GetSpriteCollection(LoadSave.PLAYER_SPRITES);
        animations = new BufferedImage[9][6];

        // Assign each animation's subimage to a [j][i]
        for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
                }
            }
    }

    /**
     * Resets all four direction booleans.
     */
    public void resetPlayerDirBooleans() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    /**
     * @return true/false if player is attacking
     */
    public boolean isPlayerAttacking() {
        return attacking;
    }

    /**
     * @param attacking boolean to make player attack or not
     */
    public void setPlayerAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    /**
     * @return true/false if the player is moving up.
     */
    public boolean isUp() {
        return up;
    }

    /**
     * Set true/false if the player is moving up.
     * @param up true/false if player is moving up
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * @return true/false if the player is moving down.
     */
    public boolean isDown() {
        return down;
    }

    /**
     * Set true/false if the player is moving down.
     * @param down true/false if player is moving down
     */
    public void setDown(boolean down) {
        this.down = down;
    }

    /**
     * @return true/false if the player is moving left.
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Set true/false if the player is moving left.
     * @param left true/false if player is moving left
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * @return true/false if the player is moving right.
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Set true/false if the player is moving right.
     * @param right true/false if player is moving right
     */
    public void setRight(boolean right) {
        this.right = right;
    }
}
