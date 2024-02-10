package bricker.brick_strategies;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

import java.util.Random;

/**
 * A CollisionStrategy implementation that creates new puck balls in the game.
 * When a collision occurs between a brick and another game object, this strategy
 * deletes the collided brick and spawns two new puck balls in random directions.
 */
public class PuckStrategy implements CollisionStrategy {

    private BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs a new PuckStrategy.
     *
     * @param brickerGameManager The game manager responsible for managing game objects.
     */
    public PuckStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles collision between the brick and another game object.
     * Deletes the collided brick and creates two new puck balls.
     *
     * @param thisObj  The brick involved in the collision.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj); // Delete the collided brick

        // Create two new puck balls
        for (int i = 0; i < 2; i++) {
            brickerGameManager.addObject(
                    brickerGameManager.getGameObjectFactory().createBall(
                            brickerGameManager,
                            Constants.PUCK_BALL_IMAGE, // Image for puck balls
                            Constants.BALL_SOUND, // Sound for puck balls
                            Constants.BALL_SIZE * 0.75f, // Size of puck balls
                            thisObj.getCenter(), // Position of the brick
                            choosePuckDirection() // Random direction for puck balls
                    ),
                    Layer.DEFAULT // Layer for rendering puck balls
            );
        }
    }

    /**
     * Chooses a random direction for the puck balls.
     *
     * @return A vector representing the chosen direction.
     */
    public Vector2 choosePuckDirection() {
        // Calculate random direction using polar coordinates
        double speedAbsoluteValue = Math.sqrt(2) * Constants.BALL_SPEED;
        Random random = new Random();
        double randomValue = random.nextDouble() * 2 * Math.PI;
        double xSpeed = speedAbsoluteValue * Math.cos(randomValue);
        double ySpeed = speedAbsoluteValue * Math.sin(randomValue);
        return new Vector2((float) xSpeed, (float) ySpeed);
    }
}
