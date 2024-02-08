package bricker.brick_strategies;

import bricker.Constants;
import bricker.gameobjects.ExtraLive;
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
        ExtraLive extraLive = brickerGameManager.getGameObjectFactory().createLive(brickerGameManager,
                Constants.LIVE_IMAGE, new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE),
                thisObj.getCenter(), Vector2.DOWN.mult(100));
        brickerGameManager.addObject(extraLive);
    }
}
