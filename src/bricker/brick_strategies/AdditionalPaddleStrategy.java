package bricker.brick_strategies;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

/**
 * A BrickStrategy class for creating a temporary extra paddle in the game.
 * This strategy creates an extra paddle when a collision occurs with a specific brick,
 * and ensures that only one extra paddle can exist at a time.
 */
public class AdditionalPaddleStrategy implements CollisionStrategy {

    private static boolean hasExtraPaddle = false; // Flag indicating if an extra paddle exists
    private BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs a new AdditionalPaddleStrategy.
     *
     * @param brickerGameManager The game manager responsible for managing game objects.
     */
    public AdditionalPaddleStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Creates a new extra paddle if one does not already exist.
     *
     * @param thisObj  The brick involved in the collision.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj); // Delete the collided brick

        // Check if an extra paddle does not already exist
        if (!hasExtraPaddle) {
            // Create a new extra paddle and add it to the game
            brickerGameManager.addObject(
                    brickerGameManager.getGameObjectFactory().createPaddle(
                            brickerGameManager,
                            Constants.PADDLE_IMAGE, // Image for the paddle
                            new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_LENGTH), // Dimensions of the paddle
                            brickerGameManager.getUserInput(), // User input listener for controlling the paddle
                            brickerGameManager.getWindowDimensions().x(), // Width of the game window
                            getPaddleInitLocation(), // Initial location of the paddle
                            true // Flag indicating that this is an extra paddle
                    ),
                    Layer.DEFAULT // Layer for rendering the extra paddle
            );
            // Update the flag to indicate that an extra paddle now exists
            setHasExtraPaddle(true);
        }
    }

    /**
     * Sets the flag indicating if an extra paddle exists.
     *
     * @param hasExtraPaddle True if an extra paddle is added, false if an extra paddle is removed.
     */
    public static void setHasExtraPaddle(boolean hasExtraPaddle) {
        AdditionalPaddleStrategy.hasExtraPaddle = hasExtraPaddle;
    }

    /**
     * Calculates the initial location of the extra paddle.
     *
     * @return The initial location of the extra paddle.
     */
    private Vector2 getPaddleInitLocation() {
        // Return the center of the game window as the initial location
        return new Vector2(
                brickerGameManager.getWindowDimensions().x() / 2,
                brickerGameManager.getWindowDimensions().y() / 2
        );
    }
}
