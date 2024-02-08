package bricker.brick_strategies;

import bricker.gameobjects.CameraFollower;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * a CollisionStrategy that changes the player's view of the game
 */
public class CameraStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    /**
     * creating a new CameraStrategy
     * @param brickerGameManager the game manager instance
     */
    public CameraStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * deletes the collided brick and creates a CameraFollower if the camera is not yet switched
     * @param thisObj the brick which was collided
     * @param otherObj the collided object
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj);
        if (otherObj == brickerGameManager.getMainBall() && brickerGameManager.camera() == null){
            brickerGameManager.addObject(new CameraFollower(brickerGameManager), CameraFollower.cameraFollowerLayer);
        }
    }
}
