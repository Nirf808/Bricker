package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a brick GameObject in the Bricker game.
 */
public class Brick extends GameObject {

    private CollisionStrategy strategy; // The collision strategy associated with the brick

    /**
     * Constructs a new Brick object.
     *
     * @param topLeftCorner The position of the top-left corner of the brick.
     * @param dimensions    The dimensions (width and height) of the brick.
     * @param renderable    The renderable object used to render the brick.
     * @param strategy      The collision strategy associated with the brick.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy strategy) {
        super(topLeftCorner, dimensions, renderable);
        this.strategy = strategy;
    }

    /**
     * Called when a collision occurs with another game object.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        // Call the collision strategy's onCollision method
        strategy.onCollision(this, other);
    }
}
