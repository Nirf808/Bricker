package bricker.brick_strategies;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

/**
 * a BrickStrategy class for creating a temporary extra paddle to the game
 */
public class AdditionalPaddleStrategy implements CollisionStrategy{
    private static boolean hasExtraPuddle = false;
    private BrickerGameManager brickerGameManager;

    /**
     * creating a new AdditionalPaddleStrategy
     * @param brickerGameManager the game manager instance
     */
    public AdditionalPaddleStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * creates a new extra paddle if not exists
     * @param thisObj the brick which was collided
     * @param otherObj the collided object
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj);
        if (!hasExtraPuddle){
            brickerGameManager.addObject(brickerGameManager.getPaddleFactory().createPaddle(
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

    /**
     * set hasExtraPaddle value
     * @param hasExtraPuddle true if an extra paddle is added, false if an extra puddle is removed
     */
    public static void setHasExtraPaddle(boolean hasExtraPuddle) {
        AdditionalPaddleStrategy.hasExtraPuddle = hasExtraPuddle;
    }

    private Vector2 getPaddleInitLocation(){
        return new Vector2(brickerGameManager.getWindowDimensions().x() / 2,
                brickerGameManager.getWindowDimensions().y() / 2);
    }
}
