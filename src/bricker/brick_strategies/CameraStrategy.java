package bricker.brick_strategies;

import bricker.main.Constants;
import bricker.gameobjects.CameraFollower;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * A CollisionStrategy implementation that changes the player's view of the game.
 * This strategy deletes the collided brick and creates a CameraFollower if the camera is not yet switched.
 */
public class CameraStrategy implements CollisionStrategy {

    private final BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs a new CameraStrategy.
     *
     * @param brickerGameManager The game manager responsible for managing game objects.
     */
    public CameraStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Deletes the collided brick and creates a CameraFollower if the camera is not yet switched.
     *
     * @param thisObj   The brick involved in the collision.
     * @param otherObj  The collided object.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj); // Delete the collided brick

        // Check if the collided object is the main ball and if the camera is not yet switched
        if (otherObj == brickerGameManager.getMainBall() && brickerGameManager.camera() == null) {
            // Create a CameraFollower object to change the player's view
            brickerGameManager.addObject(new CameraFollower(brickerGameManager),
                    Constants.CAMERA_FOLLOWER_LAYER);
        }
    }
}
