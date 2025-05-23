package entities;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;
import static utils.Constants.Enemy.*;
import static utils.HelpMethods.*;

public abstract class Enemy extends Entity {
    // Enemy attributes
    protected int animationIndex, enemyState, enemyType;
    protected int animationTick, animationSpeed = 25;
    protected boolean active = true, attackChecked;

    // Enemy physics
    protected boolean firstUpdate = true, inAir;
    protected float fallSpeed, gravity = 0.04f * Game.SCALE;
    protected float enemyWalkingSpeed = 0.35f * Game.SCALE;
    protected int walkingDirection = LEFT;

    // Enemy attacking/patrolling
    protected int enemyTileY;
    protected float attackRange = Game.SIZE_IN_TILES; // range is in tiles
    protected final int RANGE_MULTIPLIER = 5;

    // Enemy health
    protected int maxEnemyHealth, currentEnemyHealth;

    /**
     * Constructor for an Enemy object.
     *
     * @param x         the x-coordinate
     * @param y         the y-coordinate
     * @param width     the width of an enemy
     * @param height    the height of an enemy
     * @param enemyType the type of enemy
     */
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initEntityHitbox(x, y, width, height);
        setUpHealth();
    }

    /**
     * Sets up the health of any enemy using its type.
     */
    private void setUpHealth() {
        maxEnemyHealth = GetEnemyMaxHealth(enemyType);
        currentEnemyHealth = maxEnemyHealth;
    }

    /**
     * Updates the animation tick of an enemy.
     */
    protected void updateEnemyAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetEnemySpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
                switch (enemyState) {
                    case ATTACK, HIT -> enemyState = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }

    /**
     * Checks if this is the enemy's first time updating (i.e., when the level first starts).
     */
    protected void firstEnemyUpdateCheck(int[][] levelData) {
        if (!IsEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    /**
     * Makes an enemy fall to the ground/ceiling if in the air.
     * @param levelData the level data
     */
    protected void enemyFallToGroundCeiling(int[][] levelData) {
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetYDistanceToCeilingOrFloor(hitbox, fallSpeed);
            enemyTileY = (int) (hitbox.y / Game.SIZE_IN_TILES);
        }
    }

    /**
     * Determines the enemy's patrol route.
     * @param levelData the level data
     */
    protected void makeEnemyMove(int[][] levelData, Player player) {
        float xSpeed = walkingDirection == LEFT ? -enemyWalkingSpeed : enemyWalkingSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            if (IsFloorTile(hitbox, xSpeed, levelData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        changeEnemyWalkingDirection();
    }

    /**
     * Changes the horizontal direction of the enemy.
     */
    protected void changeEnemyWalkingDirection() {
        walkingDirection = walkingDirection == LEFT ? RIGHT : LEFT;
    }

    /**
     * Changes the enemy's direction to face the player.
     * @param player who the enemy should be facing
     */
    protected void turnToPlayer(Player player) {
        walkingDirection = player.hitbox.x > hitbox.x ? RIGHT : LEFT;
    }

    /**
     * Simply changes the enemy's current state to another fresh state.
     * @param enemyState the new state of the enemy
     */
    protected void setEnemyState(int enemyState) {
        this.enemyState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    /**
     * Determines if the enemy can see the player.
     * @param levelData the level data
     * @param player who the enemy can(not) see
     * @return whether the enemy can see the player
     */
    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getEntityHitbox().y / Game.SIZE_IN_TILES);

        return  (playerTileY == enemyTileY) &&
                (isPlayerInRange(player)) &&
                (IsInSight(levelData, hitbox, player.hitbox, enemyTileY));
    }

    /**
     * Determines if the enemy is in range of the player.
     * @param player who the enemy is (not) in range of
     * @return whether the enemy is in range of the player
     */
    protected boolean isPlayerInRange(Player player) {
        // Determine the distance between the enemy and player
        int enemyPlayerDistance = (int) Math.abs(player.hitbox.x - hitbox.x);

        return enemyPlayerDistance <= attackRange * RANGE_MULTIPLIER;
    }

    /**
     * Determines if the enemy is in attack range of the player.
     * @param player who the enemy is (not) in attack range of
     * @return whether the enemy is in attack range of the player
     */
    protected boolean isPlayerInAttackRange(Player player) {
        int enemyPlayerDistance = (int) Math.abs(player.hitbox.x - hitbox.x);

        return enemyPlayerDistance <= attackRange;
    }

    /**
     * The enemy receives damage from the player, lowering its health.
     * @param damageAmount the amount of damage to receive by the enemy
     */
    public void takeDamage(int damageAmount) {
        currentEnemyHealth -= damageAmount;
        setEnemyState(currentEnemyHealth <= 0 ? DEAD : HIT);
    }

    /**
     * Checks if the enemy attack hitbox collides with the player's hitbox.
     * @param enemyAttackHitbox the enemy's attack hitbox
     * @param player who the enemy is interacting with
     */
    protected void checkPlayerHitByEnemy(Rectangle2D.Float enemyAttackHitbox, Player player) {
        if (enemyAttackHitbox.intersects(player.hitbox)) {
            player.changePlayerHealth(-GetEnemyDamage(enemyType));
        }
        attackChecked = true;
    }

    /**
     *
     * @return the enemy's animation index
     */
    public int getAnimationIndex() {
        return animationIndex;
    }

    /**
     *
     * @return the enemy's state
     */
    public int getEnemyState() {
        return enemyState;
    }

    /**
     *
     * @return if the enemy is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Resets the enemy's attributes to their default values.
     */
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentEnemyHealth = maxEnemyHealth;
        setEnemyState(IDLE);
        active = true;
        fallSpeed = 0;
    }
}
