package entities;

import main.Game;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

public abstract class Enemy extends Entity {
    private int animationIndex, enemyState, enemyType;
    private int animationTick, animationSpeed = 25;
    private boolean firstUpdate = true, inAir;
    private float fallSpeed, gravity = 0.04f * Game.SCALE;
    private float enemyWalkingSpeed = 0.35f * Game.SCALE;
    private int walkingDirection = LEFT;

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
        initHitbox(x, y, width, height);
    }

    /**
     * Updates the many attributes of an enemy.
     */
    public void update(int[][] levelData) {
        updateEnemyMovement(levelData);
        updateAnimationTick();
    }

    /**
     * Updates the animation tick of an enemy.
     */
    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetEnemySpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
            }
        }
    }

    /**
     * Updates the movement of an enemy.
     */
    private void updateEnemyMovement(int[][] levelData) {
        // Check if this is the enemy's first time updating
        if (firstUpdate) {
            if (!IsEntityOnFloor(hitbox, levelData)) {
                inAir = true;
            }
            firstUpdate = false;
        }
        if (inAir) { // If the enemy is in the air, make them fall to the ground/ceiling
            fallToGroundCeiling(levelData);
        } else { // Otherwise, the enemy can patrol
            patrol(levelData);
        }
    }

    /**
     * Makes the enemy fall to the ground/ceiling if in the air.
     * @param levelData the level data
     */
    private void fallToGroundCeiling(int[][] levelData) {
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetYDistanceToCeilingOrFloor(hitbox, fallSpeed);
        }
    }

    /**
     * The enemy will patrol if permitted.
     */
    private void patrol(int[][] levelData) {
        switch (enemyState) {
            case IDLE -> {enemyState = RUNNING;}
            case RUNNING -> {
                makeEnemyPatrol(levelData);
            }
        }
    }

    /**
     * Allows the enemy to patrol.
     * @param levelData the level data
     */
    private void makeEnemyPatrol(int[][] levelData) {
        float xSpeed = walkingDirection == LEFT ? -enemyWalkingSpeed : enemyWalkingSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            if (IsFloorTile(hitbox, xSpeed, levelData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        changeWalkDirection();
    }

    private void changeWalkDirection() {
        walkingDirection = walkingDirection == LEFT ? RIGHT : LEFT;
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
}
