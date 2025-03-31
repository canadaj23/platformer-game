package entities;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.CanMoveHere;

/**
 * This class is for everything related to the player.
 */
public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean up, down, left, right;
    private float playerSpeed = 2.0f;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE, yDrawOffset = 4 * Game.SCALE;

    /**
     * Constructor for creating a Player object.
     *
     * @param x  the Player object's x-coordinate
     * @param y  the Player object's y-coordinate
     * @param width  the Player object's width
     * @param height  the Player object's height
     */
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadPlayerAnimations();
        initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
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
                (int) (hitbox.x - xDrawOffset),
                (int) (hitbox.y - yDrawOffset),
                width,
                height,
                null);
        drawHitbox(g);
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

        if (!up && !down && !left && !right) {
            return;
        }

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left) {
            xSpeed = playerSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

//        if (CanMoveHere(x + xSpeed, y + ySpeed, width, height, levelData)) {
//            this.x += xSpeed;
//            this.y += ySpeed;
//            moving = true;
//        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
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
     * Takes the level data and stores it in the Player object.
     * TODO: possibly implement better way of getting level data
     * @param levelData the level data to be used with player actions
     */
    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
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
