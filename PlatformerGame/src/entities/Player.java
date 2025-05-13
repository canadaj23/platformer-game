package entities;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

/**
 * This class is for everything related to the player.
 */
public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = (int) (15 * Game.SCALE);
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean up, down, left, right, jump;
    private float playerSpeed = 1.2f * Game.SCALE;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE, yDrawOffset = 4 * Game.SCALE;

    // Jumping and gravity
    private float airSpeed = 0f, gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.5f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = true;

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
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
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

        if (inAir) {
            playerAction = airSpeed < 0 ? JUMPING : FALLING;
        }

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

        if (jump) {
            playerJump();
        }

        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }

        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) { // jumping or falling
            if (CanMoveHere(hitbox.x, hitbox.y  + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = GetYDistanceToCeiling(hitbox, airSpeed);
                if (airSpeed > 0) { // touching a floor
                    resetInAir();
                } else { // touching a roof
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPosition(xSpeed);
            }
        } else { // on a floor
            updateXPosition(xSpeed);
        }

        // Allow moving once done with falling or jumping
        moving = true;
    }

    /**
     * Updates the player's speed when going left or right.
     * @param xSpeed the speed of the character going left or right
     */
    private void updateXPosition(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetXDistanceToWall(hitbox, xSpeed);
        }
    }

    /**
     * Resets air related variables when touching a floor.
     */
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    /**
     * (Dis)allows the player to jump.
     */
    private void playerJump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
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
        if (!IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
