package entities;

/**
 * This is an abstract class that will be extended by Player, Enemy, etc.
 */
public abstract class Entity {
    protected float x, y;

    /**
     * Constructor for an entity.
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
