package bricker.brick_strategies;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

public class AdditionalPaddleStrategy implements CollisionStrategy{
    private static boolean hasExtraPuddle = false;
    private BrickerGameManager brickerGameManager;
    private BasicCollisionStrategy basicCollisionStrategy;

    public AdditionalPaddleStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
        this.basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        if (!hasExtraPuddle){
            brickerGameManager.addObject(brickerGameManager.getGameObjectFactory().createPaddle(
                    brickerGameManager,
                    Constants.PADDLE_IMAGE,
                    new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_LENGTH),
                    brickerGameManager.getUserInput(),
                    brickerGameManager.getWindowDimensions().x(),
                    getPaddleInitLocation(),
                    true
            ), Layer.DEFAULT);
        }
    }

    public static void setHasExtraPuddle(boolean hasExtraPuddle) {
        AdditionalPaddleStrategy.hasExtraPuddle = hasExtraPuddle;
    }

    private Vector2 getPaddleInitLocation(){
        return new Vector2(brickerGameManager.getWindowDimensions().x() / 2,
                brickerGameManager.getWindowDimensions().y() / 2);
    }
}
