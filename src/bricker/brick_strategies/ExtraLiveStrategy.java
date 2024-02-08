package bricker.brick_strategies;

import bricker.Constants;
import bricker.gameobjects.ExtraLive;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Vector2;
/**
 * The ExtraLiveStrategy class represents a collision strategy in the Bricker game.
 * When a collision occurs between the specified game objects, this strategy is triggered.
 * It deletes the brick object from the game and creates an ExtraLive power-up,
 * which grants an additional life to the player.
 */
public class ExtraLiveStrategy implements CollisionStrategy {

    // Fields
    private final BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs a new ExtraLiveStrategy object.
     *
     * @param brickerGameManager The BrickerGameManager instance for managing game objects.
     */
    public ExtraLiveStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager; // Set the game manager reference
    }

    /**
     * Handles the collision event between game objects.
     * When a collision occurs between the specified game objects, this method deletes the brick object
     * from the game and creates an ExtraLive power-up, which grants an additional life to the player.
     * @param thisObj  The first game object involved in the collision. A brick.
     * @param otherObj The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        // Delete the brick object from the game
        brickerGameManager.deleteBrick(thisObj);
        // Create an ExtraLive power-up
        ExtraLive extraLive = brickerGameManager.getGameObjectFactory().createLive(brickerGameManager,
                Constants.LIVE_IMAGE, new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE),
                thisObj.getCenter(), Vector2.DOWN.mult(100));
        // Add the ExtraLive power-up to the game
        brickerGameManager.addObject(extraLive);
    }
}
