package bricker.gameobjects;

import bricker.brick_strategies.AdditionalPaddleStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents an extra paddle object in the Bricker game.
 * An extra paddle behaves similarly to a regular paddle but disappears after a certain number of collisions.
 */
public class ExtraPaddle extends Paddle {

    private int numCollisions; // Number of collisions the extra paddle has encountered
    private final BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs an ExtraPaddle object.
     *
     * @param brickerGameManager The game manager instance.
     * @param topLeftCorner      The position of the top-left corner of the paddle.
     * @param dimensions         The dimensions (width and height) of the paddle.
     * @param renderable         The renderable object used to render the paddle.
     * @param inputListener      The input listener for user interactions with the paddle.
     * @param x                  The initial x-coordinate of the paddle.
     */
    public ExtraPaddle(BrickerGameManager brickerGameManager,
                       Vector2 topLeftCorner, Vector2 dimensions,
                       Renderable renderable,
                       UserInputListener inputListener, float x) {
        super(topLeftCorner, dimensions, renderable, inputListener, x);
        numCollisions = 0;
        AdditionalPaddleStrategy.setHasExtraPaddle(true); // Indicate that an extra paddle is active
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Called when a collision occurs with another game object.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        numCollisions++; // Increment the collision count
    }

    /**
     * Updates the state of the extra paddle.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime); // Call the base class update method

        // Check if the extra paddle has reached its collision limit
        if (numCollisions >= 4) {
            // Remove the extra paddle from the game
            brickerGameManager.deleteObject(this);
            // Reset the flag indicating the presence of an extra paddle
            AdditionalPaddleStrategy.setHasExtraPaddle(false);
        }
    }
}