package bricker.brick_strategies;

import bricker.Constants;
import bricker.factories.StrategyFactory;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

import java.util.Random;

/**
 * The DoubleStrategy class represents a collision strategy for a brick in the Bricker game that combines
 * two different collision strategies into one. It randomly selects two collision strategies,
 * including the possibility of creating nested DoubleStrategy instances. This class implements the
 * CollisionStrategyDecorator interface.
 */
public class DoubleStrategy implements CollisionStrategyDecorator {

    // Fields
    private final BrickerGameManager brickerGameManager; // Reference to the game manager
    private final  CollisionStrategy[] strategies; // Array to store the selected collision strategies
    private int strategyCounter; // Counter for tracking the number of strategies

    /**
     * Constructs a new DoubleStrategy object.
     *
     * @param brickerGameManager The BrickerGameManager instance for managing game objects.
     * @param strategyCounter    The current counter for the number of strategies.
     */
    public DoubleStrategy(BrickerGameManager brickerGameManager, int strategyCounter) {
        this.brickerGameManager = brickerGameManager; // Set the game manager reference
        this.strategyCounter = strategyCounter; // Set the strategy counter
        this.strategies = new CollisionStrategy[2]; // Initialize the array for strategies
        initializeStrategies(); // Initialize the strategies array
    }

    /**
     * Initializes the selected collision strategies.
     * Randomly selects two collision strategies, possibly creating nested DoubleStrategy instances.
     */
    private void initializeStrategies() {
        Random rand = new Random(); // Create a new random number generator
        for (int i = 0; i < 2; i++) {
            int strategyNum;
            // Check if the maximum number of nested strategies has been reached
            if (strategyCounter < Constants.MAX_MULTIPLE_STRATEGIES) {
                strategyNum = rand.nextInt(5); // Generate a random strategy number
            } else { // Generate a random strategy number excluding DoubleStrategy
                strategyNum = rand.nextInt(4);
            }
            // Check if the selected strategy is DoubleStrategy
            if (strategyNum == 4) {
                strategyCounter++; // Increment the strategy counter
                // Create a new instance of DoubleStrategy and update the counter
                DoubleStrategy doubleStrategy = new DoubleStrategy(brickerGameManager, strategyCounter);
                strategyCounter = doubleStrategy.getStrategyCounter(); // Update the counter
                strategies[i] = doubleStrategy; // Assign the nested DoubleStrategy
            } else {
                // Create a new instance of a collision strategy using the StrategyFactory
                strategies[i] = new StrategyFactory(brickerGameManager).createStrategy(strategyNum);
            }
        }
    }

    /**
     * Retrieves the current number of strategies.
     *
     * @return The current number of strategies.
     */
    private int getStrategyCounter() {
        return strategyCounter;
    }

    /**
     * Handles the collision between two game objects using the selected collision strategies.
     *
     * @param thisObj  The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        for (CollisionStrategy strat : strategies) {
            strat.onCollision(thisObj, otherObj); // Apply each selected collision strategy
        }
    }
}
