package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Entity.*;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

/**
 * This class is for everything related to the player.
 */
public class Player extends Entity {
    private Playing playing;
    private BufferedImage[][] animations;

    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE, yDrawOffset = 4 * Game.SCALE;

    // Physics
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private float jumpSpeed = -2.5f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean attackChecked;
    private int flipX = 0, flipWidth = 1;

    // Status Bar UI
    private BufferedImage statusOverlayImage;

    private int statusOverlayWidth = (int) (192 * Game.SCALE);
    private int statusOverlayHeight = (int) (58 * Game.SCALE);
    private int statusOverlayX = (int) (10 * Game.SCALE);
    private int statusOverlayY= (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarStartX = (int) (34 * Game.SCALE);
    private int healthBarStartY = (int) (14 * Game.SCALE);

    private int healthWidth = healthBarWidth;

    /**
     * Constructor for creating a Player object.
     *
     * @param x  the Player object's x-coordinate
     * @param y  the Player object's y-coordinate
     * @param width  the Player object's width
     * @param height  the Player object's height
     */
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.entityState = IDLE;
        this.maxEntityHealth = 100;
        this.currentEntityHealth = maxEntityHealth;
        this.entitySpeed = 1.0f * Game.SCALE;
        loadPlayerAnimations();
        initEntityHitbox(20, 27);
        initPlayerAttackHitBox();
    }

    public void setPlayerSpawnPoint(Point playerSpawnPoint) {
        this.x = playerSpawnPoint.x;
        this.y = playerSpawnPoint.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    /**
     * Creates an animation using multiple still images.
     */
    private void loadPlayerAnimations() {
        BufferedImage image = LoadSave.GetSpriteCollection(LoadSave.PLAYER_SPRITES);
        animations = new BufferedImage[7][8];

        // Assign each animation's subimage to a [j][i]
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
            }
        }

