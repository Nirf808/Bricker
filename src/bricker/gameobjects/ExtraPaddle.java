package bricker.gameobjects;

import bricker.brick_strategies.AdditionalPaddleStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{
    private int numCollisions;
    private BrickerGameManager brickerGameManager;

    public ExtraPaddle(BrickerGameManager brickerGameManager,
                       Vector2 topLeftCorner, Vector2 dimensions,
                       Renderable renderable,
                       UserInputListener inputListener, float x) {
        super(topLeftCorner, dimensions, renderable, inputListener, x);
        numCollisions = 0;
        AdditionalPaddleStrategy.setHasExtraPuddle(true);
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        numCollisions++;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (numCollisions >= 4){
            brickerGameManager.deleteObject(this);
            AdditionalPaddleStrategy.setHasExtraPuddle(false);
        }
    }
}
