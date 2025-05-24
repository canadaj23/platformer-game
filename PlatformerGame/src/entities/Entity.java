package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This is an abstract class that will be extended by entities such as Player, Enemy, etc.
 */
public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int animationTick, animationIndex;
    protected int entityState;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxEntityHealth, currentEntityHealth;
    protected Rectangle2D.Float entityAttackHitBox;
    protected float entitySpeed = 1.0f * Game.SCALE;

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
    protected void initEntityHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y,  (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    /**
     * Draws the entity's hitbox for debugging.
     * @param g the Graphics object used for drawing
     */
    protected void drawEntityHitbox(Graphics g, int levelOffset) {
        g.setColor(Color.CYAN);
        g.drawRect((int) hitbox.x - levelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * Draws the attack hitbox for an entity for debugging.
     * @param g the Graphics object for drawing
     * @param xLevelOffset the offset the hitbox uses for a correct position
     */
    protected void drawEntityAttackHitbox(Graphics g, int xLevelOffset) {
        g.setColor(Color.BLUE);
        g.drawRect(
                (int) entityAttackHitBox.x - xLevelOffset,
                (int) entityAttackHitBox.y,
                (int) entityAttackHitBox.width,
                (int) entityAttackHitBox.height);
    }

    /**
     * @return the Entity object's hitbox
     */
    public Rectangle2D.Float getEntityHitbox() {
        return hitbox;
    }

    /**
     * @return the entity's state
     */
    public int getEntityState() {
        return entityState;
    }

    /**
     *
     * @return the entity's animation index
     */
    public int getEntityAnimationIndex() {
        return animationIndex;
    }
}