        statusOverlayImage = LoadSave.GetSpriteCollection(LoadSave.PLAYER_STATUS_OVERLAY);
    }

    /**
     * Initializes the attack hitbox the player will use.
     */
    private void initPlayerAttackHitBox() {
        entityAttackHitBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    /**
     * Updates the player's attributes.
     */
    public void updatePlayer() {
        updatePlayerHealthBar();
        if (currentEntityHealth <= 0) {
            playing.setGameOver(true);
            return;
        }
        updatePlayerAttackHitbox();
        updatePlayerPosition();
        if (attacking) {
            checkPlayerAttack();
        }
        updatePlayerAnimationTick();
        setPlayerAnimation();
    }

    /**
     * Updates the health bar to match the player's current health.
     */
    private void updatePlayerHealthBar() {
        healthWidth = (int) ((currentEntityHealth / (float) maxEntityHealth) * healthBarWidth);
    }

    /**
     * Updates the attack hitbox as the player moves.
     */
    private void updatePlayerAttackHitbox() {
        if (right) {
            entityAttackHitBox.x = hitbox.x + hitbox.width + (int) (10 * Game.SCALE);
        } else if (left) {
            entityAttackHitBox.x = hitbox.x - hitbox.width - (int) (10 * Game.SCALE);
        }

        entityAttackHitBox.y = hitbox.y + (int) (10 * Game.SCALE);
    }

    /**
     * Updates the x- and y-coordinates of the player.
     */
    private void updatePlayerPosition() {
        moving = false;

        // Perform a jump if allowed
        if (jump) {
            playerJump();
        }

        // Idle animation for remaining still on the ground
        if (!inAir) {
            if ((!left && !right) || (left && right)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= entitySpeed;
            flipX = width;
            flipWidth = -1;
        }
        if (right) {
            xSpeed += entitySpeed;
            flipX = 0;
            flipWidth = 1;
        }

        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) { // jumping or falling
            if (CanMoveHere(hitbox.x, hitbox.y  + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updatePlayerXPosition(xSpeed);
            } else {
                hitbox.y = GetYDistanceToCeilingOrFloor(hitbox, airSpeed);
                if (airSpeed > 0) { // touching a floor
                    resetPlayerInAir();
                } else { // touching a roof
                    airSpeed = fallSpeedAfterCollision;
                }
                updatePlayerXPosition(xSpeed);
            }
        } else { // on a floor
            updatePlayerXPosition(xSpeed);
        }

        // Allow moving once done with falling or jumping
        moving = true;
    }

    /**
     * Updates the player's speed when going left or right.
     * @param xSpeed the speed of the character going left or right
     */
    private void updatePlayerXPosition(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetXDistanceToWall(hitbox, xSpeed);
        }
    }

    /**
     * Checks if player has attacked yet.
     */
    private void checkPlayerAttack() {
        if (attackChecked || animationIndex != 1) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHitByPlayer(entityAttackHitBox);
    }

    /**
     * Updates the index of the image to be displayed within an animation array.
     */
    private void updatePlayerAnimationTick() {
        animationTick++;

        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= GetSpriteAmount(entityState)) {
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    /**
     * Renders the player to be seen and interacted with.
     *
     * @param g            the Graphics object used for drawing
     * @param levelOffset  the offset of the player and the left/right border
     */
    public void renderPlayer(Graphics g, int levelOffset) {
        g.drawImage(
                animations[entityState][animationIndex],
                (int) (hitbox.x - xDrawOffset) - levelOffset + flipX,
                (int) (hitbox.y - yDrawOffset),
                width * flipWidth,
                height,
                null);
        // For debugging
//        drawEntityHitbox(g, levelOffset);
        drawEntityAttackHitbox(g, levelOffset);
        drawPlayerStatusOverlay(g);
    }

    /**
     * Draws the status overlay to display the health and energy bars.
     * @param g the Graphics object for drawing
     */
    private void drawPlayerStatusOverlay(Graphics g) {
        g.drawImage(
                statusOverlayImage,
                statusOverlayX,
                statusOverlayY,
                statusOverlayWidth,
                statusOverlayHeight,
                null);

        g.setColor(Color.RED);
        g.fillRect(
                healthBarStartX + statusOverlayX,
                healthBarStartY + statusOverlayY,
                healthWidth,
                healthBarHeight);
    }

    /**
     * Increases/decreases the player's current health.
     * @param value what to remove from the player's current health
     */
    public void changePlayerHealth(int value) {
        currentEntityHealth += value;

        if (currentEntityHealth <= 0) {
            currentEntityHealth = 0;
        } else if (currentEntityHealth >= maxEntityHealth) {
            currentEntityHealth = maxEntityHealth;
        }
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
     * Determines which animation array to use for a given frame.
     */
    private void setPlayerAnimation() {
        int startAnimation = entityState;

        entityState = moving ? RUNNING : IDLE;

        if (inAir) {
            entityState = airSpeed < 0 ? JUMPING : FALLING;
        }

        if (attacking) {
            entityState = ATTACK;
            if (startAnimation != ATTACK) {
                animationIndex = 1;
                animationTick = 0;
                return;
            }
        }

        if (startAnimation != entityState) {
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
     * Resets all four direction booleans.
     */
    public void resetPlayerDirBooleans() {
        left = false;
        right = false;
    }

    /**
     * Resets air related variables when touching a floor.
     */
    private void resetPlayerInAir() {
        inAir = false;
        airSpeed = 0;
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
     * @return true/false if the player is moving left.
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Sets true/false if the player is moving left.
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
     * Sets true/false if the player is moving right.
     * @param right true/false if player is moving right
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Sets jump to true/false.
     * @param jump true/false
     */
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    /**
     * Resets everything related to the player.
     */
    public void resetAll() {
        resetPlayerDirBooleans();
        resetPlayer();
        resetHitboxes();

        if (!IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
    }

    /**
     * Resets the player's attributes to its defaults.
     */
    private void resetPlayer() {
        resetPlayerPhysics();
        entityState = IDLE;
        currentEntityHealth = maxEntityHealth;
    }

    /**
     * Resets the player's physics-related attributes.
     */
    private void resetPlayerPhysics() {
        flipX = 0;
        flipWidth = 1;
        inAir = false;
        attacking = false;
        moving = false;
    }

    /**
     * Resets the player's hitbox and attack hitbox.
     */
    private void resetHitboxes() {
        hitbox.x = x;
        hitbox.y = y;
        entityAttackHitBox.x = hitbox.x + hitbox.width + (int) (10 * Game.SCALE);
        entityAttackHitBox.y = hitbox.y + (int) (10 * Game.SCALE);
    }
}
