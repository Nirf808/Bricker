package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * A CollisionStrategy implementation that deletes a brick when a collision occurs.
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager brickerGameManager;

    /**
     * Constructs a new BasicCollisionStrategy.
     *
     * @param brickerGameManager The game manager responsible for managing game objects.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles collision between the brick and another game object.
     *
     * @param thisObj  The brick involved in the collision.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj);
    }
}