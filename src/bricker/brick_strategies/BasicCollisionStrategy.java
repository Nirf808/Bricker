package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * a CollisionStrategy that's only deletes a brick when a collision occurs
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager brickerGameManager;

    /**
     * creates new BasicCollisionStrategy
     * @param brickerGameManager the game manager
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     *
     * @param thisObj the brick which was collided
     * @param otherObj the collided object
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj);
    }
}
