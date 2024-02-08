package bricker.brick_strategies;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

import java.util.Random;

public class PuckStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    public PuckStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj);
        for (int i = 0; i < 2; i++) {
            brickerGameManager.addObject(brickerGameManager.getBallFactory().createBall(
                            brickerGameManager,
                            Constants.PUCK_BALL_IMAGE,
                            Constants.BALL_SOUND,
                            Constants.BALL_SIZE * 0.75f,
                            thisObj.getCenter(),
                            choosePuckDirection()),
                    Layer.DEFAULT);
        }
    }

    public Vector2 choosePuckDirection(){
        double speedAbsoluteValue = Math.sqrt(2) * Constants.BALL_SPEED;
        Random random = new Random();
        double randomValue = random.nextDouble() * 2 * Math.PI;
        double xSpeed = speedAbsoluteValue * Math.cos(randomValue);
        double ySpeed = speedAbsoluteValue * Math.sin(randomValue);
        return new Vector2((float)xSpeed, (float)ySpeed);
    }
}
