package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * Represents a camera follower GameObject in the Bricker game.
 * This object is invisible, and it follows the main ball and adjusts the camera accordingly.
 * It keeps track of ball collisions until the camera need to be set back to normal.
 */
public class CameraFollower extends GameObject {
    /**
     * Represents the layer assigned to the camera follower objects in the Bricker game.
     */
//    public static int cameraFollowerLayer = Layer.BACKGROUND;
    private final int initCollisions; // Initial collision count of the main ball
    private final BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs a new CameraFollower object.
     *
     * @param brickerGameManager The game manager instance.
     */
    public CameraFollower(BrickerGameManager brickerGameManager) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.brickerGameManager = brickerGameManager;
        this.initCollisions = brickerGameManager.getMainBall().getCollisionCounter();
        setManagerCamera();
    }

    /**
     * Updates the state of the camera follower.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Check if the main ball has collided more than 4 times since this camera follower was created
        if (brickerGameManager.getMainBall().getCollisionCounter() > initCollisions + 4) {
            rollBackCamera(); // Roll back the camera
            brickerGameManager.deleteObject(this, Constants.CAMERA_FOLLOWER_LAYER); // Delete this camera follower
        }
    }

    /**
     * Sets the camera to follow the main ball.
     */
    private void setManagerCamera() {
        brickerGameManager.setCamera(
                new Camera(
                        brickerGameManager.getMainBall(), // Object to follow
                        Vector2.ZERO, // Follow the center of the object
                        brickerGameManager.getWindowDimensions().mult(1.2f), // Widen the frame a bit
                        brickerGameManager.getWindowDimensions() // Share the window dimensions
                )
        );
    }

    /**
     * Rolls back the camera to its default state.
     */
    private void rollBackCamera() {
        brickerGameManager.setCamera(null); // Set the camera to null
    }
}
