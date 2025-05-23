package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This is an abstract class that will be extended by entities such as Player, Enemy, etc.
 */
public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    /**
     * Constructor for an Entity object.
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the width of the entity
     * @param height the height of the entity
     */
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes the Entity object's hitbox.
     */
    protected void initEntityHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    /**
     * Draws the Entity object's hitbox for debugging.
     * @param g the Graphics object used for drawing
     */
    protected void drawEntityHitbox(Graphics g, int levelOffset) {
        g.setColor(Color.CYAN);
        g.drawRect((int) hitbox.x - levelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     *
     * @return the Entity object's hitbox
     */
    public Rectangle2D.Float getEntityHitbox() {
        return hitbox;
    }
}
