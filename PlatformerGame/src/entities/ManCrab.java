package entities;

import main.Game;

import static utils.Constants.EnemyConstants.*;

public class ManCrab extends Enemy {
    /**
     * Constructor for a Man-Crab object.
     *
     * @param x                 the x-coordinate
     * @param y                 the y-coordinate
     */
    public ManCrab(float x, float y) {
        super(x, y, MAN_CRAB_WIDTH, MAN_CRAB_HEIGHT, MAN_CRAB);

        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

    /**
     * Updates the many attributes of a Man-Crab.
     */
    public void update(int[][] levelData, Player player) {
        updateManCrabMovement(levelData, player);
        updateAnimationTick();
    }

    /**
     * Updates the movement of a Man-Crab.
     */
    private void updateManCrabMovement(int[][] levelData, Player player) {
        // Check if this is a Man-Crab's first time updating
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }
        if (inAir) { // If the Man-Crab is in the air, make them fall to the ground/ceiling
            fallToGroundCeiling(levelData);
        } else { // Otherwise, the Man-Crab can patrol
            setMovement(levelData, player);
        }
    }

    /**
     * Initializes a Man-Crab's movement.
     */
    private void setMovement(int[][] levelData, Player player) {
        switch (enemyState) {
            case IDLE -> {setEnemyState(RUNNING);}
            case RUNNING -> {
                tryToAttack(levelData, player);
                makeEnemyMove(levelData, player);
            }
        }
    }

    /**
     * The Man-Crab will follow and attack the player if possible.
     * @param levelData the level data
     * @param player who the Man-Crab will follow and attack if within range
     */
    private void tryToAttack(int[][] levelData, Player player) {
        if (canSeePlayer(levelData, player)) {
            turnToPlayer(player);
        }
        if (isPlayerInAttackRange(player)) {
            setEnemyState(ATTACK);
        }
    }
}
