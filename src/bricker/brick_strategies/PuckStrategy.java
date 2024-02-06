package bricker.brick_strategies;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

public class PuckStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;
    private BasicCollisionStrategy basicCollisionStrategy;

    public PuckStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        for (int i = 0; i < 2; i++) {
            brickerGameManager.addObject(brickerGameManager.getBallFactory().createBall(
                            brickerGameManager,
                            Constants.PUCK_BALL_IMAGE,
                            Constants.BALL_SOUND,
                            Constants.BALL_SIZE * 0.75f,
                            thisObj.getCenter(),
                            brickerGameManager.chooseBallDirection()),
                    Layer.DEFAULT);
        }

    }
}
