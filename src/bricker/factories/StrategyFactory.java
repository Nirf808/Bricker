package bricker.factories;

import bricker.brick_strategies.*;
import bricker.main.BrickerGameManager;

public class StrategyFactory {
    private BrickerGameManager brickerGameManager;

    public StrategyFactory(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    public CollisionStrategy createStrategy(int i) {
        CollisionStrategy strategy = null;
        switch (i)
        {
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
        return strategy;
    }
}
