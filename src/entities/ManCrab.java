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
}
