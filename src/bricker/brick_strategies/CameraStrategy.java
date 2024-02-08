package bricker.brick_strategies;

import bricker.gameobjects.CameraFollower;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class CameraStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    public CameraStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj);
        if (otherObj == brickerGameManager.getMainBall() && brickerGameManager.camera() == null){
            brickerGameManager.addObject(new CameraFollower(brickerGameManager), CameraFollower.cameraFollowerLayer);
        }
    }
}
