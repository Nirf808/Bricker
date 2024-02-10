package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a ball GameObject in the Bricker game.
 */
public class Ball extends GameObject {

    private BrickerGameManager brickerGameManager; // Reference to the game manager
    private int collisionCounter; // Number of collisions the ball has encountered
    private Sound collisionSound; // Sound to play on collision

    /**
     * Constructs a new Ball object.
     *
     * @param brickGameManager The game manager instance.
     * @param topLeftCorner    The position of the top-left corner of the ball.
     * @param dimensions       The dimensions (width and height) of the ball.
     * @param renderable       The renderable object used to render the ball.
     * @param collisionSound   The sound to play on collision.
     */
    public Ball(BrickerGameManager brickGameManager,
                Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.brickerGameManager = brickGameManager;
    }

    /**
     * Called when a collision occurs with another game object.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        // Increment the collision counter
        collisionCounter++;

        // Calculate and set the new velocity based on the collision normal
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);

        // Play the collision sound
        collisionSound.play();
    }

    /**
     * Gets the number of collisions the ball has encountered.
     *
     * @return The number of collisions.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

    /**
     * Updates the state of the ball.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Check if the ball has reached the bottom of the window and delete it if so
        if (brickerGameManager.getWindowDimensions().y() < this.getCenter().y()) {
            brickerGameManager.deleteObject(this);
        }
    }
}
