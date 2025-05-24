package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;
import static utils.Constants.Enemy.*;

public class ManCrab extends Enemy {
    private int attackHitboxOffsetX;

    /**
     * Constructor for a Man-Crab object.
     *
     * @param x                 the x-coordinate
     * @param y                 the y-coordinate
     */
    public ManCrab(float x, float y) {
        super(x, y, MAN_CRAB_WIDTH, MAN_CRAB_HEIGHT, MAN_CRAB);

        initEntityHitbox(22, 19);
        initManCrabAttackHitbox();
    }

    /**
     * Creates the attack hitbox for a Man-Crab.
     */
    private void initManCrabAttackHitbox() {
        entityAttackHitBox = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
        attackHitboxOffsetX = (int) (30 * Game.SCALE);
    }

    /**
     * Updates the many attributes of a Man-Crab.
     */
    protected void updateManCrab(int[][] levelData, Player player) {
        updateManCrabBehavior(levelData, player);
        updateEnemyAnimationTick();
        updateManCrabAttackHitbox();
    }

    /**
     * Updates the behavior of a Man-Crab.
     */
    private void updateManCrabBehavior(int[][] levelData, Player player) {
        // Check if this is a Man-Crab's first time updating
        if (firstUpdate) {
            firstEnemyUpdateCheck(levelData);
        }
        if (inAir) { // If the Man-Crab is in the air, make them fall to the ground/ceiling
            enemyFallToGroundCeiling(levelData);
        } else { // Otherwise, the Man-Crab can patrol
            setManCrabBehavior(levelData, player);
        }
    }

    /**
     * Updates the Man-Crab's attack hitbox when moving.
     */
    private void updateManCrabAttackHitbox() {
        entityAttackHitBox.x = hitbox.x - attackHitboxOffsetX;
        entityAttackHitBox.y = hitbox.y;
    }

    /**
     * Initializes a Man-Crab's movement.
     */
    private void setManCrabBehavior(int[][] levelData, Player player) {
        switch (entityState) {
            case IDLE -> setEnemyState(RUNNING);
            case RUNNING -> {
                manCrabTryToAttack(levelData, player);
                makeEnemyMove(levelData, player);
            }
            case ATTACK -> {
                if (animationIndex == 0) {
                    attackChecked = false;
                }
                if (animationIndex == 3 && !attackChecked) {
                    checkPlayerHitByEnemy(entityAttackHitBox, player);
                }
            }
            case HIT -> {}
        }
    }

    /**
     * The Man-Crab will follow and attack the player if possible.
     * @param levelData the level data
     * @param player who the Man-Crab will follow and attack if within range
     */
    private void manCrabTryToAttack(int[][] levelData, Player player) {
        if (canSeePlayer(levelData, player)) {
            turnToPlayer(player);
            if (isPlayerInAttackRange(player)) {
                setEnemyState(ATTACK);
            }
        }
    }

    /**
     *
     * @return x-direction the Man-Crab will go
     */
    public int flipManCrabX() {
        return walkingDirection == RIGHT ? width : 0;
    }

    /**
     *
     * @return the direction the Man-Crab faces (left or right)
     */
    public int flipManCrabW() {
        return walkingDirection == RIGHT ? -1 : 1;
    }
}
