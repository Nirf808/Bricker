package bricker.factories;

import bricker.brick_strategies.*;
import bricker.main.BrickerGameManager;


/**
 * The StrategyFactory class is responsible for creating instances of different collision strategies
 * for the bricks used in the Bricker game.
 */
public class StrategyFactory {

    // Fields
    private final BrickerGameManager brickerGameManager; // Reference to the game manager

    /**
     * Constructs a new StrategyFactory object.
     *
     * @param brickerGameManager The BrickerGameManager instance for managing game objects.
     */
    public StrategyFactory(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager; // Set the game manager reference
    }

    /**
     * Creates a new instance of a CollisionStrategy based on the given integer identifier.
     *
     * @param i The integer identifier indicating the type of collision strategy to create.
     *          0 - Strategy that adds a paddle on collision.
     *          1 - Strategy that adds Puck balls on collision.
     *          2 - Strategy that adds Extra lives on collision.
     *          3 - Strategy that changes the camera on collision.
     *          4 - Strategy that adds applies multiple strategies on collision.
     *          5+ - basic strategy that delets the brick.
     * @return An instance of CollisionStrategy based on the specified identifier.
     */
    public CollisionStrategy createStrategy(int i) {
        CollisionStrategy strategy; // Initialize the strategy variable
        switch (i) {
            case 0:
                strategy = new AdditionalPaddleStrategy(brickerGameManager);
                break;
            case 1:
                strategy = new PuckStrategy(brickerGameManager);
                break;
            case 2:
                strategy = new ExtraLiveStrategy(brickerGameManager);
                break;
            case 3:
                strategy = new CameraStrategy(brickerGameManager);
                break;
            case 4:
                strategy = new DoubleStrategy(brickerGameManager, 2);
                break;
            default:
                strategy = new BasicCollisionStrategy(brickerGameManager);
                break;
        }
        return strategy; // Return the created CollisionStrategy instance
    }
}
