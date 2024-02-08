package bricker.brick_strategies;

import bricker.gameobjects.CameraFollower;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class CameraStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;
    private BasicCollisionStrategy basicCollisionStrategy;

    public CameraStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        if (otherObj == brickerGameManager.getMainBall() && brickerGameManager.camera() == null){
            brickerGameManager.addObject(new CameraFollower(brickerGameManager), CameraFollower.cameraFollowerLayer);
        }
    }
}
