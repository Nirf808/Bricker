package bricker.brick_strategies;

import bricker.Constants;
import bricker.main.BrickerGameManager;
import bricker.factories.GameObjectFactory;
import danogl.GameObject;
import danogl.util.Vector2;

public class ExtraLiveStrategy implements CollisionStrategy{
    private BrickerGameManager brickerGameManager;

    public ExtraLiveStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        brickerGameManager.deleteBrick(thisObj);
        brickerGameManager.addObject(brickerGameManager.getGameObjectFactory().createLive(
                new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE), Constants.LIVE_IMAGE,
                thisObj.getCenter(), brickerGameManager));
    }
}
