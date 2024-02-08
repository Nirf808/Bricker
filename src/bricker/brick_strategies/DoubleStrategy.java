package bricker.brick_strategies;

import bricker.Constants;
import bricker.factories.StrategyFactory;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

import java.util.Random;

public class DoubleStrategy implements CollisionStrategyDecorator{
    private BrickerGameManager brickerGameManager;
    private CollisionStrategy[] strategies;
    private int strategyCounter;

    public DoubleStrategy(BrickerGameManager brickerGameManager, int strategyCounter) {
        this.brickerGameManager = brickerGameManager;
        this.strategyCounter = strategyCounter;
        this.strategies = new CollisionStrategy[2];
        initializeStrategies();
    }

    private void initializeStrategies(){
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            int strategyNum;
            if(strategyCounter < Constants.MAX_MULTIPLE_STRATEGIES) {
                strategyNum = rand.nextInt(5);
            }
            else {
                strategyNum = rand.nextInt(4);
            }
            if(strategyNum == 4) {
                strategyCounter++;
                DoubleStrategy doubleStrategy = new DoubleStrategy(brickerGameManager,strategyCounter);
                strategyCounter = doubleStrategy.getStrategyCounter();
                strategies[i] = doubleStrategy;
            }
            else {
                strategies[i] = new StrategyFactory(brickerGameManager).createStrategy(strategyNum);
            }
        }
    }

    private int getStrategyCounter() {return strategyCounter;}


    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        for (CollisionStrategy strat : strategies) {
            strat.onCollision(thisObj, otherObj);
        }
    }
}
